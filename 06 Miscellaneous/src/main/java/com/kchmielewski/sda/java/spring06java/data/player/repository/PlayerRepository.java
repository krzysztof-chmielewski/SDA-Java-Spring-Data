package com.kchmielewski.sda.java.spring06java.data.player.repository;

import com.kchmielewski.sda.java.spring06java.data.player.entity.PlayerEntity;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer> {
    List<PlayerEntity> findAllByName(String name);

    @Query("SELECT p FROM PlayerEntity p WHERE surname = 'Lallana'")
    List<PlayerEntity> findLallana();

    @Query("SELECT p FROM PlayerEntity p WHERE surname LIKE ?1%")
    List<PlayerEntity> findBySurnameStartingWith(String surname);
}
