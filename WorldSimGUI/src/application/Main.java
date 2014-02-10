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
	@Override
	public void start(Stage primaryStage) {
		try {

			System.out.println("Working Directory = "
					+ System.getProperty("user.dir"));

			URL test = Main.class.getResource("WorldSimFXML.fxml");

			// System.out.println(test);
			AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class
					.getResource("WorldSimFXML.fxml"));

			Scene scene = new Scene(page);

			// scene.getStylesheets().add(
			// getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String[] args) {

		launch(args);
		System.out.println("Working Directory = "
				+ System.getProperty("user.dir"));
		initialize();

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
		Game newGame = new Game();
		importNations(newGame);

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

}
