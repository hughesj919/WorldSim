package application;

import java.io.Serializable;
import java.math.BigDecimal;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Trade implements Serializable{

	private static final long serialVersionUID = 1622278305076421552L;
	private SimpleObjectProperty<Commodity> c;
	private SimpleObjectProperty<Nation> importer;
	private SimpleObjectProperty<Nation> exporter;
	private SimpleObjectProperty<BigDecimal> amount;
	private SimpleStringProperty type;

	public Trade(Commodity com, Nation e, Nation i, String amt) {
		
		exporter = new SimpleObjectProperty<Nation>(e);
		importer = new SimpleObjectProperty<Nation>(i);
		BigDecimal newAmount = new BigDecimal(amt);
		amount = new SimpleObjectProperty<BigDecimal>(newAmount);
		type = new SimpleStringProperty(com.getTypeString());
		
		//Override the set method to set type every time the commodity is set
		c = new SimpleObjectProperty<Commodity>(com){
			@Override public void set(Commodity newCommod){
				super.set(newCommod);
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

	/*public String getAmountString() {
		return NumberFormat.getCurrencyInstance().format(amount);
	}*/
	
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
