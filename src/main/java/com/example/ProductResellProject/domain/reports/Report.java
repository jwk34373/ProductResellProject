package com.example.ProductResellProject.domain.reports;

import com.example.ProductResellProject.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    private String reporterId;
    private String reportedId;
    private String content;
    private Long postId;
    private String reporterStatus; // 이게뭐더라??

    @Builder
    public Report(String reporterId, String reportedId, String content, Long postId, String reporterStatus) {
        this.reporterId = reporterId;
        this.reportedId = reportedId;
        this.content = content;
        this.postId = postId;
        this.reporterStatus = reporterStatus;
    }
}
