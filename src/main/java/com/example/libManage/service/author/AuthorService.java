package com.example.libManage.service.author;

import com.example.libManage.entity.author.Author;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthorService {

    ResponseEntity saveAuthor(HttpServletRequest request, HttpServletResponse response, Author author);
    ResponseEntity getAllAuthors(HttpServletRequest request, HttpServletResponse response);
}
