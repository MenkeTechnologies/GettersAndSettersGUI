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

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader mainLoader = new FXMLLoader(mainPackage.Controller.class.getResource("/mainPackage/sample.fxml"));
        BorderPane root = mainLoader.load();
        Controller mainController = mainLoader.getController();

        TabPane mainTabPanes = (TabPane)root.getCenter();

        FXMLLoader pythonLoader = new FXMLLoader(Python.PythonController.class.getResource("/Python/PythonTab.fxml"));
        Tab pythonTab = pythonLoader.load();
        PythonController pythonController =  pythonLoader.getController();

        FXMLLoader swiftLoader = new FXMLLoader(Swift.class.getResource("/Swift/SwiftTab.fxml"));
        Tab swiftTab = swiftLoader.load();
        SwiftController swiftController = swiftLoader.getController();

        FXMLLoader cppHeaderLoader = new FXMLLoader(CppHeader.class.getResource("/CppHeader/CppHeaderTab.fxml"));
        Tab cppHeaderTab = cppHeaderLoader.load();
        CppHeaderController cppHeaderController = cppHeaderLoader.getController();

        FXMLLoader cppImplementationLoader = new FXMLLoader(CppImplementation.class.getResource("/CppImplementation/CppImplementationTab.fxml"));
        Tab cppImplementationTab = cppImplementationLoader.load();
        CppImplementationController cppImplementationController = cppImplementationLoader.getController();

        FXMLLoader cppStandaloneLoader = new FXMLLoader(CppStandalone.class.getResource("/CppStandalone/CppStandaloneTab.fxml"));
        Tab cppStandaloneTab = cppStandaloneLoader.load();
        CppStandaloneController cppStandaloneController = cppStandaloneLoader.getController();

        mainTabPanes.getTabs().addAll(pythonTab, swiftTab, cppHeaderTab, cppImplementationTab, cppStandaloneTab);
        mainTabPanes.getSelectionModel().selectFirst();


        primaryStage.setTitle("Getter And Setter Generator");
        Scene scene = new Scene(root, 600,1000);


        primaryStage.setScene(scene);
        primaryStage.show();

        mainController.passControllers(pythonController, swiftController, cppHeaderController, cppImplementationController, cppStandaloneController);
        pythonController.initBindings();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
