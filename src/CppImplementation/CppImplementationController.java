package CppImplementation;

import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import mainPackage.GeneratorAncestor;
import mainPackage.LanguageController;
import mainPackage.MainUtilities;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by jacobmenke on 4/1/17.
 */
public class CppImplementationController extends LanguageController implements Initializable{
    public CheckBox cppImplementationGetterCheckBox;
    public CheckBox cppImplementationSetterCheckBox;
    public CheckBox cppImplementationConstructorCheckBox;
    public CheckBox cppImplementationDeleterCheckBox;
    public TextArea cppImplementationInputTextArea;
    public TextArea cppImplementationOutputTextArea;
    public SplitPane cppImplementationSplitPane;
    public CppImplementationGenerator cppImplementationGenerator;
    String className;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textAreas.addAll(Arrays.asList(cppImplementationInputTextArea, cppImplementationOutputTextArea));

        mainSplitPane =cppImplementationSplitPane;

        cppImplementationInputTextArea.setText("class MyCustomVector{\n" +
                "private:\n" +
                "int currentSize;capacity;T* mainArray;\n" +
                "    void alloc_new();\n" +
                "public:\n" +
                "    MyCustomVector(int sz =0);\n" +
                "    void push_back(T& item);\n" +
                "    int size();\n" +
                "    T& at(int);\n" +
                "    T& operator[](int);\n" +
                "    T back();\n" +
                "    void pop();\n" +
                "    T front();\n" +
                "    bool isEmpty();\n" +
                "    void reserve(int);\n" +
                "};");

        cppImplementationGenerator = new CppImplementationGenerator();



    }


    public void generateCppImplementation(HashMap<String, Boolean> options, boolean dialog, String previousOutputText) throws Exception{
        String outputText = "";


    }
}
