package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	public static Game currGame;
	public static Player currPlayer;
	public static Nation currNation;
	public static Stage passwordStage;
	public static Stage pStage;
	public static Stage advanceStage;
	public static boolean passwordOk;
	public static boolean advanceOk;
	public static Nation passwordNation;
	public static PasswordController passwordController;
	public static AdvanceController advanceController;
	public static InternationalOrganization passwordOrg;

	public static ArrayList<Game> allGames;

	@Override
	public void start(Stage primaryStage) {
		try {

			// primaryStage.initStyle(StageStyle.TRANSPARENT);

			System.out.println("Working Directory = "
					+ System.getProperty("user.dir"));

			FXMLLoader fxmlLoader = new FXMLLoader();
			URL location = Main.class.getResource("WorldSimFXML.fxml");
			fxmlLoader.setLocation(location);

			AnchorPane page = (AnchorPane) fxmlLoader.load(Main.class
					.getResource("WorldSimFXML.fxml").openStream());
			UIController UICont = fxmlLoader.getController();

			String lastGame = getProperty("Last_Game_Name");
			if (!changeGame(lastGame, UICont)) {
				System.out.println("Could not find game: " + lastGame);
				UICont.gameButtonClick(null);
			} else
				System.out.println("Loaded Game: " + lastGame);

			Scene scene = new Scene(page);
			scene.getStylesheets().add(
					Main.class.getResource("application.css").toString());
			// Color c =
			// Color.rgb((int)Color.BLACK.getRed(),(int)Color.BLACK.getGreen(),
			// (int)Color.BLACK.getBlue(), .90);

			// scene.setFill(c);
			pStage = primaryStage;
			primaryStage.setTitle("World Simulation");
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.show();
			
			FXMLLoader fxmlLoader2 = new FXMLLoader();
			URL location2 = Main.class.getResource("Password.fxml");
			fxmlLoader2.setLocation(location2);
			AnchorPane page2 = (AnchorPane) fxmlLoader2.load(Main.class.getResource("Password.fxml").openStream());
			passwordController = fxmlLoader2.getController();
			Scene Scene2 = new Scene(page2);

			passwordStage = new Stage();
			passwordStage.initStyle(StageStyle.TRANSPARENT);
			passwordStage.initOwner(primaryStage);			
			passwordStage.initModality(Modality.APPLICATION_MODAL);			
			passwordStage.setScene(Scene2);
			passwordStage.setResizable(false);
			
			FXMLLoader fxmlLoader3 = new FXMLLoader();
			URL location3 = Main.class.getResource("Advance.fxml");
			fxmlLoader3.setLocation(location3);
			AnchorPane page3 = (AnchorPane) fxmlLoader3.load(Main.class.getResource("Advance.fxml").openStream());
			advanceController = fxmlLoader3.getController();
			Scene Scene3 = new Scene(page3);
			
			advanceStage = new Stage();
			advanceStage.initStyle(StageStyle.TRANSPARENT);
			advanceStage.initOwner(primaryStage);			
			advanceStage.initModality(Modality.APPLICATION_MODAL);			
			advanceStage.setScene(Scene3);
			advanceStage.setResizable(false);
			

			// ArrayList<Nation> nations = currGame.getNations();

			// ArrayList<String> UINations = UICont.nationsArray();
			// UICont.addNationToUI("#3 nation");
			// for (Nation nation : nations) {
			// UICont.addNationToUI(nation.name());
			// }
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String[] args) {

		System.out.println("Working Directory = "
				+ System.getProperty("user.dir"));

		allGames = new ArrayList<Game>();

		loadGames();

		// createNewGame();
		// initialize();
		launch(args);

	}

	public static void loadGames() {
		File[] gameFiles = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		Game g;

		gameFiles = findFilesStartAndEndWith("Game", ".wsm");

		for (File f : gameFiles) {
			try {
				fis = new FileInputStream(f);
				ois = new ObjectInputStream(fis);
				g = (Game) ois.readObject();
				allGames.add(g);
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static File[] findFilesStartAndEndWith(final String beg,
			final String end) {
		File workingDir = new File(System.getProperty("user.dir"));
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return (filename.startsWith(beg) && filename.endsWith(end));
			}
		};
		return workingDir.listFiles(filter);
	}

	public static void initialize() {

		String alaf2 = System.getProperty("apple.laf.useScreenMenuBar");
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		String alaf = System.getProperty("apple.laf.useScreenMenuBar");

	}

	public static void changeGame(Game g, UIController UI) {

		currGame = g;
		setProperty("Last_Game_Name", currGame.getName());

		for (Nation n : g.getNations()) {

			if (n.inUse())
				UI.addInUseNation(n);
			else
				UI.addAvailableNation(n);
		}

		UI.setAllPlayers(g.getPlayers());
		UI.setAllNations(g.getNations());
		UI.setAllTeachers(g.getTeachers());
		UI.setAllInternationalOrgs(g.getInternationalOrganizations());
		UI.updateLeaderboard();
		
		ArrayList<ContingencyTransaction> imfTrans = new ArrayList<ContingencyTransaction>();
		for(ContingencyTransaction t:currGame.getContingencyTransactions()){
			if(t.getType() == ContingencyType.IMF && t.getGiver()==null)
				imfTrans.add(t);
		}
		UI.setAllIMFTransactions(imfTrans);
		

	}

	public static boolean changeGame(String gameName, UIController UI) {
		Game foundGame = UI.findGame(gameName);
		if (foundGame != null) {
			UI.setGame(foundGame);
			return true;
		} else
			return false;
	}

	public static void setProperty(String key, String value) {

		Properties prop = new Properties();
		OutputStream output = null;

		try {

			output = new FileOutputStream("config.properties");
			prop.setProperty(key, value);
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static String getProperty(String key) {

		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("config.properties");
			prop.load(input);
			return prop.getProperty(key);
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
