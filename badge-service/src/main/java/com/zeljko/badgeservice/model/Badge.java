package com.zeljko.badgeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "badge")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Badge {
    @Id
    private String id;
    private String name;
    private String description;
    private Binary image;
}
