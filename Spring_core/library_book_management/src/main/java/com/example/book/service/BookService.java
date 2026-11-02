
package com.example.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.book.model.Book;
import com.example.book.exception.BookNotFoundException;
import com.example.book.exception.BookAlreadyBorrowedException;
import com.example.book.exception.BookAlreadyAvailableException;

@Service
public class BookService {

    private List<Book> books = new ArrayList<>();

    private Integer bookIdCounter = 1;

    // 1. Add Book
    public Book addBook(Book book) {

        book.setBookId(bookIdCounter++);
        books.add(book);

        return book;
    }

    // 2. Get All Books
    public List<Book> getAllBooks() {

        return books;
    }

    // 3. Get Book By ID
    public Book getBookById(Integer bookId) {

        return books.stream()
                .filter(book -> book.getBookId().equals(bookId))
                .findFirst()
                .orElseThrow(() ->
                    new BookNotFoundException(
                        "Book not found with ID: " + bookId
                    )
                );
    }

    // 4. Update Book Details
    public Book updateBook(Integer bookId, Book updatedBook) {

        Book existingBook = getBookById(bookId);

        existingBook.setBookName(updatedBook.getBookName());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setPrice(updatedBook.getPrice());

        return existingBook;
    }

    // 5. Delete Book
    public String deleteBook(Integer bookId) {

        Book book = getBookById(bookId);

        books.remove(book);

        return "Book deleted successfully with ID: " + bookId;
    }

    // 6. Search By Book Name
    public List<Book> searchByBookName(String bookName) {

        List<Book> result = books.stream()
                .filter(book -> book.getBookName()
                .toLowerCase()
                .contains(bookName.toLowerCase()))
                .collect(Collectors.toList());

        return result;
    }

    // 7. Search By Author
    public List<Book> searchByAuthor(String author) {

        List<Book> result = books.stream()
                .filter(book -> book.getAuthor()
                .toLowerCase()
                .contains(author.toLowerCase()))
                .collect(Collectors.toList());

        return result;
    }

    // 8. Get Available Books
    public List<Book> getAvailableBooks() {

        return books.stream()
                .filter(book -> book.getAvailable())
                .collect(Collectors.toList());
    }

    // 9. Borrow Book
    public Book borrowBook(Integer bookId) {

        Book book = getBookById(bookId);

        if (!book.getAvailable()) {

            throw new BookAlreadyBorrowedException(
                "Book is already borrowed"
            );
        }

        book.setAvailable(false);

        return book;
    }

    // 10. Return Book
    public Book returnBook(Integer bookId) {

        Book book = getBookById(bookId);

        if (book.getAvailable()) {

            throw new BookAlreadyAvailableException(
                "Book is already available"
            );
        }

        book.setAvailable(true);

        return book;
    }
}
