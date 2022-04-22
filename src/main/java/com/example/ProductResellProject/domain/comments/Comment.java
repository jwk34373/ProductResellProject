package com.example.ProductResellProject.domain.comments;

import com.example.ProductResellProject.Post.domain.Post;
import com.example.ProductResellProject.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String name;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id")
    private Post posts;

    public Comment(String name, String content, Post posts) {
        this.name = name;
        this.content = content;
        this.posts = posts;
    }

    private void addPosts(Post posts){
        this.posts = posts;
        posts.getComments().add(this);
    }
}
