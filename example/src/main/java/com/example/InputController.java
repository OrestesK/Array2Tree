package com.example;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class InputController {

    @FXML
    private TextField text;

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
    }

    private boolean valid(String v) { // checks if input is composed of digits only
        char[] eachDigit = v.toCharArray();
        for (char a : eachDigit) {
            if (!(a >= '0' && a <= '9') && a != ',')
                return false;
        }
        return true;
    }

}
