package application;

import java.util.ArrayList;

public class Game {

	private int currRound;
	private ArrayList<Team> teams;
	private ArrayList<Nation> availableNations;

	public Game() {
		currRound = 1;
	}

	public void addNation(Nation n) {
		availableNations.add(n);
	}

}
