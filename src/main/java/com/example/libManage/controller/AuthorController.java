package com.example.libManage.controller;

import com.example.libManage.entity.author.Author;
import com.example.libManage.service.author.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/author")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/add-author")
    public ResponseEntity saveAuthor(HttpServletRequest request, HttpServletResponse response,
                                        @RequestBody Author author) {
        return authorService.saveAuthor(request, response, author);
    }
    @GetMapping("/get-all-author")
    public ResponseEntity getAllAuthors(HttpServletRequest request, HttpServletResponse response) {
        return authorService.getAllAuthors(request, response);
    }
}
