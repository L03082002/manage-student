package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/controller/Main.fxml"));
		BorderPane root = mainLoader.load();
		Scene scene = new Scene(root);

		primaryStage.setTitle("Quản lý sinh viên");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("student.png")));
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
