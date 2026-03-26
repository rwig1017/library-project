import java.util.ArrayList;
import java.util.Objects;

public class Reader {

    // Constants (from UML)
    public static final int CARD_NUMBER_ = 0;
    public static final int NAME_ = 1;
    public static final int PHONE_ = 2;
    public static final int BOOK_COUNT_ = 3;
    public static final int BOOK_START_ = 4;

    private int cardNumber;
    private String name;
    private String phone;
    private ArrayList<Book> books;

    // Constructor
    public Reader(int cardNumber, String name, String phone) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.phone = phone;
        this.books = new ArrayList<>();
    }

    // Getters
    public int getCardNumber() { return cardNumber; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public int getBookCount() { return books.size(); }

    // Setters
    public void setCardNumber(int cardNumber) { this.cardNumber = cardNumber; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }

    // Helper method (important for consistency)
    private Book findBook(Book book) {
        for (Book b : books) {
            if (b.equals(book)) {
                return b;
            }
        }
        return null;
    }

    // addBook
    public Code addBook(Book book) {
        if (findBook(book) != null) {
            return Code.BOOK_ALREADY_CHECKED_OUT_ERROR;
        }
        books.add(book);
        return Code.SUCCESS;
    }

    // removeBook
    public Code removeBook(Book book) {
        Book found = findBook(book);

        if (found == null) {
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        }

        if (books.remove(found)) {
            return Code.SUCCESS;
        }

        return Code.READER_COULD_NOT_REMOVE_BOOK_ERROR;
    }

    // hasBook
    public boolean hasBook(Book book) {
        return findBook(book) != null;
    }

    // equals (DO NOT include books list)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reader)) return false;
        Reader reader = (Reader) o;
        return cardNumber == reader.cardNumber &&
                Objects.equals(name, reader.name) &&
                Objects.equals(phone, reader.phone);
    }

    // hashCode (exclude books)
    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, name, phone);
    }

    // toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name)
                .append(" (#")
                .append(cardNumber)
                .append(") has checked out {");

        for (int i = 0; i < books.size(); i++) {
            sb.append(books.get(i));
            if (i < books.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("}");
        return sb.toString();
    }
}