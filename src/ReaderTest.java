import java.time.LocalDate;

public class ReaderTest {

    // Helper to print results
    private static void check(String testName, boolean condition) {
        if (condition) {
            System.out.println("PASS: " + testName);
        } else {
            System.out.println("FAIL: " + testName);
        }
    }

    private static Book createBook(String isbn, String date) {
        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        return new Book(isbn, "Title-" + isbn, "Subject", 100,
                "Author", LocalDate.of(year, month, day));
    }

    public static void main(String[] args) {

        Reader r1 = new Reader(1, "Drew Clinkenbeard", "831-582-4007");

        Book r1b1 = createBook("42-w-87", "2020-10-12");
        Book r1b2 = createBook("1337", "2020-11-1");

        r1.addBook(r1b1);
        r1.addBook(r1b2);

        check("Reader1 initial count", r1.getBookCount() == 2);
        check("Reader1 has first book", r1.hasBook(r1b1));
        check("Reader1 has second book", r1.hasBook(r1b2));

        Reader r2 = new Reader(2, "Jennifer Clinkenbeard", "831-555-6284");
        Book r2b1 = createBook("42-w-87", "2020-05-05");

        r2.addBook(r2b1);

        check("Reader2 count", r2.getBookCount() == 1);
        check("Reader2 has book", r2.hasBook(r2b1));

        Reader r3 = new Reader(3, "Monte Ray", "555-555-4444");

        Book r3b1 = createBook("42-w-87", "2020-12-12");
        Book r3b2 = createBook("1337", "2021-1-2");

        r3.addBook(r3b1);
        r3.addBook(r3b2);

        check("Reader3 count", r3.getBookCount() == 2);

        Reader r4 = new Reader(4, "Laurence Fishburn", "831-582-4007");

        Book r4b1 = createBook("42-w-87", "2019-02-18");
        Book r4b2 = createBook("1337", "2025-10-10");

        r4.addBook(r4b1);
        r4.addBook(r4b2);

        check("Reader4 count", r4.getBookCount() == 2);


        // Duplicate add
        check("Duplicate add blocked",
                r1.addBook(r1b1) == Code.BOOK_ALREADY_CHECKED_OUT_ERROR);

        // Remove test
        check("Remove existing book",
                r1.removeBook(r1b1) == Code.SUCCESS);

        check("Remove missing book",
                r1.removeBook(r1b1) == Code.READER_DOESNT_HAVE_BOOK_ERROR);

        // hasBook after removal
        check("hasBook after removal",
                !r1.hasBook(r1b1));

        // toString format check
        System.out.println("\nSample toString output:");
        System.out.println(r2);
    }
}