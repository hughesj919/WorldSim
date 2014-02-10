package application;

public class Commodity {
	
	private int type;
	private String name;
	
	public Commodity(int t, String n){
		type = t;
		name = n;
	}
	public int getType(){
		return type;
	}
	public void setType(int t){
		type = t;
	}
	
	public String name(){
		return name;
	}
	
	public void setName(String n){
		name = n;
	}

}
