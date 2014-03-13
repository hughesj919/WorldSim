package application;

import java.io.Serializable;
import java.math.BigDecimal;


public class TradeData implements Serializable{

	private static final long serialVersionUID = 294711390978544800L;
	private Commodity c;
	private Nation importer;
	private Nation exporter;
	private BigDecimal amount;
	
	public TradeData(Commodity com, Nation i, Nation e, BigDecimal amt){
		c = com;
		importer = i;
		exporter = e;
		amount = amt;
	}
	
	public void setCommodity(Commodity com){
		c = com;
	}
	
	public void setImporter(Nation i){
		importer = i;
	}
	
	public void setExporter(Nation e){
		exporter = e;
	}
	
	public void setAmount(BigDecimal amt){
		amount = amt;
	}
	
	public Commodity getCommodity(){
		return c;
	}
	
	public Nation getImporter(){
		return importer;
	}
	
	public Nation getExporter(){
		return exporter;
	}
	
	public BigDecimal getAmount(){
		return amount;
	}

}
