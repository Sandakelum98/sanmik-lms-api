package com.example.libManage.repo.bookLoan;

import com.example.libManage.entity.bookLoan.BookLoanBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


public interface BookLoanRepo extends JpaRepository<BookLoanBean, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE BookLoanBean b SET b.returnedDate=:returnDate WHERE b.id=:loanId")
    void updateBookReturnedDate(@Param("returnDate") Date returnDate, @Param("loanId") Integer loanId);

    BookLoanBean findByBookIdAndReturnedDateIsNull(Integer bookId);
}
