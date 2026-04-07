import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;

public class Library {

    public static final int LENDING_LIMIT = 5;

    private String name;
    private static int libraryCard = 0;

    private List<Reader> readers;
    private HashMap<String, Shelf> shelves;
    private HashMap<Book, Integer> books;

    // Constructor
    public Library(String name) {
        this.name = name;
        this.readers = new ArrayList<>();
        this.shelves = new HashMap<>();
        this.books = new HashMap<>();
    }

    public Code init(String filename) {
        try {
            Scanner scan = new Scanner(new File(filename));

            int bookCount = convertInt(scan.nextLine(), Code.BOOK_COUNT_ERROR);
            if (bookCount < 0) return errorCode(bookCount);

            Code code = initBooks(bookCount, scan);
            if (code != Code.SUCCESS) return code;
            listBooks();

            int shelfCount = convertInt(scan.nextLine(), Code.SHELF_COUNT_ERROR);
            if (shelfCount < 0) return errorCode(shelfCount);

            code = initShelves(shelfCount, scan);
            if (code != Code.SUCCESS) return code;
            listShelves();

            int readerCount = convertInt(scan.nextLine(), Code.READER_COUNT_ERROR);
            if (readerCount < 0) return errorCode(readerCount);

            code = initReaders(readerCount, scan);
            if (code != Code.SUCCESS) return code;
            listReaders();

        } catch (FileNotFoundException e) {
            return Code.FILE_NOT_FOUND_ERROR;
        }

        return Code.SUCCESS;
    }

    public Code initBooks(int bookCount, Scanner scan) {
        if (bookCount < 1) return Code.UNKNOWN_ERROR;

        for (int i = 0; i < bookCount; i++) {
            String[] data = scan.nextLine().split(",");

            if (data.length <= Book.DUE_DATE_) {
                return Code.BOOK_RECORD_COUNT_ERROR;
            }

            int pageCount = convertInt(data[Book.PAGE_COUNT_], Code.PAGE_COUNT_ERROR);
            if (pageCount <= 0) return Code.PAGE_COUNT_ERROR;

            LocalDate date = convertDate(data[Book.DUE_DATE_], Code.DATE_CONVERSION_ERROR);

            Book book = new Book(
                    data[Book.ISBN_],
                    data[Book.TITLE_],
                    data[Book.SUBJECT_],
                    pageCount,
                    data[Book.AUTHOR_],
                    date
            );

            addBook(book);
        }

        return Code.SUCCESS;
    }

    public Code initShelves(int shelfCount, Scanner scan) {
        if (shelfCount < 1) return Code.SHELF_COUNT_ERROR;

        for (int i = 0; i < shelfCount; i++) {
            String[] data = scan.nextLine().split(",");

            int number = convertInt(data[0], Code.SHELF_NUMBER_PARSE_ERROR);
            if (number < 0) return Code.SHELF_NUMBER_PARSE_ERROR;

            Shelf shelf = new Shelf(number, data[1]);
            addShelf(shelf);
        }

        if (shelves.size() != shelfCount) {
            System.out.println("Number of shelves doesn't match expected");
            return Code.SHELF_NUMBER_PARSE_ERROR;
        }

        return Code.SUCCESS;
    }

    public Code initReaders(int readerCount, Scanner scan) {
        if (readerCount <= 0) return Code.READER_COUNT_ERROR;

        for (int i = 0; i < readerCount; i++) {
            String[] data = scan.nextLine().split(",");

            int card = convertInt(data[Reader.CARD_NUMBER_], Code.READER_CARD_NUMBER_ERROR);
            Reader reader = new Reader(card, data[Reader.NAME_], data[Reader.PHONE_]);

            addReader(reader);

            int bookCount = convertInt(data[Reader.BOOK_COUNT_], Code.BOOK_COUNT_ERROR);

            int index = Reader.BOOK_START_;

            for (int j = 0; j < bookCount; j++) {
                String isbn = data[index++];
                String dateStr = data[index++];

                Book book = getBookByISBN(isbn);

                if (book == null) {
                    System.out.println("ERROR: Book with ISBN " + isbn + " not found");
                    continue;
                }

                LocalDate date = convertDate(dateStr, Code.DATE_CONVERSION_ERROR);
                book.setDueDate(date);

                checkoutBook(reader, book);
            }
        }

        return Code.SUCCESS;
    }

