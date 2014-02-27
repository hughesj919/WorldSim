package application;

import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class UIController {
	
	/***********************************************
	 * Game Tab
	 **********************************************/
	private ObservableList<Game> obsGames;	
	
	@FXML
	private Button gameButton;
	
	@FXML
	private TitledPane gamePane;
	
	@FXML
    private ComboBox<Game> gamesList;
    
    @FXML
    private Button saveGameButton;

    @FXML
    private TextField newGameText;
    

	@FXML
	public void gameButtonClick(ActionEvent event) {
		gamePane.setVisible(true);
		teamPane.setVisible(false);
		budgetPane.setVisible(false);
		leaderboardPane.setVisible(false);
		nationPane.setVisible(false);

	}
    
    @FXML
    public void saveGameButtonClick(ActionEvent event) {
    	
    	String name = newGameText.getText();
    	if(name.matches("\\s*\\w+(\\w|\\s)*"))
    	{
        	Game newGame = new Game(name);
        	String saveName = "Game" + name.replaceAll("\\s+", "") + ".wsm";
        	if(newGame.saveGame(saveName))
        	{
        		obsGames.add(newGame);
        		Main.changeGame(newGame,this);
        		System.out.println("Game Saved");
        		
        	}
        	else{
        		System.out.println("Error saving game");
        	}
    	}
    	else{
    		System.out.println("Letters and numbers only please!");
    	}
    	newGameText.setText(null);
    	
    }
    

    @FXML
    public void gamesListChange(ActionEvent event) {
    	Game g = gamesList.getValue();
    	Main.changeGame(g, this);
    	
    }
    
    public void setGame(Game g){
    	gamesList.setValue(g);
    }
    
    public Game findGame(String gameName){
    	
    	for(Game g:obsGames){
    		if(g.getName().equals(gameName)){
    			return g;
    		}
    	}
    	return null;
    }
    
    

	/***********************************************
	 * Nation Tab
	 ***********************************************/	
	private ObservableList<Nation> obsInUseNations;
	private ObservableList<Nation> obsAvailableNations;
	
	@FXML
	private Button nationButton;
	
	@FXML
	private TitledPane nationPane;

	@FXML
	private ListView<Nation> nationsAvailableList;

	@FXML
	private ListView<Nation> nationsInUseList;
	
	@FXML
	public void nationButtonClick(ActionEvent event) {
		gamePane.setVisible(false);
		teamPane.setVisible(false);
		budgetPane.setVisible(false);
		leaderboardPane.setVisible(false);
		nationPane.setVisible(true);

	}
	
	public void addInUseNation(Nation nat) {
		obsInUseNations.add(nat);
	}
	
	public void addAvailableNation(Nation nat) {
		obsAvailableNations.add(nat);
	}

	public void setInUseNations(ArrayList<Nation> nats) {
		for (Nation n : nats) {
			obsInUseNations.add(n);
			// obsNations.
		}
	}
	
	
	/***********************************************
	 * Team/Player Tab
	 ***********************************************/
	private ObservableList<Player> obsPlayers;
	private ObservableList<Nation> obsAllNations;

	@FXML
	private Button teamButton;
	
	@FXML
	private TitledPane teamPane;
	
	@FXML
	private Button savePlayerButton;

	@FXML
	private Button deletePlayerButton;

	@FXML
	private TextField playerPeriod;

	@FXML
	private TextField playerName;

	@FXML
	private ComboBox<String> playerPosition;

	@FXML
	private ComboBox<Nation> playerNation;

	@FXML
	private ListView<Player> playersList;
	

	public void teamButtonClick(ActionEvent event) {
		gamePane.setVisible(false);
		teamPane.setVisible(true);
		budgetPane.setVisible(false);
		leaderboardPane.setVisible(false);
		nationPane.setVisible(false);
	}
	
	@FXML
	public void playerSaveButtonClick(ActionEvent event) {
		String pName = playerName.getText();
		//String pType = playerPosition.getValue();
		Integer pPeriod = Integer.parseInt(playerPeriod.getText());
		Nation pNation = playerNation.getValue();
		
		Player newPlayer = new Player(pName);
		newPlayer.setPeriod(pPeriod);
		newPlayer.setNation(pNation);
		//newPlayer.setType(pType);
		
		pNation.addPlayer(newPlayer);
		obsInUseNations.remove(pNation);
		obsAvailableNations.remove(pNation);
		obsInUseNations.add(pNation);
		
		obsPlayers.add(newPlayer);
		Main.currGame.addPlayer(newPlayer);
		
		/* Rectangle rect = new Rectangle (100, 40, 100, 100);
	     rect.setArcHeight(50);
	     rect.setArcWidth(50);
	     rect.setFill(Color.VIOLET);
	 
	     FadeTransition ft = new FadeTransition(Duration.millis(3000), rect);
	     ft.setFromValue(1.0);
	     ft.setToValue(0.3);
	     ft.setCycleCount(4);
	     ft.setAutoReverse(true);
	 
	     ft.play();*/


	}
	
	public void updateNationsLists(){
	


	}
	
	public void setAllPlayers(ArrayList<Player> players){
		obsPlayers.setAll(players);
	}
	
	public void setAllNations(ArrayList<Nation> nations){
		obsAllNations.setAll(nations);
	}

	@FXML
	public void playerDeleteButtonClick(ActionEvent event) {

	}

	/***********************************************
	 * Budget Tab
	 ***********************************************/

	@FXML
	private TitledPane budgetPane;
	
	@FXML
	private Button budgetButton;
	
	@FXML
	public void budgetButtonClick(ActionEvent event) {
		gamePane.setVisible(false);
		teamPane.setVisible(false);
		budgetPane.setVisible(true);
		leaderboardPane.setVisible(false);
		nationPane.setVisible(false);

	}

	/***********************************************
	 * Leaderboard Tab
	 ***********************************************/

	@FXML
	private TitledPane leaderboardPane;
	
	@FXML
	private Button leaderboardButton;
	
	
	@FXML
	public void leaderboardButtonClick(ActionEvent event) {
		gamePane.setVisible(false);
		teamPane.setVisible(false);
		budgetPane.setVisible(false);
		leaderboardPane.setVisible(true);
		nationPane.setVisible(false);

	}

	/***********************************************
	 * Initialization/Round Advancement
	 ***********************************************/
	@FXML
	private Button advanceButton;

	@FXML
	public void advanceButtonClick(ActionEvent event) {

	}
	
	public void initialize() {
		System.out.println("Initializing UI...");
		

		obsInUseNations = FXCollections.observableArrayList();
		obsAvailableNations = FXCollections.observableArrayList();
		obsPlayers = FXCollections.observableArrayList();
		obsAllNations = FXCollections.observableArrayList();
		obsGames = FXCollections.observableArrayList();
		
		obsGames.setAll(Main.allGames);
		
		gamesList.setItems(obsGames);

		// playerNation.setItems(obsNations);

		playerPosition.setItems(FXCollections.observableArrayList(
				"Chief of State", "Budget Officer"));
		// playerPosition.setItems();

		nationsInUseList.setItems(obsInUseNations);
		nationsAvailableList.setItems(obsAvailableNations);
		playersList.setItems(obsPlayers);
		playerNation.setItems(obsAllNations);
		
		gameButtonClick(null);

	}

}
