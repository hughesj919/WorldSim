package application;

import java.math.BigDecimal;
import java.text.NumberFormat;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Trade {

	private SimpleObjectProperty<Commodity> c;
	private SimpleObjectProperty<Nation> importer;
	private SimpleObjectProperty<Nation> exporter;
	private SimpleObjectProperty<BigDecimal> amount;

	public Trade(Commodity com, Nation e, Nation i, String amt) {
		c = new SimpleObjectProperty<Commodity>(com);
		exporter = new SimpleObjectProperty<Nation>(e);
		importer = new SimpleObjectProperty<Nation>(i);
		BigDecimal newAmount = new BigDecimal(amt);
		amount = new SimpleObjectProperty<BigDecimal>(newAmount);
	}

	public void setCommodity(Commodity com) {
		c.set(com);
	}

	public void setExporter(Nation e) {
		exporter.set(e);
	}

	public void setImporter(Nation i) {
		importer.set(i);
	}

	public void setAmount(String amt) {
		BigDecimal newAmount = new BigDecimal(amt);
		amount.set(newAmount);
	}

	public Commodity getCommodity() {
		return c.get();
	}

	public String getCommodityType() {
		return c.get().getTypeString();
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
	
}
