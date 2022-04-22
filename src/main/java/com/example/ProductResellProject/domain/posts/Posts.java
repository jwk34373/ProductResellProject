package com.example.ProductResellProject.domain.posts;

import com.example.ProductResellProject.domain.BaseTimeEntity;
import com.example.ProductResellProject.domain.comments.Comment;
import com.example.ProductResellProject.domain.users.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posts_id")
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    private UploadFile uploadFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code")
    private User user;

    @OneToMany(mappedBy = "posts")
    private List<Comment> comments = new ArrayList<>();

    public void addUploadFile(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // == 연관관계 메서드 ==
    public void addUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }


}
