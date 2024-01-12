package com.example.libManage.service.bookLoan;

import com.example.libManage.dto.request.bookLoan.AddBookLoanRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface BookLoanService {

    ResponseEntity loanBook(HttpServletRequest request, HttpServletResponse response, AddBookLoanRequest loanDetails);
    ResponseEntity returnBook(HttpServletRequest request, HttpServletResponse response, Integer bookId);
}
