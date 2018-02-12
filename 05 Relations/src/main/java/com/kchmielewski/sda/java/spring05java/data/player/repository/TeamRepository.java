package com.kchmielewski.sda.java.spring05java.data.player.repository;

import com.kchmielewski.sda.java.spring05java.data.player.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {
    Optional<TeamEntity> findByName(String name);
}
