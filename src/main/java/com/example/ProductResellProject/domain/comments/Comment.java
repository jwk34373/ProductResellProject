package com.example.ProductResellProject.domain.comments;

import com.example.ProductResellProject.domain.BaseTimeEntity;
import com.example.ProductResellProject.domain.posts.Posts;
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
    private Posts posts;

    public Comment(String name, String content, Posts posts) {
        this.name = name;
        this.content = content;
        this.posts = posts;
    }

    private void addPosts(Posts posts){
        this.posts = posts;
        posts.getComments().add(this);
    }
}
