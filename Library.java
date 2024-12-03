package librarymanagement;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Member> members = new ArrayList<Member>();
    private List<Book> books = new ArrayList<Book>();

    // Add a new member to the library
    public boolean addMember(Member member) {
        // Check if a member with the same ID already exists
        if (findMemberById(member.getId()) != null) {
            System.out.println("Error: A member with ID " + member.getId() + " already exists.");
            return false; // Addition failed
        }

        // Add the member if no duplicate ID is found
        members.add(member);
        System.out.println("Member added successfully!");
        return true; // Addition successful
    }
    
    // Add a new book to the library
    public boolean addBook(Book book) {
        // Check if a book with the same ID already exists
        if (findBookById(book.getId()) != null) {
            System.out.println("Error: A book with ID " + book.getId() + " already exists.");
            return false; // Addition failed
        }

        // Add the book if no duplicate ID is found
        books.add(book);
        System.out.println("Book added successfully!");
        return true; // Addition successful
    }

    // Find a member by ID
    public Member findMemberById(int id) {
        for (Member member : members) {
            if (member.getId() == id) {
                return member;
            }
        }
        return null; // No member found with the given ID
    }

    // Find a book by ID
    public Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null; // No book found with the given ID
    }

    // Get the list of members
    public List<Member> getMembers() {
        return members;
    }
    
    // Get the list of books
    public List<Book> getBooks() {
        return books;
    }
}
