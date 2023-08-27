package com.zeljko.badgeservice.repository;

import com.zeljko.badgeservice.model.Badge;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BadgeRepository extends MongoRepository<Badge, Long> {

}
