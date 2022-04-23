package com.example.ProductResellProject.Post.domain;

import com.example.ProductResellProject.common.BaseTimeEntity;
import com.example.ProductResellProject.domain.comments.Comment;
import com.example.ProductResellProject.user.domain.User;
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
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posts_id")
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @ElementCollection
    @CollectionTable(name = "upload_files",
            joinColumns = @JoinColumn(name = "post_id"))
    private List<UploadFile> uploadFiles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code")
    private User user;

    @OneToMany(mappedBy = "posts")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(String title, String content, String author, List<UploadFile> uploadFiles) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.uploadFiles = uploadFiles;
    }

//    // == 연관관계 메서드 ==
//    public void addUser(User user) {
//        this.user = user;
//        user.getPosts().add(this);
//    }


}
