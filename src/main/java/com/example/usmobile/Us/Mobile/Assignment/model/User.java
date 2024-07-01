package com.example.usmobile.Us.Mobile.Assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("Users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    String id;
    String firstName;
    String lastName;
    String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
    @Indexed(unique = true)
    String mdn;

    @CreatedDate
    public LocalDateTime createdDate;
    @LastModifiedDate
    public LocalDateTime lastModifiedDate;

}
