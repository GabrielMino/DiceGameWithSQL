package com.dados.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.dados.service.*;
import com.fasterxml.jackson.databind.node.TextNode;
import com.dados.domain.*;
import com.dados.exceptions.*;
//import com.dados.exceptions.*;
import com.dados.exceptions.ResourceNotFound;

@Controller
public class RestController {
	
	/*
	 URL's:





- Fase 1
Persistencia: utiliza como base de datos mysql
- Fase 2
Cambia la configuración y utiliza MongoDB para persistir los datos
- Fase 3
Agregar seguridad: incluye autenticación por JWT en todos los accesos a las URL del microservicio.
	 
	 
	 */
	
	@Autowired 
	PlayerService playerService;
	
	@Autowired
	GameService gameService;
	
	//GET: /  devuelve todos los players
	
	@GetMapping("/")
	public ResponseEntity<List<Player>> getPlayers(){
	
	return ResponseEntity.ok(playerService.getPlayers());
	}
	
	//POST: /players : crea un jugador
	
	@PostMapping("/players")
	public ResponseEntity<String> createPlayer(@RequestBody Player player) throws PlayerAlreadyExists{
		playerService.savePlayer(player);
	return ResponseEntity.ok("Player correctly added!");
	
	}
	
	//PUT /players/{id} : modifica el nombre del jugador
	//The name has to be introduced as a json file, an between double quotes(i.e. "Player 4")
	@PutMapping("/players/{id}/")
	public ResponseEntity<Player> modifyPlayerName(@PathVariable(name="id") int id,@RequestBody TextNode name) throws ResourceNotFound, PlayerAlreadyExists{
	
	String newName = name.asText();
	
	return ResponseEntity.ok(playerService.modifyName(id,newName));
	}
	
	//POST /players/{id}/games/ : un jugador específico realiza un tirón de los dados.
	
	@PostMapping("/players/{id}/games")
	public ResponseEntity<Game> addGame(@PathVariable(name="id") int id, @RequestBody Game game ) throws ResourceNotFound{
		Game gameAdded = gameService.addGame(id,game);
		
		return ResponseEntity.ok(gameAdded);
	}
	
	//DELETE /players/{id}/games: elimina las tiradas del jugador
	
	@DeleteMapping("/players/{id}/games")
	public ResponseEntity<String> deleteGames(@PathVariable(name="id") int id) throws ResourceNotFound{
		
		gameService.deleteAll(id);
		
		return ResponseEntity.ok("Games correctly deleted!");	
	}
	
	//GET /players/: devuelve el listado de todos los jugadores del sistema con su porcentaje medio de éxitos
	@GetMapping("/players/")
	public ResponseEntity<Map<String,Double>> averageWinRate(){
		
		return ResponseEntity.ok(playerService.averageWinRate());	
	}
	
	//GET /players/{id}/games: devuelve el listado de jugadas por un jugador.
	@GetMapping("/players/{id}/games")
	public ResponseEntity<List<Game>> getGamesByPlayer(@PathVariable(name="id") int id) throws ResourceNotFound{
		
		return ResponseEntity.ok(playerService.getGames(id));	
	
	}
	
	//GET /players/ranking: devuelve el ranking medio de todos los jugadores del sistema . Es decir, el porcentaje medio de logros.
	@GetMapping("/players/ranking")
	public ResponseEntity<LinkedList<String>> getRanking(){
		
		return ResponseEntity.ok(playerService.getRanking());	
	
	}
	
	//GET /players/ranking/loser: devuelve al jugador con peor porcentaje de éxito
	@GetMapping("/players/ranking/loser")
	public ResponseEntity<String> getRankingLoser(){
		System.out.println();
		return ResponseEntity.ok(playerService.getRanking().getLast());
	}
	
	//GET /players/ranking/winner: devuelve al jugador con peor porcentaje de éxito
	
	@GetMapping("/players/ranking/winner")
	public ResponseEntity<String> getRankingWinner(){
		System.out.println();
		return ResponseEntity.ok(playerService.getRanking().getFirst());
	}
	
	
	
	
}
