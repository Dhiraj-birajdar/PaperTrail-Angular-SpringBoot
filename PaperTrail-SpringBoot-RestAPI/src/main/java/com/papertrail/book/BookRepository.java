package com.papertrail.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

    @Query("SELECT b " +
            "FROM Book b " +
            "WHERE b.archived = false " +
            "AND b.shareable = true " +
            "AND b.createdBy != :id")
//            "AND b.owner.id != ?2") // This is the same as the above query ?2 indicates the second parameter
    Page<Book> findAllDisplaybleBooks(Pageable pageable, String id);
}
