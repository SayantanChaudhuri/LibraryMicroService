package com.sayantan.books.services;

import com.sayantan.library.model.Book;
import com.sayantan.library.model.BookEntity;
import com.sayantan.library.model.Books;
import com.sayantan.library.repository.BookRepository;
import com.sayantan.library.services.BooksServiceImpl;
import com.sayantan.library.util.BookUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
public class BookServiceImplIntegrationTest {

    @Autowired
    private BooksServiceImpl booksService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private Page<BookEntity> bookEntities;

    private Pageable pageable;

    @MockBean
    private Book book;

    @MockBean
    private Books books;

    @MockBean
    private BookEntity bookEntity;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private BookUtil bookUtil;

    @Before
    public void setUp() {
        pageable = PageRequest.of(0, 10, Sort.by("bookName"));

        Mockito.when(bookRepository.findAll(pageable))
                .thenReturn(bookEntities);

        Mockito.when(bookEntities.getTotalElements())
                .thenReturn(5L);

        Mockito.when(bookRepository.save(bookEntity))
                .thenReturn(bookEntity);

        Mockito.when(bookUtil.convertToEntity(book))
                .thenReturn(bookEntity);
    }

//    @Ignore
    @Test
    public void whenGetAll_thenAllEntitiesReturn() {
        assertThat("whenGetAll_thenAllEntitiesFound",
                bookRepository.findAll(pageable).getTotalElements(),
                equalTo(5L));
    }

//    @Ignore
    @Test
    public void whenSave_thenSavedEntitityReturn() {
        assertThat("whenGetAll_thenAllEntitiesFound",
                bookRepository.save(bookEntity),
                equalTo(bookEntity));
    }
}