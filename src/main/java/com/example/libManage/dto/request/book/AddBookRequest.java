package com.example.libManage.dto.request.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddBookRequest {

    private Integer id;
    private String title;
    private Integer authorId;
    private Integer status;

    public AddBookRequest(String title, Integer authorId) {
        this.title = title;
        this.authorId = authorId;
    }
}
