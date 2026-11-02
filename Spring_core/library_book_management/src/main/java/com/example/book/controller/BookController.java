
package com.example.book.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.example.book.model.Book;
import com.example.book.service.BookService;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    
    private final BookService bookService;

    // 1. Add Book
    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {

        Book savedBook = bookService.addBook(book);

        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // 2. Get All Books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {

        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // 3. Get Book By ID
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(
            @PathVariable Integer bookId) {

        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    // 4. Update Book Details
    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Integer bookId,
            @Valid @RequestBody Book book) {

        return ResponseEntity.ok(
                bookService.updateBook(bookId, book)
        );
    }

    // 5. Delete Book
    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> deleteBook(
            @PathVariable Integer bookId) {

        return ResponseEntity.ok(
                bookService.deleteBook(bookId)
        );
    }

    // 6. Search By Book Name
    @GetMapping("/search/name")
    public ResponseEntity<List<Book>> searchByBookName(
            @RequestParam String name) {

        return ResponseEntity.ok(
                bookService.searchByBookName(name)
        );
    }

    // 7. Search By Author
    @GetMapping("/search/author")
    public ResponseEntity<List<Book>> searchByAuthor(
            @RequestParam String author) {

        return ResponseEntity.ok(
                bookService.searchByAuthor(author)
        );
    }

    // 8. Get Available Books
    @GetMapping("/available")
    public ResponseEntity<List<Book>> getAvailableBooks() {

        return ResponseEntity.ok(
                bookService.getAvailableBooks()
        );
    }

    // 9. Borrow Book
    @PatchMapping("/{bookId}/borrow")
    public ResponseEntity<Book> borrowBook(
            @PathVariable Integer bookId) {

        return ResponseEntity.ok(
                bookService.borrowBook(bookId)
        );
    }

    // 10. Return Book
    @PatchMapping("/{bookId}/return")
    public ResponseEntity<Book> returnBook(
            @PathVariable Integer bookId) {

        return ResponseEntity.ok(
                bookService.returnBook(bookId)
        );
    }
}

