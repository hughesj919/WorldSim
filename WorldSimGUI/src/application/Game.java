package application;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable{


	private static final long serialVersionUID = -243860625637606695L;
	private String name;
	private int currRound;
	private ArrayList<Player> allPlayers;
	private ArrayList<Nation> availableNations;
	
	public Game() {
		
		currRound = 1;
		// teams = new ArrayList<Team>();
		availableNations = new ArrayList<Nation>();
	}

	public Game(String n) {
		name = n;
		currRound = 1;
		// teams = new ArrayList<Team>();
		availableNations = new ArrayList<Nation>();
	}
	
	@Override public String toString(){
		return name;
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
