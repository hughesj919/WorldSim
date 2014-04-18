package application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;


public class PieChartNoCenter extends PieChart {

	public DoubleProperty centerX;
	public DoubleProperty centerY;
	public DoubleProperty centerRadius;
	private Circle budgetCircle;
	
	public PieChartNoCenter() {
		// TODO Auto-generated constructor stub
		super();
		centerX = new SimpleDoubleProperty();
		centerY = new SimpleDoubleProperty();
		centerRadius = new SimpleDoubleProperty();
		budgetCircle = new Circle(0,0,0,Paint.valueOf("#f4f4f4"));
		budgetCircle.centerXProperty().bind(centerX);
		budgetCircle.centerYProperty().bind(centerY);
		budgetCircle.radiusProperty().bind(centerRadius);
		this.getChildren().add(budgetCircle);
	}

	public PieChartNoCenter(ObservableList<Data> data) {
		super(data);
		// TODO Auto-generated constructor stub
	}
	
	 @Override protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight){		 
		 super.layoutChartChildren(top, left, contentWidth, contentHeight);
		 //centerX.set(contentWidth/2 + left+5);
		// centerY.set(contentHeight/2 + top+29);	
		// double pieRadius = Math.max(contentWidth-left,contentHeight-top) / 2;
		// centerRadius.set(25);
		// boolean sizesSet = false;
		/* for(int i=0;i<getChartChildren().size() && !sizesSet;i++){
			 Node n = getChartChildren().get(i);
			 if(n !=null && n instanceof Region){				 
				 centerX.set(n.getLayoutX());
				 centerY.set(n.getLayoutY());
				 Region newRegion = (Region) n;
				 Arc newArc = (Arc)newRegion.getShape();
				 if(newArc!=null && newArc.getLength()!=0){
					// newArc.getLength()
					// centerRadius.set(Math.abs(newArc.get)*.70);
					 sizesSet=true;
				// centerRadius.set((n.getBoundsInParent().getMaxX() - n.getBoundsInParent().getMinX())*.70);
				 }
			 }
		 }		
		// /'/this.get*/
	 }
}
