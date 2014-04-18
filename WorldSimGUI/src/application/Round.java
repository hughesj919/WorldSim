package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public class Round implements Serializable{
	
	private static final long serialVersionUID = -7718379888986882377L;
	private Hashtable<String,Nation> roundNations;
	private ArrayList<TradeData> roundTrades;
	private ArrayList<ContingencyTransaction> roundContingencyTransactions;
	
	public Round(Hashtable<String,Nation> nations, ArrayList<TradeData> trades, ArrayList<ContingencyTransaction> trans){
		roundNations = new Hashtable<String,Nation>();
		roundTrades = new ArrayList<TradeData>();
		roundContingencyTransactions = new ArrayList<ContingencyTransaction>();
		
		for(Nation n:nations.values()){
			Nation newNation = Nation.copyNation(n);
			roundNations.put(n.getCountryCode(), newNation);
		}
		
		for(Nation n:getNations()){
			for(Relationship r:n.getRelationships()){
				r.setFirstNation(roundNations.get(r.getFirstNation().getCountryCode()));
				r.setSecondNation(roundNations.get(r.getSecondNation().getCountryCode()));
			}
		}
		
		for(TradeData t:trades){
			t.setImporter(roundNations.get(t.getImporter().getCountryCode()));
			t.setExporter(roundNations.get(t.getExporter().getCountryCode()));
			roundTrades.add(t);
		}		
		for(ContingencyTransaction t:trans){
			t.setGiver(roundNations.get(t.getGiver().getCountryCode()));
			t.setReceiver(roundNations.get(t.getReceiver().getCountryCode()));
			roundContingencyTransactions.add(t);
		}		
	}
	
	public ArrayList<Nation> getNations(){
		return new ArrayList<Nation>(roundNations.values());
	}
	
	public Nation getNation(String key){
		return roundNations.get(key);
	}	
	
	public ArrayList<TradeData> getTrades(){
		return roundTrades;
	}
}
