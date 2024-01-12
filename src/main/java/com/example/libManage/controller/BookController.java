package com.example.libManage.controller;

import com.example.libManage.dto.request.book.AddBookRequest;
import com.example.libManage.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/add-new-book")
    public ResponseEntity addNewBook(HttpServletRequest request, HttpServletResponse response,
                                     @RequestBody AddBookRequest book) {
        return bookService.addNewBook(request, response, book);
    }

    @GetMapping("/get-all-books")
    public ResponseEntity getAllBooks(HttpServletRequest request, HttpServletResponse response) {
        return bookService.getAllBookList(request,response);
    }

    @PostMapping("/update-book")
    public ResponseEntity updateBook(HttpServletRequest request, HttpServletResponse response,
                                     @RequestBody AddBookRequest book) {
        return bookService.updateBook(request, response, book);
    }

    @PostMapping("/delete-book-by-id/{bookId}")
    public ResponseEntity deleteBook(HttpServletRequest request, HttpServletResponse response,
                                     @PathVariable Integer bookId) {
        return bookService.deleteBook(request, response, bookId);
    }
}
