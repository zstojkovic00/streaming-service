package com.zeljko.badgeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "user_badges")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserBadges {
    @Id
    String id;
    String userId;
    List<Badge> badges;
}
