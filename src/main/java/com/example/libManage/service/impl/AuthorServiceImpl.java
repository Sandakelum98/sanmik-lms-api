package com.example.libManage.service.impl;

import com.example.libManage.constants.Constants;
import com.example.libManage.entity.author.Author;
import com.example.libManage.repo.author.AuthorRepo;
import com.example.libManage.service.author.AuthorService;
import com.example.libManage.utility.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepo authorRepo;
    private final Logger logger = LogManager.getLogger(getClass());

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Override
    public ResponseEntity saveAuthor(HttpServletRequest request, HttpServletResponse response, Author author) {
        logger.info("Received request: {}", request.getRequestURI());

        try {

            // save author
            Author newAuthor = authorRepo.save(author);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseWrapper<>().responseOk(Constants.RESPONSE_OK, newAuthor));

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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Override
    public ResponseEntity getAllAuthors(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Received request: {}", request.getRequestURI());

        try {

            List<Author> allAuthors = authorRepo.findAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseWrapper<>().responseOk(Constants.RESPONSE_OK, allAuthors));

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
