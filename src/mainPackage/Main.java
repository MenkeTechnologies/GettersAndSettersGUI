package mainPackage;

import Python.PythonController;
import Swift.SwiftController;
import CppHeader.CppHeaderController;
import CppImplementation.CppImplementationController;
import CppStandalone.CppStandaloneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader mainLoader = new FXMLLoader(MainController.class.getResource("/mainPackage/sample.fxml"));
        BorderPane root = mainLoader.load();
        MainController mainController = mainLoader.getController();

        TabPane mainTabPanes = (TabPane) root.getCenter();

        FXMLLoader pythonLoader = new FXMLLoader(Python.PythonController.class.getResource("/Python/PythonTab.fxml"));
        Tab pythonTab = pythonLoader.load();
        PythonController pythonController = pythonLoader.getController();

        FXMLLoader swiftLoader = new FXMLLoader(Swift.SwiftController.class.getResource("/Swift/SwiftTab.fxml"));
        Tab swiftTab = swiftLoader.load();
        SwiftController swiftController = swiftLoader.getController();


        FXMLLoader cppHeaderLoader = new FXMLLoader(CppHeader.CppHeaderController.class.getResource("/CppHeader/CppHeaderTab.fxml"));
        Tab cppHeaderTab = cppHeaderLoader.load();
        CppHeaderController cppHeaderController = cppHeaderLoader.getController();

        FXMLLoader cppImplementationLoader = new FXMLLoader(CppImplementation.CppImplementationController.class.getResource("/CppImplementation/CppImplementationTab.fxml"));
        Tab cppImplementationTab = cppImplementationLoader.load();
        CppImplementationController cppImplementationController = cppImplementationLoader.getController();

        FXMLLoader cppStandaloneLoader = new FXMLLoader(CppStandalone.CppStandaloneController.class.getResource("/CppStandalone/CppStandaloneTab.fxml"));
        Tab cppStandaloneTab = cppStandaloneLoader.load();
        CppStandaloneController cppStandaloneController = cppStandaloneLoader.getController();

        mainTabPanes.getTabs().addAll(pythonTab, swiftTab, cppHeaderTab, cppImplementationTab, cppStandaloneTab);
        mainTabPanes.getSelectionModel().select(1);


        primaryStage.setTitle("Getter And Setter Generator");
        Scene scene = new Scene(root, 1000, 1000);

        scene.getStylesheets().add("styles.css");

        primaryStage.setScene(scene);
        primaryStage.show();

        ArrayList<LanguageController> languageControllers = new ArrayList<>();
        languageControllers.addAll(Arrays.asList(pythonController, swiftController, cppHeaderController, cppImplementationController, cppStandaloneController));
        mainController.passControllers(languageControllers);
        mainController.initBindings(scene, languageControllers);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
