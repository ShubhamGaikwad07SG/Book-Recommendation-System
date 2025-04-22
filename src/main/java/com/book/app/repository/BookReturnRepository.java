package com.book.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.app.model.BookReturn;
import com.book.app.model.User;

@Repository
public interface BookReturnRepository extends JpaRepository<BookReturn, Integer> {
    List<BookReturn> findByUser(User user);
    List<BookReturn> findByStatus(String string);

}
