package application;

import java.util.ArrayList;

public class Game {

	private int currRound;
	private ArrayList<Player> allPlayers;
	private ArrayList<Nation> availableNations;

	public Game() {
		currRound = 1;
		// teams = new ArrayList<Team>();
		availableNations = new ArrayList<Nation>();
	}

	public void addNation(Nation n) {
		availableNations.add(n);
	}

	public ArrayList<Nation> getNations() {
		return availableNations;
	}

	public void addPlayer(Player p) {
		allPlayers.add(p);
	}

}
