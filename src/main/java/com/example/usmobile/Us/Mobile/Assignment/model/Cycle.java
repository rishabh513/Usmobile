package com.example.usmobile.Us.Mobile.Assignment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cycle {

    @Id
    String id;
    @Indexed
    String mdn;
    LocalDate startDate;
    LocalDate endDate;
    @Indexed
    String userId;

    @CreatedDate
    public LocalDateTime createdDate;
    @LastModifiedDate
    public LocalDateTime lastModifiedDate;
}
