package com.example.trellox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Board {
    @Id
    private String id;
    private String boardName;
    @LastModifiedDate
    private Instant modifiedOn;
    @CreatedDate
    private Instant createdOn;
    @CreatedBy
    private String creatorId;
}
