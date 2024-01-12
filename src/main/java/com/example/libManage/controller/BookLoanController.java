package com.example.libManage.controller;

import com.example.libManage.dto.request.bookLoan.AddBookLoanRequest;
import com.example.libManage.service.bookLoan.BookLoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/book-loan")
@RequiredArgsConstructor
public class BookLoanController {

    private final BookLoanService bookLoanService;

    @PostMapping("/add-book-loan")
    public ResponseEntity loanBook(HttpServletRequest request, HttpServletResponse response,
                                     @RequestBody AddBookLoanRequest loanDetails) {
        return bookLoanService.loanBook(request, response, loanDetails);
    }

    @PostMapping("/return-book/book/{bookId}")
    public ResponseEntity returnBook(HttpServletRequest request, HttpServletResponse response,
                                        @PathVariable Integer bookId) {
        return bookLoanService.returnBook(request, response, bookId);
    }
}
