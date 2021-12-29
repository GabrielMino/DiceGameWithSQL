# DiceGameWithSQL

Description


This is your final project, a 100% API designed by you where you will apply everything you know so far to create a complete application, from database to security. Apply everything you know even what is not asked.

Level 1

The dice game is played with two dice. In case the result of the sum of the two dice is 7, the game is won, otherwise it is lost. A player can see a list of all the rolls he has made and the success rate.

In order to play the game and make a roll, a user must register with a non-repeated name. When created, it is assigned a unique numeric identifier and a registration date. If the user so wishes, you may not add any name and it will be called "ANONYMOUS". There can be more than one "ANONYMOUS" player.

Each player can see a list of all the rolls they have made, with the value of each dice and whether or not the game has been won. In addition, you can know your success rate for all the rolls you have made.

You can't delete a particular game, but you can delete the entire list of rolls per player.

The software should be able to list all the players in the system, the success rate of each player and the average success rate of all the players in the system.

The software must respect the main design patterns.

NOTES

Please note the following construction details:

URL’s:
POST: / players: create a player
PUT / players: Change the player's name
POST / players / {id} / games /: A specific player rolls the dice.
DELETE / players / {id} / games - removes player rolls
GET / players /: Returns the list of all players in the system with their average success rate
GET / players / {id} / games: Returns the list of plays by a player.
GET / players / ranking: returns the average ranking of all players in the system. That is, the average success rate.
GET / players / ranking / loser - returns the player with the highest success rate
GET / players / ranking / winner: returns the player with the highest success rate
- Phase 1
Persistence: used as mysql database
