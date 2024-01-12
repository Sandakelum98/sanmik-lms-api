package com.example.libManage.repo.author;

import com.example.libManage.entity.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Integer> {
}
