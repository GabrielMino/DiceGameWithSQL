package com.dados.service;


import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dados.domain.Game;
import com.dados.domain.Player;
import com.dados.repository.*;
import com.dados.exceptions.*;


@Service
public class PlayerService {
	
	@Autowired
	GameRepository gameRepository;
	
	@Autowired
	PlayerRepository playerRepository;


	public List<Player> getPlayers() {
		 return playerRepository.findAll();
	}

	public void savePlayer(Player player) throws PlayerAlreadyExists {
		
		Date now = new Date(); // This object contains the current date value
		if (player.getName()=="" || player.getName()=="ANONYMOUS" || player.getName()==null) {
			player.setDate(now);
			player.setName("ANONYMOUS");
			playerRepository.save(player);
		} else {
		boolean unique = playerRepository.existsByName(player.getName());
		if (unique)  throw new PlayerAlreadyExists("Player with name "+ player.getName() +" ,already exists!");
		
		player.setDate(now);
		playerRepository.save(player);
		}
		
	}

	public Player modifyName(int id,String name) throws ResourceNotFound, PlayerAlreadyExists {
		
		Player player = playerRepository.findById(id).orElseThrow(() -> new ResourceNotFound("There is no player with this id"));
		boolean unique = playerRepository.existsByName(name);
		if (unique)  throw new PlayerAlreadyExists("Player with name "+ name+" ,already exists!");
		if (name != null && name.length()>0 && !Objects.equals(player.getName(),name)){
			player.setName(name);
			playerRepository.save(player);
			
			}
		return player;	
	}

	public Map<String, Double> averageWinRate() {
		List<Player> players=playerRepository.findAll();
		HashMap<String,Double> result = new HashMap <String,Double>();
		for (Player player : players) {
			result.put(player.getName(), DoubleRounder.round(player.winsRate(),2));
			}
			return result;
	}

	public List<Game> getGames(int id) throws ResourceNotFound {
		Player player = playerRepository.findById(id).orElseThrow(() -> new ResourceNotFound("There is no player with this id"));
		if(player.getGames().isEmpty()) throw new ResourceNotFound("There is no game for this player");
		else return player.getGames();
		
	}

	public LinkedList<String> getRanking() {
		List<Player> players = playerRepository.findAll();
		LinkedList<String> result = new LinkedList<String>();
		double avgToCompare = -1;
		for (Player player: players) {
			if (player.winsRate()>avgToCompare) {
				//Inserts the specified element at the beginning of this list.
				result.addFirst(player.getName());}
			else {
				//Appends the specified element to the end of this list.
				result.addLast(player.getName());	
			}
			avgToCompare=player.winsRate();		
		}
		return result;	
	}
	
	
	
	

}
