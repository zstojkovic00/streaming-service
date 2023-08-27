package com.zeljko.badgeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "badge")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Badge {
    private Long id;
    private String name;
    private boolean earned;
}
