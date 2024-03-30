package com.school.commentmanager.controller;

import com.school.commentmanager.model.Comment;
import com.school.commentmanager.repo.CommentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping
    public List<Comment> getAllComments() {
        Iterable<Comment> iterableComments = commentRepository.findAll();
        List<Comment> result = new ArrayList<>();
        iterableComments.iterator().forEachRemaining(result::add);
        return result;
    }

    @PostMapping
    public void addComment(@RequestBody Comment comment) {
        commentRepository.save(comment);
    }

    @PutMapping("/{id}/admin")
    public void updateComment(@PathVariable("id") String id, @RequestBody Comment comment) {
        commentRepository.findById(UUID.fromString(id)).ifPresent(oldComment -> {
            oldComment.setAuthor(comment.getAuthor());
            oldComment.setComment(comment.getComment());
            commentRepository.save(oldComment);
        });
    }

    @DeleteMapping("/{id}/admin")
    public void deleteCommentById(@PathVariable("id") String id) {
        commentRepository.deleteById(UUID.fromString(id));
    }
}
