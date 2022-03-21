package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class InputController {

    @FXML
    private TextField text;
    @FXML
    private Button fileButton;
    @FXML
    private Button animationButton;

    @FXML
    private void switchToAnimation() throws IOException {
        App.setRoot("animation");
    }

    @FXML
    private void setArray(ActionEvent event) throws IOException {
        String input = text.getText().replaceAll(" ", "");
        if (valid(input)) {
            App.setArray(input.split(",", 0));
            text.setText("DONE");
        } else {
            text.setText("INVALID");
            text.positionCaret(7);
        }
        text.getParent().requestFocus();
        text.requestFocus();
    }

    public void initialize() {
        text.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DOWN) {
                animationButton.requestFocus(); //javafx textfield treats down arrow as a text components instead of switching elements
            }
        });
    }
    
    private boolean valid(String v) { // checks if input is composed of digits only and is valid
        if (v.isEmpty())
            return false;
        char[] eachDigit = v.toCharArray();
        for (char a : eachDigit) {
            if (!(a >= '0' && a <= '9') && a != ',')
                return false;
        }
        return true;
    }

    @FXML
    private void fileInput() {
        FileChooser choose = new FileChooser();
        choose.getExtensionFilters().addAll(new ExtensionFilter("Text Files","*.txt*", "*.csv*"));
        processFile(choose.showOpenDialog(App.getStage()));
    }

    private void processFile(File file) {
        Scanner sc = null;
        if (file == null)
            return;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {}
        String content = "";
        
        
        
        
        while(sc.hasNext())
        {
            content += sc.nextLine();
        }
        if(valid(content)) 
            App.setArray(content.split(",", 0));

    }
}

