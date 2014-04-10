package application;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class Game implements Serializable{


	private static final long serialVersionUID = -243860625637606695L;
	private String name;
	private String saveName;
	private Hashtable<String,Commodity> allCommodities;
	private ArrayList<Player> allPlayers;
	private Hashtable<String, Nation> allNations; //nations by country code
	private Hashtable<String, Nation> allPasswords; //nations by password
	private Hashtable<String,InternationalOrganization> allIntOrgs;
	private ArrayList<Teacher> allTeachers;
	private ArrayList<TradeData> currentTrades;
	private ArrayList<ContingencyTransaction> currentContingencyTransactions;
	private ArrayList<Round> allRounds;
	
	public Game(String n) {
		name = n;
		currentTrades = new ArrayList<TradeData>();
		currentContingencyTransactions = new ArrayList<ContingencyTransaction>();
		allPlayers = new ArrayList<Player>();
		allRounds = new ArrayList<Round>();
		allCommodities = new Hashtable<String,Commodity>();
		allNations = new Hashtable<String,Nation>();
		allPasswords = new Hashtable<String,Nation>();
		allIntOrgs = new Hashtable<String,InternationalOrganization>();
		allTeachers = new ArrayList<Teacher>();
		importCommodities();
		importNations();
		initializeRounds();
	}
	
	@Override public String toString(){
		return name;
	}

	public void addNation(Nation n, String key) {
		allNations.put(key, n);	
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
		return new ArrayList<Nation>(allNations.values());
	}
	
	public Nation getNation(String key){
		return allNations.get(key);
	}
	
	public ArrayList<Player> getPlayers(){
		return allPlayers;
	}
	
	public Hashtable<String,Commodity> getCommodities(){
		return allCommodities;
	}	

	public ArrayList<TradeData> getTrades() {
		return currentTrades;
	}
	
	public ArrayList<Round> getRounds(){
		return allRounds;
	}
	
	public ArrayList<Teacher> getTeachers(){
		return allTeachers;
	}
	
	public ArrayList<ContingencyTransaction> getContingencyTransactions(){
		return currentContingencyTransactions;
	}
	
	public ArrayList<InternationalOrganization> getInternationalOrganizations(){
		return new ArrayList<InternationalOrganization>(allIntOrgs.values());
	}

	public void addPlayer(Player p) {
		allPlayers.add(p);
	}
	
	public void removePlayer(Player p){
		allPlayers.remove(p);
	}
	
	public void addTrade(TradeData t) {
		currentTrades.add(t);
	}
	
	public void removeTrade(TradeData t) {
		currentTrades.remove(t);
	}
	
	public void addInternationalOrganization(InternationalOrganization i){		
		Random randomGenerator = new Random();
		while(!allIntOrgs.containsValue(i)){
			int randomHex1 = randomGenerator.nextInt(16);
			int randomHex2 = randomGenerator.nextInt(16);
			int randomHex3 = randomGenerator.nextInt(16);
			int randomHex4 = randomGenerator.nextInt(16);
			String pwd = String.format("%x%x%x%x",randomHex1,randomHex2,randomHex3,randomHex4);
			allIntOrgs.put(pwd,i);
			i.setPassword(pwd);
		}
	}
	
	public void removeInternationalOrganization(InternationalOrganization i){
		allIntOrgs.remove(i);
	}
	
	public int currentRound(){
		return allRounds.size();
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

			BufferedReader br = new BufferedReader(new FileReader(
					"NationsList.txt"));
			Random randomGenerator = new Random();

			while ((line = br.readLine()) != null) {
				values = line.split(",");
				Nation newNation = new Nation();
				newNation.setCountryCode(values[0]);
				newNation.setName(values[1]);
				newNation.setGNP(values[2]);
				
				for(int i=3;i<values.length;i++){
					String tempCommod = values[i];
					if(tempCommod.length()==4){
						Commodity c = allCommodities.get(tempCommod.substring(1, 4).toUpperCase());
						boolean isExport = tempCommod.substring(0, 1).equalsIgnoreCase("E");
						boolean isImport = tempCommod.substring(0, 1).equalsIgnoreCase("I");
						if(c!=null && (isExport || isImport)){
							if(isExport)
								newNation.addAvailableExport(c);
							else if(isImport)
								newNation.addRequiredImport(c);
						}
						else
							System.out.println(values[1] + " invalid commodity: " + values[i]);
					}
					else
						System.out.println(values[1] + " invalid commodity: " + values[i]);
				}
				addNation(newNation,values[0]);
				
				while(!allPasswords.containsValue(newNation)){
					int randomHex1 = randomGenerator.nextInt(16);
					int randomHex2 = randomGenerator.nextInt(16);
					int randomHex3 = randomGenerator.nextInt(16);
					String pwd = String.format("%x%x%x",randomHex1,randomHex2,randomHex3);
					allPasswords.put(pwd,newNation);
					newNation.setPassword(pwd);
				}
				
				
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
					if(values[2].equalsIgnoreCase("F"))
						newCommod = new Commodity(values[1], CommodityType.Food);
					else if(values[2].equalsIgnoreCase("O"))
						newCommod = new Commodity(values[1], CommodityType.Oil);
					else if(values[2].equalsIgnoreCase("X"))
						newCommod = new Commodity(values[1], CommodityType.Other);
					else
						System.out.println("Error importing commodity: " + line);
				
					allCommodities.put(values[0], newCommod);				
				}
			}
			br.close();
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void initializeRounds(){
		Round firstRound = new Round(allNations, currentTrades);
		allRounds.add(firstRound);	
	}
}
