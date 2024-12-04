package librarymanagement;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private static Transaction instance;

    private Transaction() {
        // Private constructor for Singleton
    }

    public static Transaction getTransaction() {
        if (instance == null) {
            instance = new Transaction();
        }
        return instance;
    }

    public boolean borrowBook(Book book, Member member) {
        if (book.isAvailable()) {
            book.borrowBook();
            member.borrowBook(book);
            String transactionDetails = getCurrentDateTime() + " - Borrowing: " + member.getName() + " borrowed " + book.getTitle();
            System.out.println(transactionDetails);

            saveTransaction(transactionDetails);
            return true;
        } else {
            System.out.println("The book is not available.");
            return false;
        }
    }

    public boolean returnBook(Book book, Member member) {
        if (!member.getBorrowedBooks().contains(book)) {
            System.out.println("This book was not borrowed by the member.");
            return false;
        }

        member.returnBook(book);
        book.returnBook();
        String transactionDetails = getCurrentDateTime() + " - Returning: " + member.getName() + " returned " + book.getTitle();
        System.out.println(transactionDetails);

        saveTransaction(transactionDetails);
        return true;
    }


    private void saveTransaction(String transactionDetails) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            writer.write(transactionDetails);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to save transaction: " + e.getMessage());
        }
    }

    // Display transaction history from the file
    public void displayTransactionHistory() {
        System.out.println("\n=== Transaction History ===");
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("No transaction history found. File does not exist.");
        } catch (IOException e) {
            System.err.println("Error reading transaction history: " + e.getMessage());
        }
        System.out.println("===========================\n");
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