    // ADD BOOK
    public Code addBook(Book newBook) {
        if (books.containsKey(newBook)) {
            int count = books.get(newBook) + 1;
            books.put(newBook, count);
            System.out.println(count + " copies of " + newBook.getTitle() + " in the stacks");
        } else {
            books.put(newBook, 1);
            System.out.println(newBook.getTitle() + " added to the stacks.");
        }

        Shelf shelf = shelves.get(newBook.getSubject());
        if (shelf != null) {
            shelf.addBook(newBook);
            return Code.SUCCESS;
        }

        System.out.println("No shelf for " + newBook.getSubject() + " books");
        return Code.SHELF_EXISTS_ERROR;
    }

    // LIST BOOKS
    public int listBooks() {
        int total = 0;

        for (Map.Entry<Book, Integer> entry : books.entrySet()) {
            System.out.println(entry.getValue() + " copies of " + entry.getKey());
            total += entry.getValue();
        }

        return total;
    }

    // CHECKOUT
    public Code checkoutBook(Reader reader, Book book) {

        if (!readers.contains(reader)) {
            System.out.println(reader.getName() + " doesn't have an account here");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }

        if (reader.getBookCount() >= LENDING_LIMIT) {
            System.out.println(reader.getName() + " has reached the lending limit");
            return Code.BOOK_LIMIT_REACHED_ERROR;
        }

        if (!books.containsKey(book)) {
            System.out.println("ERROR: could not find " + book);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        Shelf shelf = shelves.get(book.getSubject());
        if (shelf == null) {
            return Code.SHELF_EXISTS_ERROR;
        }

        if (shelf.getBookCount(book) <= 0) {
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        Code code = reader.addBook(book);
        if (code != Code.SUCCESS) return code;

        shelf.removeBook(book);

        System.out.println(book + " checked out successfully");
        return Code.SUCCESS;
    }

    // RETURN BOOK
    public Code returnBook(Reader reader, Book book) {

        if (!reader.hasBook(book)) {
            System.out.println(reader.getName() + " doesn't have " + book.getTitle());
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        }

        Code code = reader.removeBook(book);

        if (code == Code.SUCCESS) {
            return returnBook(book);
        }

        return code;
    }

    public Code returnBook(Book book) {
        Shelf shelf = shelves.get(book.getSubject());

        if (shelf == null) {
            return Code.SHELF_EXISTS_ERROR;
        }

        return shelf.addBook(book);
    }

    // ADDITIONAL MISSING METHODS

    public Code addShelf(Shelf shelf) {
        if (shelves.containsKey(shelf.getSubject())) {
            System.out.println("Shelf for " + shelf.getSubject() + " already exists");
            return Code.SHELF_EXISTS_ERROR;
        }
        shelves.put(shelf.getSubject(), shelf);
        System.out.println("Shelf for " + shelf.getSubject() + " added.");
        return Code.SUCCESS;
    }

    public Code addReader(Reader reader) {
        if (readers.contains(reader)) {
            System.out.println(reader.getName() + " already has an account");
            return Code.READER_ALREADY_EXISTS_ERROR;
        }
        readers.add(reader);
        System.out.println("Reader " + reader.getName() + " added.");
        return Code.SUCCESS;
    }

    public void listShelves() {
        System.out.println("Library Shelves:");
        for (Shelf shelf : shelves.values()) {
            System.out.println(shelf);
        }
    }

    public void listReaders() {
        System.out.println("Library Readers:");
        for (Reader reader : readers) {
            System.out.println(reader);
        }
    }

    public Book getBookByISBN(String isbn) {
        for (Book book : books.keySet()) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    // HELPERS
    public static int convertInt(String value, Code code) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.out.println("Value which caused the error: " + value);
            System.out.println("Error message: " + code.getMessage());
            return code.getCode();
        }
    }

    public static LocalDate convertDate(String date, Code code) {
        try {
            if (date.equals("0000")) {
                return LocalDate.of(1970, 1, 1);
            }

            String[] parts = date.split("-");
            return LocalDate.of(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );

        } catch (Exception e) {
            System.out.println("ERROR: date conversion error, could not parse " + date);
            return LocalDate.of(1970, 1, 1);
        }
    }

    private Code errorCode(int codeNumber) {
        for (Code code : Code.values()) {
            if (code.getCode() == codeNumber) {
                return code;
            }
        }
        return Code.UNKNOWN_ERROR;
    }

    public static int getLibraryCardNumber() {
        return libraryCard + 1;
    }
}