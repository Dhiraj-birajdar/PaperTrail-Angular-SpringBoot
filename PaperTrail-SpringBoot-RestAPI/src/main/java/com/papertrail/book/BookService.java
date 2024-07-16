package com.papertrail.book;

import com.papertrail.common.PageResponse;
import com.papertrail.exception.OperationNotPermittedException;
import com.papertrail.file.FileStorageService;
import com.papertrail.history.BookTransactionHistory;
import com.papertrail.history.BookTransactionHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookTransactionHistoryRepository historyRepository;
    private final FileStorageService fileStorageService;

    public Integer saveBook(BookRequest request, Authentication connectedUser) {
//        User user = (User) connectedUser.getPrincipal();
        Book book = bookMapper.toBook(request);
//        book.setOwner(user);
        return bookRepository.save(book).getId();
    }

    public BookResponse findById(Integer id) {
        return bookRepository.findById(id)
                .map(bookMapper::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
    }

    public PageResponse<BookResponse> findAllBooks(Integer page, Integer size, Authentication connectedUser) {
//        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Book> books = bookRepository.findAllDisplaybleBooks(pageable, connectedUser.getName());
        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()

        );
    }

    public PageResponse<BookResponse> findAllBooksByOwner(Integer page, Integer size, Authentication connectedUser) {
//        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Book> books = bookRepository.findAll(BookSpecification.withOwnerId(connectedUser.getName()), pageable);
        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(Integer page, Integer size, Authentication connectedUser) {
//        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<BookTransactionHistory> allBorrowedBooks = historyRepository.findAllBorrowedBooks(pageable, connectedUser.getName());
        List<BorrowedBookResponse> borrowedBookResponses = allBorrowedBooks.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                borrowedBookResponses,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(Integer page, Integer size, Authentication connectedUser) {
//        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<BookTransactionHistory> allBorrowedBooks = historyRepository.findAllReturnedBooks(pageable, connectedUser.getName());
        List<BorrowedBookResponse> borrowedBookResponses = allBorrowedBooks.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                borrowedBookResponses,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

    public Integer updateShareableStatus(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
//        User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(book.getCreatedBy(),connectedUser.getName())) {
            throw new OperationNotPermittedException("You are not the owner of this book, you can't update shareable status");
        }
        book.setShareable(!book.isShareable()); // toggle shareable status
        bookRepository.save(book);
        return book.getId();

    }

    public Integer updateArchivedStatus(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
//        User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(book.getCreatedBy(),connectedUser.getName())) {
            throw new OperationNotPermittedException("You are not the owner of this book, you can't update archived status");
        }
        book.setArchived(!book.isArchived()); // toggle archived status
        bookRepository.save(book);
        return book.getId();
    }

    public Integer borrowBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
        if (!book.isShareable() || book.isArchived()) {
            throw new OperationNotPermittedException("This book is not shareable or archived, you can't borrow it.");
        }
//        User user = (User) connectedUser.getPrincipal();
        if (Objects.equals(book.getCreatedBy(),connectedUser.getName())) {
            throw new OperationNotPermittedException("You are the owner of this book, you can't borrow it.");
        }
        final boolean isBorrowed = historyRepository.isBorrowed(bookId,connectedUser.getName());
        if (isBorrowed) {
            throw new OperationNotPermittedException("Requested book is already borrowed.");
        }
        System.err.println("Borrowing book");
        BookTransactionHistory bookTransactionHistory = BookTransactionHistory.builder()
                .userId(connectedUser.getName())
                .book(book)
                .returned(false)
                .returnApproved(false)
                .build();
        System.err.println("Borrowing book before save");
        return historyRepository.save(bookTransactionHistory).getId();
    }

    public Integer returnBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
        if (!book.isShareable() || book.isArchived()) {
            throw new OperationNotPermittedException("This book is not shareable or archived, you can't return it.");
        }
//        User user = (User) connectedUser.getPrincipal();
        if (Objects.equals(book.getCreatedBy(),connectedUser.getName())) {
            throw new OperationNotPermittedException("You are the owner of this book, you can't return it.");
        }
        BookTransactionHistory bookTransactionHistory = historyRepository.findByBookIdAndUserId(bookId, connectedUser.getName())
                .orElseThrow(()-> new OperationNotPermittedException("You didn't borrow this book."));
        bookTransactionHistory.setReturned(true);
        return historyRepository.save(bookTransactionHistory).getId();
    }

    public Integer approveReturnedBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
        if (!book.isShareable() || book.isArchived()) {
            throw new OperationNotPermittedException("This book is not shareable or archived, you can't approve return.");
        }
//        User user = (User) connectedUser.getPrincipal();
        if (Objects.equals(book.getCreatedBy(),connectedUser.getName())) {
            throw new OperationNotPermittedException("You are not the owner of this book, you can't approve return.");
        }
        BookTransactionHistory bookTransactionHistory = historyRepository.findByBookIdAndOwnerId(bookId, connectedUser.getName())
                .orElseThrow(()-> new OperationNotPermittedException("Book is not returned yet, or you are not the owner of this book, you can't approve return."));
        bookTransactionHistory.setReturnApproved(true);
        return historyRepository.save(bookTransactionHistory).getId();
    }

    public void uploadBookCover(Integer bookId, MultipartFile file, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
//        User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(book.getCreatedBy(),connectedUser.getName())) {
            throw new OperationNotPermittedException("You are not the owner of this book, you can't upload cover image.");
        }
        var bookCover = fileStorageService.saveFile(file, connectedUser.getName());
        book.setBookCover(bookCover);
        bookRepository.save(book);
    }
}
