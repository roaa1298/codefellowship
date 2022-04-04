package com.example.CodeFellowshipPro.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private ApplicationUser applicationUser;

    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd||HH:mm:ss")
    private LocalDateTime createdAt;

    private String body;

    public Post() {
    }

    public Post(ApplicationUser applicationUser, String body ) {
        this.applicationUser = applicationUser;
        this.body = body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getBody() {
        return body;
    }

    public Long getId() {
        return id;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }
}
