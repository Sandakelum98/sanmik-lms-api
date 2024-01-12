package com.example.libManage.service.impl;

import com.example.libManage.constants.Constants;
import com.example.libManage.dto.request.bookLoan.AddBookLoanRequest;
import com.example.libManage.entity.book.BookBean;
import com.example.libManage.entity.bookLoan.BookLoanBean;
import com.example.libManage.repo.book.BookRepo;
import com.example.libManage.repo.bookLoan.BookLoanRepo;
import com.example.libManage.service.bookLoan.BookLoanService;
import com.example.libManage.utility.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BookLoanServiceImpl implements BookLoanService {

    private final BookLoanRepo bookLoanRepo;

    private final BookRepo bookRepo;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @Override
    public ResponseEntity loanBook(HttpServletRequest request, HttpServletResponse response, AddBookLoanRequest loanDetails) {
        logger.info("Received request: {}", request.getRequestURI());

        // validate data
        if (loanDetails.getBookId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseWrapper<>().responseFail("Book cannot be empty"));
        }

        try {

            BookLoanBean bookLoanBean = new BookLoanBean();
            bookLoanBean.setLoadDate(new Date());
            bookLoanBean.setBook(new BookBean(loanDetails.getBookId()));

            // set acceptReturnDate to today's date plus 20 days
            LocalDate currentDate = LocalDate.now();
            LocalDate acceptReturnDate = currentDate.plus(Period.ofDays(20));
            bookLoanBean.setAcceptReturnDate(java.sql.Date.valueOf(acceptReturnDate));

            // save loan details
            BookLoanBean saveLoanDetails = bookLoanRepo.save(bookLoanBean);

            // update book status
            bookRepo.updateBookStatus(loanDetails.getBookId(), 1); // 1 -> on-loan

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseWrapper<>().responseOk(Constants.RESPONSE_OK, saveLoanDetails));

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
    public ResponseEntity returnBook(HttpServletRequest request, HttpServletResponse response, Integer bookId) {
        logger.info("Received request: {}", request.getRequestURI());

        try {

            // check if the book with the given ID exists
            BookBean existingBook = bookRepo.findById(bookId).orElse(null);
            if (existingBook == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseWrapper<>().responseOk("Book not found with the id"));
            }

            // check book status
            if (existingBook.getStatus() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseWrapper<>().responseFail("Cannot return the book because it is already available"));
            }

            BookLoanBean bookLoan = bookLoanRepo.findByBookIdAndReturnedDateIsNull(bookId);

            if (bookLoan == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseWrapper<>().responseFail("No active loan found for the book"));
            }

            // update the returnedDate
            bookLoan.setReturnedDate(new Date());
            bookLoanRepo.save(bookLoan);

            // update book status
            bookRepo.updateBookStatus(bookId, 0);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseWrapper<>().responseOk("Book returned successfully"));

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
