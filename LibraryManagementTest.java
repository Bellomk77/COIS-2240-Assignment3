package librarymanagement;

import static org.junit.Assert.*;
import org.junit.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * This class contains unit tests for the Library Management System.
 * It validates the behavior of various classes including Transaction, Book, and Member.
 * The tests cover Singleton enforcement, book ID validation, and borrow/return functionality.
 */
public class LibraryManagementTest {

    /**
     * Test to validate the Singleton behavior of the Transaction class.
     * Ensures that the constructor of the Transaction class is private.
     */
    @Test
    public void testSingletonTransaction() {
        try {
            // Step 1: Access the constructor of the Transaction class using reflection
            Constructor<Transaction> constructor = Transaction.class.getDeclaredConstructor();

            // Step 2: Get the modifier of the constructor to check if it is private
            int modifiers = constructor.getModifiers();

            // Step 3: Assert that the constructor is private, enforcing Singleton behavior
            assertTrue("Constructor should be private", Modifier.isPrivate(modifiers));
        } catch (Exception e) {
            // If an exception is thrown, the test fails with the exception message
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Test to validate the ID constraints of the Book class.
     * Ensures that valid IDs are accepted, while invalid IDs throw exceptions.
     */
    @Test
    public void testBookId() {
        try {
            // Valid IDs
            Book book1 = new Book(100, "Book 100");
            assertNotNull("Book object should not be null", book1);
            assertEquals("Book ID should be 100", 100, book1.getId());

            Book book2 = new Book(999, "Book 999");
            assertNotNull("Book object should not be null", book2);
            assertEquals("Book ID should be 999", 999, book2.getId());

            // Invalid IDs (less than 100)
            try {
                new Book(99, "Invalid Book 1");
                fail("Expected an exception for ID less than 100.");
            } catch (Exception e) {
                assertEquals("Invalid book ID. ID must be between 100 and 999.", e.getMessage());
            }

            // Invalid IDs (greater than 999)
            try {
                new Book(1000, "Invalid Book 2");
                fail("Expected an exception for ID greater than 999.");
            } catch (Exception e) {
                assertEquals("Invalid book ID. ID must be between 100 and 999.", e.getMessage());
            }
        } catch (Exception e) {
            // If any unexpected exception is thrown, the test fails
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Test to validate the borrow and return functionality of the Library Management System.
     * Ensures that books can be borrowed and returned correctly, and appropriate errors occur for invalid operations.
     */
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
