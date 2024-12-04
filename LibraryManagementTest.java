package librarymanagement;

import static org.junit.Assert.*;
import org.junit.Test;

public class LibraryManagementTest {

    @Test
    public void testBookId() {
        try {
            // Valid IDs
            Book book1 = new Book(100, "Book 100");
            assertNotNull(book1);
            assertEquals(100, book1.getId());

            Book book2 = new Book(999, "Book 999");
            assertNotNull(book2);
            assertEquals(999, book2.getId());

            // Invalid IDs
            try {
                new Book(99, "Invalid Book 1");
                fail("Expected an exception for ID less than 100.");
            } catch (Exception e) {
                assertEquals("Invalid book ID. ID must be between 100 and 999.", e.getMessage());
            }

            try {
                new Book(1000, "Invalid Book 2");
                fail("Expected an exception for ID greater than 999.");
            } catch (Exception e) {
                assertEquals("Invalid book ID. ID must be between 100 and 999.", e.getMessage());
            }
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testBorrowReturn() throws Exception { // Exception handling for borrow/return logic
        // Step 1: Create a Book and Member object
        Book book = new Book(101, "Java Programming");
        Member member = new Member(1, "Alice");

        // Step 2: Retrieve the singleton Transaction instance
        Transaction transaction = Transaction.getTransaction();

        // Step 3: Assert that the book is initially available
        assertTrue("Book should be available initially", book.isAvailable());

        // Step 4: Borrow the book and assert success
        assertTrue("Borrowing should be successful", transaction.borrowBook(book, member));
        assertFalse("Book should be unavailable after borrowing", book.isAvailable());

        // Step 5: Attempt to borrow the same book again and assert failure
        assertFalse("Borrowing the same book again should fail", transaction.borrowBook(book, member));

        // Step 6: Return the book and assert success
        assertTrue("Returning the book should be successful", transaction.returnBook(book, member));
        assertTrue("Book should be available after returning", book.isAvailable());

        // Step 7: Attempt to return the same book again and assert failure
        assertFalse("Returning the same book again should fail", transaction.returnBook(book, member));
    }
}
