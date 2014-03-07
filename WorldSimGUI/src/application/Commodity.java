package application;

import java.io.Serializable;

enum CommodityType{
	Food,Oil,Other;
}

public class Commodity implements Serializable{
	

	private static final long serialVersionUID = 4271645914023542084L;
	private CommodityType type;
	private String name;
	
	public Commodity( String n, CommodityType t){
		type = t;
		name = n;
	}
	
	public Commodity(Commodity c){
		type = c.getType();
		name = c.getName();
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	public CommodityType getType(){
		return type;
	}
	
	public String getTypeString(){
		if(type == CommodityType.Food)
			return "Food";
		else if(type == CommodityType.Oil)
			return "Oil";
		else if(type == CommodityType.Other)
			return "Other";
		else
			return "";
	}
	
	public void setType(CommodityType t){
		type = t;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String n){
		name = n;
	}

}
