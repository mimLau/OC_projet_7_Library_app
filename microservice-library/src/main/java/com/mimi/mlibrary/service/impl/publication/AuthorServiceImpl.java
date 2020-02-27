package com.mimi.mlibrary.service.impl.publication;

import com.mimi.mlibrary.dao.publication.AuthorDao;
import com.mimi.mlibrary.model.source.publication.Author;
import com.mimi.mlibrary.service.publication.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public List<Author> findAll() {
        return authorDao.findAll();
    }
}