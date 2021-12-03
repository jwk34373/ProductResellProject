//package com.example.ProductResellProject.domain.users;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Role {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "role_id")
//    private long id;
//
//    @Enumerated(EnumType.STRING)
//    private String type;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_code")
//    private User user;
//
//    public enum RoleType{
//        USER,
//        ADMIN
//    }
//
//    public Role(String type) {
//        this.type = type;
//    }
//
////    public Role(String type, User user) {
////        this.type = type;
////        addUser(user);
////    }
////
////    public void addUser(User user) {
////        this.user = user;
////        user.getRoles().add(this);
////    }
//}
