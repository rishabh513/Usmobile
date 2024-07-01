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

import java.time.LocalDateTime;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyUsage {

    @Id
    String id;
    @Indexed
    String mdn;
    @Indexed
    String userId;
    LocalDateTime usageDate;
    double usedInMb;


    @CreatedDate
    public LocalDateTime createdDate;
    @LastModifiedDate
    public LocalDateTime lastModifiedDate;


}
