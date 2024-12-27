package com.nlu.tai_lieu_xanh.dto.response.post;

import com.nlu.tai_lieu_xanh.model.Post;
import com.nlu.tai_lieu_xanh.model.PostStatus;
import com.nlu.tai_lieu_xanh.model.Tag;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class PostSpecification {
    public static Specification<Post> isNotDeleted() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get("postStatus"), PostStatus.DELETED);
    }

    public static Specification<Post> isPublished() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("postStatus"), PostStatus.PUBLISHED);
    }

    public static Specification<Post> isDeleted() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("postStatus"), PostStatus.DELETED);
    }

    public static Specification<Post> hasMajorId(Integer majorId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("major").get("id"), majorId);
    }

    public static Specification<Post> hasTags(List<String> tags) {
        return (root, query, criteriaBuilder) -> {
            Join<Post, Tag> tagJoin = root.join("tags");
            return tagJoin.get("name").in(tags);
        };
    }

    public static Specification<Post> hasKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            String likeKeyword = "%" + keyword.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), likeKeyword),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likeKeyword)
            );
        };
    }

    public static Specification<Post> hasFileType(String fileType) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("doc").get("fileType"), fileType);
    }

    public static Specification<Post> isNotId(Integer postId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get("id"), postId);
    }

    public static Specification<Post> isReview() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("postStatus"), PostStatus.REVIEWING);
    }

}
