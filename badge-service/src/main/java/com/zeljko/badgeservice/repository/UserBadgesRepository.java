package com.zeljko.badgeservice.repository;

import com.zeljko.badgeservice.model.UserBadges;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserBadgesRepository extends MongoRepository<UserBadges, String> {
}