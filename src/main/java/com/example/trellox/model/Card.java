package com.example.trellox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Card {
    @Id
    private String id;
    private String cardTitle;
    private String boardId;
    private Set<String> members;
    @LastModifiedDate
    private Instant modifiedOn;
    @CreatedDate
    private Instant createdOn;
    @CreatedBy
    private String creatorId;
}
