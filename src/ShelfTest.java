import java.time.LocalDate;

public class ShelfTest {
    public static void main(String[] args) {
        Shelf shelf = new Shelf(1, "Fiction");

        // Create a few books
        Book bookA = new Book("123", "Book A", "Fiction", 100, "Author A", LocalDate.now());
        Book bookB = new Book("456", "Book B", "Fiction", 200, "Author B", LocalDate.now());
        Book bookC = new Book("789", "Book C", "Sci-Fi", 150, "Author C", LocalDate.now());

        System.out.println("=== Adding Books ===");
        shelf.addBook(bookA); // Should succeed
        shelf.addBook(bookA); // Increment count
        shelf.addBook(bookB); // Should succeed
        Code result = shelf.addBook(bookC); // Subject mismatch
        if (result != Code.SUCCESS) {
            System.out.println("Failed to add Book C: " + result);
        }

        System.out.println("\n=== Shelf Inventory ===");
        System.out.println(shelf.listBooks());

        System.out.println("=== Removing Books ===");
        shelf.removeBook(bookA); // Count decrements
        shelf.removeBook(bookA); // Count becomes 0
        shelf.removeBook(bookA); // Should trigger BOOK_NOT_IN_INVENTORY_ERROR
        shelf.removeBook(bookC); // Not in inventory, error

        System.out.println("\n=== Final Shelf Inventory ===");
        System.out.println(shelf.listBooks());
    }
}
