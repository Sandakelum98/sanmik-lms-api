package com.example.libManage.service.book;

import com.example.libManage.dto.request.book.AddBookRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface BookService {

    ResponseEntity addNewBook(HttpServletRequest request, HttpServletResponse response, AddBookRequest book);
    ResponseEntity getAllBookList(HttpServletRequest request, HttpServletResponse response);
    ResponseEntity updateBook(HttpServletRequest request, HttpServletResponse response, AddBookRequest book);
    ResponseEntity deleteBook(HttpServletRequest request, HttpServletResponse response, Integer bookId);
    ResponseEntity searchBook(HttpServletRequest request, HttpServletResponse response, String searchText);

}
