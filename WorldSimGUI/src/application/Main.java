package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	public static Game currGame;

	@Override
	public void start(Stage primaryStage) {
		try {

			System.out.println("Working Directory = "
					+ System.getProperty("user.dir"));

			FXMLLoader fxmlLoader = new FXMLLoader();
			URL location = Main.class.getResource("WorldSimFXML.fxml");
			fxmlLoader.setLocation(location);

			AnchorPane page = (AnchorPane) fxmlLoader.load(Main.class
					.getResource("WorldSimFXML.fxml").openStream());
			UIController UICont = fxmlLoader.getController();
			Scene scene = new Scene(page);
			primaryStage.setTitle("World Simulation");
			primaryStage.setScene(scene);
			primaryStage.show();

			populateNationsList(UICont);

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
		createNewGame();
		// initialize();
		launch(args);

	}

	public static void populateNationsList(UIController UICont) {
		UICont.setNations(currGame.getNations());
	}

	public static void initialize() {

		/*
		 * try { // Set cross-platform Java L&F (also called "Metal")
		 * UIManager.setLookAndFeel(UIManager
		 * .getCrossPlatformLookAndFeelClassName()); } catch
		 * (UnsupportedLookAndFeelException e) { // handle exception } catch
		 * (ClassNotFoundException e) { // handle exception } catch
		 * (InstantiationException e) { // handle exception } catch
		 * (IllegalAccessException e) { // handle exception }
		 */
		String alaf2 = System.getProperty("apple.laf.useScreenMenuBar");
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		String alaf = System.getProperty("apple.laf.useScreenMenuBar");

		/*
		 * Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 * int width = screenSize.width; int height = screenSize.height;
		 * 
		 * JFrame frame = new JFrame("World Simulation"); // final DesktopMain
		 * dm = new DesktopMain();
		 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.pack();
		 * frame.setSize(width, height);
		 * 
		 * JTabbedPane tabbedPane = new JTabbedPane(); JPanel newPanel = new
		 * JPanel(); tabbedPane.addTab("Tab 1", newPanel); JPanel newPanel2 =
		 * new JPanel(); tabbedPane.addTab("Tab 2", newPanel2);
		 * 
		 * frame.add(tabbedPane); frame.setVisible(true);
		 */
	}

	public static void createNewGame() {
		currGame = new Game();
		importNations(currGame);

	}

	public static void importNations(Game g) {

		try {
			String line;
			String[] values;
			int id = 0;

			BufferedReader br = new BufferedReader(new FileReader(
					"NationsList.txt"));

			while ((line = br.readLine()) != null) {
				values = line.split(",");
				Nation newNation = new Nation(id);
				newNation.setName(values[0]);
				newNation.setGNP(Integer.parseInt(values[1]));
				g.addNation(newNation);
				id++;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void teamButtonClick() {

		System.out.println("team clicke");

	}

	/*
	 * public static void nationButtonClick() {
	 * 
	 * System.out.println("nation clicke");
	 * 
	 * }
	 * 
	 * public static void gameButtonClick() {
	 * 
	 * System.out.println("game clicke");
	 * 
	 * }
	 * 
	 * @FXML private TitledPane gamePane;
	 * 
	 * public void teamButtonClick() {
	 * 
	 * gamePane.setVisible(false);
	 * 
	 * System.out.println("team clicke");
	 * 
	 * }
	 * 
	 * public static void budgetButtonClick() {
	 * 
	 * System.out.println("budget clicke");
	 * 
	 * }
	 * 
	 * public static void leaderboardButtonClick() {
	 * 
	 * System.out.println("leaderboard clicke");
	 * 
	 * }
	 * 
	 * public static void advanceButtonClick() {
	 * 
	 * System.out.println("advance clicke");
	 * 
	 * }
	 */

}
