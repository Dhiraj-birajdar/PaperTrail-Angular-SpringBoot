package com.papertrail.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Integer>{

    @Query("""
            SELECT h
            FROM BookTransactionHistory h
            WHERE h.user.id = :userId
            """)
    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Integer userId);

     @Query("""
            SELECT h
            FROM BookTransactionHistory h
            WHERE h.book.owner.id = :userId
            """)
    Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, Integer userId);

    @Query("""
            SELECT (COUNT(h) > 0) AS isBorrowed
            FROM BookTransactionHistory h
            WHERE h.book.id = :bookId
            AND h.user.id = :userId
            AND h.returnApproved = false
            """)
    boolean isBorrowed(Integer bookId, Integer userId);

    @Query("""
            SELECT transaction
            FROM BookTransactionHistory transaction
            WHERE transaction.book.id = :bookId
            AND transaction.user.id = :userId
            AND transaction.returned = false
            AND transaction.returnApproved = false
            """)
    Optional<BookTransactionHistory> findByBookIdAndUserId(Integer bookId, Integer userId);

@Query("""
            SELECT transaction
            FROM BookTransactionHistory transaction
            WHERE transaction.book.owner.id = :bookId
            AND transaction.user.id = :userId
            AND transaction.returned = true
            AND transaction.returnApproved = false
            """)
    Optional<BookTransactionHistory>  findByBookIdAndOwnerId(Integer bookId, Integer userId);
}
