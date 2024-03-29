package com.example.libManage.repo.book;

import com.example.libManage.entity.book.BookBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BookRepo extends JpaRepository<BookBean, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE BookBean b SET b.status = :newStatus WHERE b.id = :bookId")
    void updateBookStatus(@Param("bookId") Integer bookId, @Param("newStatus") Integer newStatus);

    @Query("SELECT b FROM BookBean b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :searchText, '%')) OR LOWER(b.author.name) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<BookBean> searchBooksByTitleOrAuthorName(@Param("searchText") String searchText);

}
