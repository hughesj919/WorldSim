package application;

import java.io.Serializable;

enum playerType {
	chiefOfState, foreignMinister;
};


public class Player implements Serializable{

	private static final long serialVersionUID = 8601841937779455868L;
	private int period;
	private String name;
	private Nation nation;
	private playerType type;

	Player(String n) {
		name = n;
	}
	
	@Override
	public String toString(){
		return name;
	}

	@Override
	public boolean equals(Object o){
		
		if(o==null)
			return false;
		else if(o instanceof Player)
			return ((Player) o).getName().equalsIgnoreCase(name);
		else
			return false;
		
	}
	
	public void setName(String n) {
		name = n;
	}

	public void setPeriod(int p) {
		period = p;
	}

	public void setNation(Nation n) {
		nation = n;
	}

	public void setType(playerType t) {
		type = t;
	}
	
	public String getName(){
		return name;
	}
	
	public int getPeriod(){
		return period;
	}
	
	public String getPeriodString(){
		return Integer.toString(period);
	}
	
	public Nation getNation(){
		return nation;
	}
	
	public playerType getType(){
		return type;
	}
	
	public String getTypeString(){
		if(type == playerType.chiefOfState)
			return "Chief of State";
		else if(type == playerType.foreignMinister)
			return "Foreign Minister";
		else 
			return "";
	}

}
