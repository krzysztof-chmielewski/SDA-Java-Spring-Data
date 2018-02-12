package com.kchmielewski.sda.java.spring05java.data.player.service;

import com.kchmielewski.sda.java.spring05java.data.player.entity.PlayerEntity;
import com.kchmielewski.sda.java.spring05java.data.player.entity.TeamEntity;
import com.kchmielewski.sda.java.spring05java.data.player.model.Player;
import com.kchmielewski.sda.java.spring05java.data.player.repository.PlayerRepository;
import com.kchmielewski.sda.java.spring05java.data.player.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class PlayerService {
    private final PlayerRepository repository;
    private final TeamRepository teamRepository;

    public PlayerService(PlayerRepository repository, TeamRepository teamRepository) {
        this.repository = repository;
        this.teamRepository = teamRepository;
    }

    public List<Player> findByName(String name) {
        return toPlayerDto(repository.findAllByName(name));
    }

    public List<Player> findLallana() {
        return toPlayerDto(repository.findLallana());
    }

    public List<Player> findBySurnameStartingWith(String surname) {
        checkNotNull(surname, "Surname cannot be null");
        return toPlayerDto(repository.findBySurnameStartingWith(surname));
    }

    public List<Player> findAll() {
        return toPlayerDto(repository.findAll());
    }

    public Optional<Player> findOne(Integer playerId) {
        return repository.findById(playerId).map(this::toPlayerDto);
    }

    public void add(Player player) {
        checkNotNull(player, "Player cannot be null");
        checkArgument(player.getId() == null, "If player is to be added, it needs it's id to be null but is %s.", player.getId());
        repository.save(toEntity(player));
    }

    public void remove(Integer playerId) {
        checkNotNull(playerId, "Player id cannot be null");
        repository.deleteById(playerId);
    }

    public void edit(Player player) {
        checkNotNull(player, "Player cannot be null");
        checkArgument(player.getId() != null, "If player is to be edited, it needs it's id to be set.");

        Optional<TeamEntity> team = teamRepository.findByName(player.getTeamName());
        PlayerEntity entity = toEntity(player);
        team.ifPresent(entity::setTeam);
        if (!team.isPresent()) {
            TeamEntity teamEntity = new TeamEntity(player.getTeamName());
            teamRepository.save(teamEntity);
            entity.setTeam(teamEntity);
        }

        repository.save(entity);
    }


    private PlayerEntity toEntity(Player player) {
        return new PlayerEntity(player.getId(), player.getName(), player.getSurname());
    }

    private Player toPlayerDto(PlayerEntity entity) {
        return new Player(entity.getId(), entity.getName(), entity.getSurname(),
                entity.getTeam() == null ? "" : entity.getTeam().getName());
    }

    private List<Player> toPlayerDto(List<PlayerEntity> entities) {
        return entities.stream().map(this::toPlayerDto).collect(Collectors.toList());
    }
}
