package com.dados.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dados.domain.Game;
import com.dados.domain.Player;
import com.dados.exceptions.ResourceNotFound;
import com.dados.repository.GameRepository;
import com.dados.repository.PlayerRepository;

@Service
public class GameService {
	
	@Autowired
	GameRepository gameRepository;
	
	@Autowired
	PlayerRepository playerRepository;

	public Game addGame(int id, Game game) throws ResourceNotFound {
		Player player = playerRepository.findById(id).orElseThrow(() -> new ResourceNotFound("There is no player with this id"));
		game.setPlayer(player);
		player.setGame(game);
		gameRepository.save(game);
		
		return game;
		
	}

	public void deleteAll(int id) throws ResourceNotFound {
		Player player = playerRepository.findById(id).orElseThrow(() -> new ResourceNotFound("There is no player with this id"));
		gameRepository.deleteAll(player.getGames());
		player.getGames().clear();
	}
	
	

}
