package application;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;

public class PasswordController {

	
    @FXML
    private Label passwordLabel;
    
    @FXML
    private PasswordField passwordTextField;
    
    @FXML
    public AnchorPane passwordAnchorPane;
    

    @FXML
    void passwordTextFieldAction(ActionEvent event) {
    	
    	String pwd = passwordTextField.getText();
    	if(Main.passwordOrg!=null){
    		if(pwd.equals(Main.passwordOrg.getPassword())){
    			Main.passwordOk = true;
    		}else{
    			Main.passwordOk = false;
    		}
    		Main.passwordOrg = null;
    	}
    	else{
    		if(pwd.equals(Main.passwordNation.getPassword())){
    			Main.passwordOk = true;    		
    		}else{
    			Main.passwordOk = false;
    		}
    	}
		Main.passwordStage.hide();
    }	
}
//Timer t = new Timer();
//t.scheduleAtFixedRate(new TimerTask(){
//	int i =0;
//	@Override			
//	public void run() {
		// TODO Auto-generated method stub
		//if(i<100){
/*		@SuppressWarnings("unused")
		double t2 = Main.passwordStage.getX();
		Main.passwordStage.setX(Main.passwordStage.getX()+100);
		@SuppressWarnings("unused")
		double t = Main.passwordStage.getX();
		Main.passwordStage.setX(Main.passwordStage.getX()+100);
		Main.passwordStage.setX(Main.passwordStage.getX()-100);
		Main.passwordStage.setX(Main.passwordStage.getX()-100);
		Main.passwordStage.setX(Main.passwordStage.getX()-100);
		Main.passwordStage.setX(Main.passwordStage.getX()-100);
		Main.passwordStage.setX(Main.passwordStage.getX()+100);
		Main.passwordStage.setX(Main.passwordStage.getX()+100);*/
	//		i++;
	//	}
	//	else{
	//		Main.passwordStage.hide();
	//	}
	//}
	
//},0,25);

//PathTransition path = new PathTransition()