package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public class Round implements Serializable{
	
	private static final long serialVersionUID = -7718379888986882377L;
	private Hashtable<String,Nation> roundNations;
	private ArrayList<TradeData> roundTrades;
	
	public Round(Hashtable<String,Nation> nations, ArrayList<TradeData> trades){
		roundNations = new Hashtable<String,Nation>();
		roundTrades = new ArrayList<TradeData>();
		
		for(Nation n:nations.values()){
			Nation newNation = Nation.copyNation(n);
			roundNations.put(n.getCountryCode(), newNation);
		}		
		for(TradeData t:trades){
			TradeData newTrade = TradeData.copyTradeData(t);
			roundTrades.add(newTrade);
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
