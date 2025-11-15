package com.nlu.tai_lieu_xanh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.nlu.tai_lieu_xanh.domain.post.Post;
import com.nlu.tai_lieu_xanh.domain.user.dto.response.tag.TagWithPostsRes;

public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {

  @Query("""
       SELECT NEW com.nlu.tai_lieu_xanh.dto.response.post.MajorWithPostsRes(m.id,m.name,cast(COUNT(p.id) AS integer))
       from Post p join Major m on p.major.id = m.id
       group by m.id,m.name
       order by count(p.id) DESC
      """)
  List<MajorWithPostsRes> findHotMajorsWithPosts();

  long countByMajorId(Integer id);

  @Query("""
          SELECT new com.nlu.tai_lieu_xanh.dto.response.tag.TagWithPostsRes(t.id, t.name, COUNT(p))
          FROM Tag t
          LEFT JOIN t.posts p
          GROUP BY t.id, t.name
          ORDER BY COUNT(p) DESC
      """)
  List<TagWithPostsRes> findTagsSortedByPostCountAsDTO();
}
