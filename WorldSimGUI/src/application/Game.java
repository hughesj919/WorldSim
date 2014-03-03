package application;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable{


	private static final long serialVersionUID = -243860625637606695L;
	private String name;
	private String saveName;
	private int currRound;
	private ArrayList<Player> allPlayers;
	private ArrayList<Nation> availableNations;
	private ArrayList<Round> allRounds;
	
	public Game(String n) {
		name = n;
		currRound = 1;
		availableNations = new ArrayList<Nation>();
		allPlayers = new ArrayList<Player>();
		allRounds = new ArrayList<Round>();
		importNations();
	}
	
	@Override public String toString(){
		return name;
	}

	public void addNation(Nation n) {
		availableNations.add(n);
	}
	
	public void setNation(ArrayList<Nation> n) {
		availableNations = n;
	}
	
	public void setSaveName(String filename){
		saveName = filename;
	}
	
	public String getName(){
		return name;		
	}
	
	public String getSaveName(){
		return saveName;
	}
	
	public ArrayList<Nation> getNations() {
		return availableNations;
	}
	
	public ArrayList<Player> getPlayers(){
		return allPlayers;
	}

	public void addPlayer(Player p) {
		allPlayers.add(p);
	}
	
	public void removePlayer(Player p){
		allPlayers.remove(p);
	}
	
	public boolean saveGame(String filename){
		setSaveName(filename);
		FileOutputStream fos = null;
	    ObjectOutputStream out = null;
	    try {
	      fos = new FileOutputStream(filename);
	      out = new ObjectOutputStream(fos);
	      out.writeObject(this);
	      out.close();
	      return true;
	    } 
	    catch (Exception ex) {
	      ex.printStackTrace();
	      return false;
	    }
	}
	
	public boolean saveGame(){
		FileOutputStream fos = null;
	    ObjectOutputStream out = null;
	    try {
	      fos = new FileOutputStream(saveName);
	      out = new ObjectOutputStream(fos);
	      out.writeObject(this);
	      out.close();
	      return true;
	    } 
	    catch (Exception ex) {
	      ex.printStackTrace();
	      return false;
	    }
	}
	
	private void importNations(){
		
		try {
			String line;
			String[] values;
			int id = 0;

			BufferedReader br = new BufferedReader(new FileReader(
					"NationsList.txt"));

			while ((line = br.readLine()) != null) {
				values = line.split(",");
				Nation newNation = new Nation(id);
				newNation.setName(values[0]);
				newNation.setGNP(Integer.parseInt(values[1]));
				addNation(newNation);
				id++;
			}
			
			br.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
}
