/**
 * 
 */
package application;

import java.text.NumberFormat;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.BigDecimalStringConverter;

/**
 * @author jhughes
 *
 */


public class CurrencyFieldTableCell<S, T> extends TextFieldTableCell<S, T> {
	
	public CurrencyFieldTableCell(StringConverter <T> converter){
		super(converter);
	}
	
	@SuppressWarnings("unchecked")
	public CurrencyFieldTableCell(){
		super((StringConverter<T>) new BigDecimalStringConverter());

	}
	
	@Override
	public void updateItem(T item, boolean empty){
		try {
			super.updateItem(item, empty);
			setText(item == null? "":NumberFormat.getCurrencyInstance().format(item));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void cancelEdit(){
		super.cancelEdit();
		setText(this.getItem() == null? "":NumberFormat.getCurrencyInstance().format(this.getItem()));	
	}
}
