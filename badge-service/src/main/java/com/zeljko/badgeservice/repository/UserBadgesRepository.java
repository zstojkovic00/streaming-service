package com.zeljko.badgeservice.repository;

import com.zeljko.badgeservice.model.Badge;
import com.zeljko.badgeservice.model.UserBadges;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserBadgesRepository extends MongoRepository<UserBadges, String> {

    Optional<UserBadges> findByUserId(String userId);

}