package mainPackage;

import javafx.scene.control.Alert;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Created by jacobmenke on 4/1/17.
 */
public class MainUtilities {


    public static void copyToClipboard(String outputText){
            StringSelection stringSelection = new StringSelection(outputText);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);

    }

    public static void generateAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("No properties found");
        alert.showAndWait();
    }

    public static  void genererateDuplicateAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Duplicate properties found");
        alert.showAndWait();
    }

    public static String capitalizeFirstLetter(String name){

      if (name.charAt(0) >= 97){
          String uppercaseFirst = name.substring(0,1).toUpperCase();

          return uppercaseFirst+name.substring(1);
      } else {
          return name;
      }
    }
}
