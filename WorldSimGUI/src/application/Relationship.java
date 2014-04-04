package application;

import java.io.Serializable;

public abstract class Relationship implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3098196896619059207L;
	private Nation firstNation;
	private Nation secondNation;
	
	public Relationship(Nation a, Nation b) {
		firstNation = a;
		secondNation = b;
	}
		
	public Nation getFirstNation(){
		return firstNation;
	}
	public Nation getSecondNation(){
		return secondNation;
	}
	public void setFirstNation(Nation a){
		firstNation = a;
	}
	public void setSecondNation(Nation b){
		secondNation = b;
	}
	public Nation getOtherNation(Nation n){
		if(firstNation == n)
			return secondNation;
		else if(secondNation == n)
			return firstNation;
		else
			return null;
	}

}
