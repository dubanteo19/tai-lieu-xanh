package com.nlu.tai_lieu_xanh.service;

import com.nlu.tai_lieu_xanh.dto.request.comment.CommentCreateReq;
import com.nlu.tai_lieu_xanh.dto.request.comment.CommentDeleteReq;
import com.nlu.tai_lieu_xanh.dto.request.comment.CommentUpdateReq;
import com.nlu.tai_lieu_xanh.dto.response.comment.CommentRes;
import com.nlu.tai_lieu_xanh.mapper.CommentMapper;
import com.nlu.tai_lieu_xanh.model.Comment;
import com.nlu.tai_lieu_xanh.model.CommentStatus;
import com.nlu.tai_lieu_xanh.model.User;
import com.nlu.tai_lieu_xanh.repository.CommentRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentService {
    CommentRepository commentRepository;
    PostService postService;
    UserService userService;
    CommentMapper commentMapper = CommentMapper.INSTANCE;

    public List<CommentRes> getAllCommentsByPostId(Integer postId) {
        var post = postService.findById(postId);
        var comments = post.getComments();
        return comments.stream().map(commentMapper::toCommentRes).collect(Collectors.toList());
    }

    public CommentRes saveComment(Integer postId, CommentCreateReq commentCreateReq) {
        if (!postId.equals(commentCreateReq.postId())) {
            throw new IllegalArgumentException("postId is not match");
        }
        var user = userService.findById(commentCreateReq.userId());
        var post = postService.findById(postId);
        var comment = new Comment();
        comment.setContent(commentCreateReq.content());
        comment.setPost(post);
        comment.setUser(user);
        return commentMapper.toCommentRes(commentRepository.save(comment));
    }

    public CommentRes updateComment(Integer postId, CommentUpdateReq req) {
        if (!postId.equals(req.postId())) {
            throw new IllegalArgumentException("postId is not match");
        }
        var user = userService.findById(req.userId());
        if (!user.getId().equals(req.userId())) {
            throw new IllegalArgumentException("userId is not match");
        }
        var currentComment = commentRepository
                .findById(req.commentId()).orElseThrow(() -> new IllegalArgumentException("comment not found"));
        currentComment.setContent(req.content());
        return commentMapper.toCommentRes(commentRepository.save(currentComment));
    }

    public void deleteComment(Integer commentId) {
        var comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("comment not found"));
        comment.setStatus(CommentStatus.DELETED);
        commentRepository.save(comment);
    }

    public void deleteComment(Integer postId, CommentDeleteReq req) {
        if (!postId.equals(req.postId())) {
            throw new IllegalArgumentException("postId is not match");
        }
        var user = userService.findById(req.userId());
        if (!user.getId().equals(req.userId())) {
            throw new IllegalArgumentException("userId is not match");
        }
        var comment = commentRepository.findById(req.commentId()).orElseThrow(() -> new IllegalArgumentException("comment not found"));
        commentRepository.deleteById(comment.getId());
    }

    public List<CommentRes> getAllComments() {
        return commentRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"))
                .stream().map(commentMapper::toCommentRes).collect(Collectors.toList());
    }
}
