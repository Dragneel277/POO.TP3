import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
public class App extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FMXL/login.fxml"));
        BorderPane root = loader.load(); 

        Scene scene= new Scene(root);

        loader.getController(); 

        scene.getStylesheets().add("style.css");

        primaryStage.setTitle("Dental Clinic");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        }


}

//"vmArgs": "--module-path /home/ruben/Documents/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml",