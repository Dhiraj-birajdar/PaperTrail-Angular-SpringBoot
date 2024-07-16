package com.papertrail.feedback;


import com.papertrail.book.Book;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {
    public Feedback toFeedback(FeedbackRequest feedbackRequest) {
        return Feedback.builder()
                .note(feedbackRequest.note())
                .comment(feedbackRequest.comment())
                .book(Book.builder()
                        .id(feedbackRequest.bookId())
                        .build()
                )
                .build();
    }

    public FeedbackResponse toFeedbackResponse(Feedback f, String id) {
        return FeedbackResponse.builder()
                .note(f.getNote())
                .comment(f.getComment())
                .ownFeedback(f.getCreatedBy().equals(id))
                .build();
    }
}
