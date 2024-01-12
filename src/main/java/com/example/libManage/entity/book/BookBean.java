package com.example.libManage.entity.book;

import com.example.libManage.entity.author.Author;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private Author author;

    public BookBean(Integer id) {
        this.id = id;
    }
}
