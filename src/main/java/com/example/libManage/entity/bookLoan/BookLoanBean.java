package com.example.libManage.entity.bookLoan;

import com.example.libManage.entity.book.BookBean;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import javax.persistence.*;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book_loan")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookLoanBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date loadDate;
    private Date acceptReturnDate;
    private Date returnedDate;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private BookBean book;

}
