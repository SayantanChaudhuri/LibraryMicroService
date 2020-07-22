package com.sayantan.library.repository;

import com.sayantan.library.model.BookEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<BookEntity, Long> {
}
