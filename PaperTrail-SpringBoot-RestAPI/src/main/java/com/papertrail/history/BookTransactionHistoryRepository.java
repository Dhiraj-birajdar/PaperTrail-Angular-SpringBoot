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
            WHERE h.userId = :userId
            """)
    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, String userId);

     @Query("""
            SELECT h
            FROM BookTransactionHistory h
            WHERE h.book.createdBy = :userId
            """)
    Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, String userId);

    @Query("""
            SELECT (COUNT(h) > 0) AS isBorrowed
            FROM BookTransactionHistory h
            WHERE h.book.id = :bookId
            AND h.userId = :userId
            AND h.returnApproved = false
            """)
    boolean isBorrowed(Integer bookId, String userId);

    @Query("""
            SELECT transaction
            FROM BookTransactionHistory transaction
            WHERE transaction.book.id = :bookId
            AND transaction.userId = :userId
            AND transaction.returned = false
            AND transaction.returnApproved = false
            """)
    Optional<BookTransactionHistory> findByBookIdAndUserId(Integer bookId, String userId);

@Query("""

        SELECT transaction
                        FROM BookTransactionHistory  transaction
                        WHERE transaction.book.createdBy = :userId
                        AND transaction.book.id = :bookId
                        AND transaction.returned = true
                        AND transaction.returnApproved = false
        """)
    Optional<BookTransactionHistory>  findByBookIdAndOwnerId(Integer bookId, String userId);
}
