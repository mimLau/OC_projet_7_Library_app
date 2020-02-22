package com.mimi.mlibrary.dao;

import com.mimi.mlibrary.model.publications.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationDao extends JpaRepository<Publication, Integer> {

   @Query("SELECT p FROM Publication p WHERE p.isbn= :isbn")
   Publication searchPublicationByIsbn(@Param("isbn") String isbn );

   @Query("SELECT p FROM Publication p WHERE p.author.name LIKE %:name%")
   List<Publication> searchPublicationsByAuthorName(@Param("name") String name );

   @Query("SELECT p FROM Publication p WHERE p.title LIKE %:title%")
   List<Publication> searchPublicationsByTitle(@Param("title") String title );


}