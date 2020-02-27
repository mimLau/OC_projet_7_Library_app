package com.mimi.mlibrary.dao.publication;

import com.mimi.mlibrary.model.source.publication.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AuthorDao extends JpaRepository<Author, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Author a WHERE a.id= :id")
    void deleteAuthorById( @Param("id") int id );
}
