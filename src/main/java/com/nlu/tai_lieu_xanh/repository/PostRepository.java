package com.nlu.tai_lieu_xanh.repository;

import com.nlu.tai_lieu_xanh.dto.response.post.MajorWithPostsRes;
import com.nlu.tai_lieu_xanh.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {

    @Query(
            """
                     SELECT NEW com.nlu.tai_lieu_xanh.dto.response.post.MajorWithPostsRes(m.id,m.name,cast(COUNT(p.id) AS integer))
                     from Post p join Major m on p.major.id = m.id
                     group by m.id,m.name
                     order by count(p.id) DESC
                    """
    )
    List<MajorWithPostsRes> findHotMajorsWithPosts();

}
