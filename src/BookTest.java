import java.time.LocalDate;

public class BookTest {
    public static void main(String[] args) {
        // Create a few Book objects
        Book book1 = new Book("978-0134685991", "Effective Java", "Programming", 416, "Joshua Bloch", LocalDate.of(2026, 4, 1));
        Book book2 = new Book("978-0596009205", "Head First Java", "Programming", 720, "Kathy Sierra", LocalDate.of(2026, 5, 15));
        Book book3 = new Book("978-0134685991", "Effective Java", "Programming", 416, "Joshua Bloch", LocalDate.of(2026, 6, 1));

        // Print books using toString
        System.out.println("Book 1: " + book1);
        System.out.println("Book 2: " + book2);
        System.out.println("Book 3: " + book3);
        System.out.println();

        // Test getters
        System.out.println("Book 1 ISBN: " + book1.getIsbn());
        System.out.println("Book 2 Title: " + book2.getTitle());
        System.out.println("Book 2 Author: " + book2.getAuthor());
        System.out.println();

        // Test setters
        book2.setPageCount(730);
        System.out.println("Updated Book 2 page count: " + book2.getPageCount());
        book2.setDueDate(LocalDate.of(2026, 6, 30));
        System.out.println("Updated Book 2 due date: " + book2.getDueDate());
        System.out.println();

        // Test equals() method
        System.out.println("book1 equals book2? " + book1.equals(book2)); // should be false
        System.out.println("book1 equals book3? " + book1.equals(book3)); // should be true, same isbn, title, author, pageCount, subject
        System.out.println();

        // Test hashCode consistency
        System.out.println("book1 hashCode: " + book1.hashCode());
        System.out.println("book3 hashCode: " + book3.hashCode()); // should be same as book1
    }
}
