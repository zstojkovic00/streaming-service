package com.zeljko.badgeservice.repository;

import com.zeljko.badgeservice.model.Badge;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BadgeRepository extends MongoRepository<Badge, String> {

    Optional<Badge> findByName(String name);
}
