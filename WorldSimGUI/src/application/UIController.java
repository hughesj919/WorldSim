package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

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
    void saveGameButtonClick(ActionEvent event) {
    	
    	String name = newGameText.getText();
    	if(name.matches("\\s*\\w+(\\w|\\s)*"))
    	{
        	Game newGame = new Game(name);
        	String saveName = "Game" + name.replaceAll("\\s+", "") + ".wsm";
        	if(newGame.saveGame(saveName))
        	{
        		System.out.println("Game Saved");
        		
        	}
        	else{
        		System.out.println("Error saving game");
        	}
    	}
    	else{
    		System.out.println("Letters and numbers only please!");
    	}
    	
    }

	/***********************************************
	 * Nation Tab
	 ***********************************************/	
	private ObservableList<Nation> obsNations;
	
	@FXML
	private Button nationButton;
	
	@FXML
	private TitledPane nationPane;

	@FXML
	private ListView<Nation> nationsList;

	@FXML
	private ListView<Nation> inUseNationsList;
	
	@FXML
	public void nationButtonClick(ActionEvent event) {
		gamePane.setVisible(false);
		teamPane.setVisible(false);
		budgetPane.setVisible(false);
		leaderboardPane.setVisible(false);
		nationPane.setVisible(true);

	}
	
	public void addNationToUI(Nation nat) {
		obsNations.add(nat);
	}

	public void setNations(ArrayList<Nation> nats) {
		for (Nation n : nats) {
			obsNations.add(n);
			// obsNations.
		}
	}
	
	
	/***********************************************
	 * Team/Player Tab
	 ***********************************************/
	private ObservableList<Player> obsPlayers;

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
		Integer pPeriod = Integer.getInteger(playerPeriod.getText());
		Nation pNation = playerNation.getValue();
		
		Player newPlayer = new Player(pName);
		newPlayer.setPeriod(pPeriod);
		newPlayer.setNation(pNation);
		//newPlayer.setType(pType);
		
		obsPlayers.add(newPlayer);
		Main.currGame.addPlayer(newPlayer);


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
		

		obsNations = FXCollections.observableArrayList();
		obsPlayers = FXCollections.observableArrayList();
		obsGames = FXCollections.observableArrayList();
		
		obsGames.setAll(Main.allGames);
		
		gamesList.setItems(obsGames);

		// playerNation.setItems(obsNations);

		playerPosition.setItems(FXCollections.observableArrayList(
				"Chief of State", "Budget Officer"));
		// playerPosition.setItems();

		nationsList.setItems(obsNations);
		playersList.setItems(obsPlayers);

	}

}
