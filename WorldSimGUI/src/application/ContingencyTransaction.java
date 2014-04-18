package application;

import java.io.Serializable;
import java.math.BigDecimal;

enum ContingencyType{
	Loan,Aid,IMF;
}


public class ContingencyTransaction implements Serializable {

	private static final long serialVersionUID = 4237976927233205728L;
	private Nation giver;
	private Nation receiver;
	private BigDecimal amount; 
	private ContingencyType type;
	

	public ContingencyTransaction(ContingencyType t, Nation g, Nation r, BigDecimal amt) {
		// TODO Auto-generated constructor stub
		type = t;
		giver = g;
		receiver = r;
		amount = amt;		
	}
	
	
	/*public String typeString(){
		if(type == ContingencyType.Loan){
			return "Loan";
		}else if(type == ContingencyType.Aid){
			return "Aid";
		}else if(type == ContingencyType.IMF){
			return "IMF";
		}
	}*/
	
	public ContingencyType getType(){
		return type;
	}
	
	public Nation getGiver(){
		return giver;
	}
	public Nation getReceiver(){
		return receiver;
	}
	public BigDecimal getAmount(){
		return amount;
	}	
	public void setType(ContingencyType t){
		type = t;
	}
	public void setGiver(Nation g){
		giver = g;
	}
	public void setReceiver(Nation r){
		receiver = r;
	}
	public void setAmount(BigDecimal amt){
		amount = amt;
	}
	
	

}
