package com.example.libManage.service.impl;

import com.example.libManage.constants.Constants;
import com.example.libManage.dto.request.book.AddBookRequest;
import com.example.libManage.entity.author.Author;
import com.example.libManage.entity.book.BookBean;
import com.example.libManage.repo.book.BookRepo;
import com.example.libManage.service.book.BookService;
import com.example.libManage.utility.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final Logger logger = LogManager.getLogger(getClass());

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @Override
    public ResponseEntity addNewBook(HttpServletRequest request, HttpServletResponse response, AddBookRequest book) {
        logger.info("Received request: {}", request.getRequestURI());

        // validate data
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>().responseFail("Title cannot be empty"));
        }
        if (book.getAuthorId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>().responseFail("Author cannot be empty"));
        }

        try {

            BookBean bookBean = new BookBean();
            bookBean.setTitle(book.getTitle());
            bookBean.setAuthor(new Author(book.getAuthorId()));
            bookBean.setStatus(0); // 0 -> available

            BookBean savedBook = bookRepo.save(bookBean);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseWrapper<>().responseOk(Constants.RESPONSE_OK, savedBook));

        } catch (AccessDeniedException ade) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseWrapper<>().responseFail(Constants.USER_FORBIDDEN));
        } catch (AuthenticationException ae) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseWrapper<>().responseFail(Constants.USER_UNAUTHORIZED));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail(Constants.INTERNAL_SERVER_ERROR));
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @Override
    public ResponseEntity getAllBookList(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Received request: {}", request.getRequestURI());

        try {

            List<BookBean> allBooks = bookRepo.findAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseWrapper<>().responseOk(Constants.RESPONSE_OK, allBooks));

        } catch (AccessDeniedException ade) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseWrapper<>().responseFail(Constants.USER_FORBIDDEN));
        } catch (AuthenticationException ae) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseWrapper<>().responseFail(Constants.USER_UNAUTHORIZED));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail(Constants.INTERNAL_SERVER_ERROR));
        }

    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @Override
    public ResponseEntity updateBook(HttpServletRequest request, HttpServletResponse response, AddBookRequest book) {
        logger.info("Received request: {}", request.getRequestURI());

        if (book.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>().responseOk("Book ID cannot be null"));
        }

        // check if the book with the given ID exists
        BookBean existingBook = bookRepo.findById(book.getId()).orElse(null);
        if (existingBook == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>().responseOk("Book not found with the id"));
        }

        // validate request data
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>().responseFail("Title cannot be empty"));
        }
        if (book.getAuthorId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>().responseFail("Author cannot be empty"));
        }
        if (book.getStatus() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>().responseFail("Status cannot be empty"));
        }

        try {
            // update the book with the new values
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(new Author(book.getAuthorId()));
            existingBook.setStatus(book.getStatus());

            // save the updated user
            BookBean updatedBook = bookRepo.save(existingBook);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseWrapper<>().responseOk(Constants.RESPONSE_OK, updatedBook));

        } catch (AccessDeniedException ade) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseWrapper<>().responseFail(Constants.USER_FORBIDDEN));
        } catch (AuthenticationException ae) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseWrapper<>().responseFail(Constants.USER_UNAUTHORIZED));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail(Constants.INTERNAL_SERVER_ERROR));
        }

    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @Override
    public ResponseEntity deleteBook(HttpServletRequest request, HttpServletResponse response, Integer bookId) {
        logger.info("Received request: {}", request.getRequestURI());

        try {

            // check if the book with the given ID exists
            if (bookRepo.existsById(bookId)) {

                // delete book by id
                bookRepo.deleteById(bookId);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseWrapper<>().responseOk("Book deleted successfully"));

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseWrapper<>().responseOk("Book not found with the id"));
            }

        } catch (AccessDeniedException ade) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseWrapper<>().responseFail(Constants.USER_FORBIDDEN));
        } catch (AuthenticationException ae) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseWrapper<>().responseFail(Constants.USER_UNAUTHORIZED));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail(Constants.INTERNAL_SERVER_ERROR));
        }

    }

    @Override
    public ResponseEntity searchBook(HttpServletRequest request, HttpServletResponse response, String searchText) {
        logger.info("Received search request: {}", request.getRequestURI());

        try {
            // search for books by title or author name
            List<BookBean> searchResults = bookRepo.searchBooksByTitleOrAuthorName(searchText);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseWrapper<>().responseOk(Constants.RESPONSE_OK, searchResults));

        } catch (AccessDeniedException ade) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseWrapper<>().responseFail(Constants.USER_FORBIDDEN));
        } catch (AuthenticationException ae) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseWrapper<>().responseFail(Constants.USER_UNAUTHORIZED));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>().responseFail(Constants.INTERNAL_SERVER_ERROR));
        }
    }


}
