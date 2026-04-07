public enum Code {

    SUCCESS(0, "Success"),

    // BOOK ERRORS
    BOOK_NOT_IN_INVENTORY_ERROR(-1, "Book not in inventory"),
    BOOK_ALREADY_CHECKED_OUT_ERROR(-2, "Book already checked out"),
    BOOK_COUNT_ERROR(-3, "Error: Could not read number of books"),
    BOOK_RECORD_COUNT_ERROR(-24, "The count of records for the book doesn't make sense"),

    // SHELF ERRORS
    SHELF_EXISTS_ERROR(-4, "Shelf already exists"),
    SHELF_SUBJECT_MISMATCH_ERROR(-5, "Shelf subject mismatch"),
    SHELF_NUMBER_PARSE_ERROR(-6, "Error parsing shelf number"),
    SHELF_COUNT_ERROR(-7, "Invalid shelf count"),

    // READER ERRORS
    READER_DOESNT_HAVE_BOOK_ERROR(-8, "Reader doesn't have book"),
    READER_COULD_NOT_REMOVE_BOOK_ERROR(-9, "Could not remove book"),
    READER_NOT_IN_LIBRARY_ERROR(-10, "Reader not in library"),
    READER_CARD_NUMBER_ERROR(-11, "Duplicate card number"),
    READER_COUNT_ERROR(-12, "Invalid reader count"),
    READER_ALREADY_EXISTS_ERROR(-47, "Reader already exists"),
    READER_STILL_HAS_BOOKS_ERROR(-48, "Must return all books"),

    // CHECKOUT / LIMIT
    BOOK_LIMIT_REACHED_ERROR(-13, "Book limit reached"),

    // CONVERSION / FILE
    PAGE_COUNT_ERROR(-14, "Error: could not parse page count"),
    DATE_CONVERSION_ERROR(-15, "Error: Could not parse date component"),
    FILE_NOT_FOUND_ERROR(-16, "File not found"),


    UNKNOWN_ERROR(-99, "Unknown error");

    private final int code;
    private final String message;

    // Constructor
    Code(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getter for numeric code
    public int getCode() {
        return code;
    }

    // Getter for message
    public String getMessage() {
        return message;
    }
}