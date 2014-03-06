package application;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public class Game implements Serializable{


	private static final long serialVersionUID = -243860625637606695L;
	private String name;
	private String saveName;
	private int currRound;
	private Hashtable<String,Commodity> allCommodities;
	private ArrayList<Player> allPlayers;
	private ArrayList<Nation> availableNations;
	private ArrayList<Round> allRounds;
	
	public Game(String n) {
		name = n;
		currRound = 1;
		availableNations = new ArrayList<Nation>();
		allPlayers = new ArrayList<Player>();
		allRounds = new ArrayList<Round>();
		allCommodities = new Hashtable<String,Commodity>();
		importCommodities();
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
				
				for(int i=2;i<=values.length-3;i++){
					String tempCommod = values[i];
					Commodity c = allCommodities.get(tempCommod.substring(1, 4));
					boolean isExport = tempCommod.substring(0, 1).equalsIgnoreCase("E");
					boolean isImport = tempCommod.substring(0, 1).equalsIgnoreCase("I");
					if(c!=null && (isExport || isImport)){
						if(isExport)
							newNation.addExport(c);
						else if(isImport)
							newNation.addImport(c);
					}
					else
						System.out.println(values[0] + " invalid commodity: " + values[i]);
				}

				addNation(newNation);
				id++;
			}
			
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
	private void importCommodities(){
		try{
			String line;
			String[] values;
			
			BufferedReader br = new BufferedReader(new FileReader("CommoditiesList.txt"));
			while((line = br.readLine()) != null){
				values = line.split(",");
				if(!allCommodities.containsKey(values[0])){
					Commodity newCommod = null;
					if(values[2].equals("F"))
						newCommod = new Commodity(values[1], CommodityType.Food);
					else if(values[2].equals("O"))
						newCommod = new Commodity(values[1], CommodityType.Oil);
					else if(values[2].equals("X"))
						newCommod = new Commodity(values[1], CommodityType.Other);
				
					allCommodities.put(values[0], newCommod);
				}
			}
			br.close();
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
}
