package application;

import java.io.Serializable;

enum playerType {
	chiefOfState, budgetOffice;
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

	public void setName(String n) {
		name = n;
	}

	public void setPeriod(int p) {
		period = p;
	}

	public void setNation(Nation n) {
		nation = n;
	}

	public void setType(playerType p) {
		type = p;
	}

}
