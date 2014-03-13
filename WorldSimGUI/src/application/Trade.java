package application;

import java.math.BigDecimal;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Trade{

	private SimpleObjectProperty<Commodity> c;
	private SimpleObjectProperty<Nation> importer;
	private SimpleObjectProperty<Nation> exporter;
	private SimpleObjectProperty<BigDecimal> amount;
	private SimpleStringProperty type;
	private TradeData tradeData;

	public Trade(TradeData data) {
		
		tradeData = data;
		
		exporter = new SimpleObjectProperty<Nation>(tradeData.getExporter()){
			//Override the set method to set type every exporter when the table is changed
			@Override public void set(Nation n){
				super.set(n);
				tradeData.setExporter(n);
			}
		};
		
		importer = new SimpleObjectProperty<Nation>(tradeData.getImporter()){
			//Override the set method to set type every importer when the table is changed
			@Override public void set(Nation n){
				super.set(n);
				tradeData.setImporter(n);
			}
		};		
		
		BigDecimal newAmount = new BigDecimal(tradeData.getAmount().toPlainString());
		amount = new SimpleObjectProperty<BigDecimal>(newAmount){
			//Override the set method to update amount when table is changed
			@Override public void set(BigDecimal amt){
				super.set(amt);
				tradeData.setAmount(amt);
			}
		};
		type = new SimpleStringProperty(tradeData.getCommodity().getTypeString());
		
		//Override the set method to set type every time the commodity is set
		c = new SimpleObjectProperty<Commodity>(tradeData.getCommodity()){
			@Override public void set(Commodity newCommod){
				super.set(newCommod);
				tradeData.setCommodity(newCommod);
				type.set(newCommod.getTypeString());
			}
		};
		
	}

	public void setCommodity(Commodity com) {
		c.set(com);
		setType(c.get().getTypeString());
	}

	public void setExporter(Nation e) {
		exporter.set(e);
	}

	public void setImporter(Nation i) {
		importer.set(i);
	}
	
	private void setType(String t){
		type.set(t);
	}

	public void setAmount(String amt) {
		BigDecimal newAmount = new BigDecimal(amt);
		amount.set(newAmount);
	}
	
	public void setTradeData(TradeData t){
		tradeData = t;
	}

	public Commodity getCommodity() {
		return c.get();
	}

	public Nation getExporter() {
		return exporter.get();
	}

	public Nation getImporter() {
		return importer.get();
	}

	public BigDecimal getAmount() {
		return amount.get();
	}
	
	public TradeData getTradeData(){
		return tradeData;
	}
	
	public SimpleObjectProperty<BigDecimal> amountProperty(){
		return amount;
	}
	
	public SimpleObjectProperty <Nation> exporterProperty(){
		return exporter;
	}
	
	public SimpleObjectProperty <Nation> importerProperty(){
		return importer;
	}
	
	public SimpleObjectProperty <Commodity> commodityProperty(){
		return c;
	}
	
	public SimpleStringProperty typeProperty() {
		return type;
	}
	
}
