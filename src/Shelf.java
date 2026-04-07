import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Shelf {

    // Constants
    public static final int SHELF_NUMBER_ = 0;
    public static final int SUBJECT_ = 1;

    private int shelfNumber;
    private String subject;
    private HashMap<Book, Integer> books;

    // Default constructor
    public Shelf() {
        this.books = new HashMap<>();
    }

    // Parameterized constructor
    public Shelf(int shelfNumber, String subject) {
        this.shelfNumber = shelfNumber;
        this.subject = subject;
        this.books = new HashMap<>();
    }

    // Getters & Setters
    public int getShelfNumber() { return shelfNumber; }
    public void setShelfNumber(int shelfNumber) { this.shelfNumber = shelfNumber; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public HashMap<Book, Integer> getBooks() { return books; }
    public void setBooks(HashMap<Book, Integer> books) { this.books = books; }

    // equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shelf)) return false;
        Shelf shelf = (Shelf) o;
        return shelfNumber == shelf.shelfNumber &&
                Objects.equals(subject, shelf.subject);
    }

    // hashCode
    @Override
    public int hashCode() {
        return Objects.hash(shelfNumber, subject);
    }

    // toString
    @Override
    public String toString() {
        return shelfNumber + " : " + subject;
    }

    // getBookCount
    public int getBookCount(Book book) {
        if (!books.containsKey(book)) {
            return -1;
        }
        return books.get(book);
    }

    // addBook
    public Code addBook(Book book) {

        // If book already exists increment count
        if (books.containsKey(book)) {
            books.put(book, books.get(book) + 1);
            System.out.println(book + " added to shelf " + this);
            return Code.SUCCESS;
        }

        // If subject mismatch
        if (!book.getSubject().equalsIgnoreCase(subject)) {
            return Code.SHELF_SUBJECT_MISMATCH_ERROR;
        }

        // Add new book
        books.put(book, 1);
        System.out.println(book + " added to shelf " + this);
        return Code.SUCCESS;
    }

    // removeBook
    public Code removeBook(Book book) {

        // Not in map at all
        if (!books.containsKey(book)) {
            System.out.println(book.getTitle() + " is not on shelf " + subject);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        int count = books.get(book);

        // Count is 0
        if (count == 0) {
            System.out.println("No copies of " + book.getTitle() + " remain on shelf " + subject);
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        // Decrement
        books.put(book, count - 1);
        System.out.println(book.getTitle() + " successfully removed from shelf " + subject);
        return Code.SUCCESS;
    }

    // listBooks
    public String listBooks() {
        StringBuilder sb = new StringBuilder();

        int total = 0;
        for (int count : books.values()) {
            total += count;
        }

        sb.append(total)
                .append(" books on shelf: ")
                .append(this)
                .append("\n");

        for (Map.Entry<Book, Integer> entry : books.entrySet()) {
            sb.append(entry.getKey())
                    .append(" ")
                    .append(entry.getValue())
                    .append("\n");
        }

        return sb.toString();
    }
}
