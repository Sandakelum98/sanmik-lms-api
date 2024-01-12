package com.example.libManage.dto.request.bookLoan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddBookLoanRequest {

    private Integer bookId;

}
