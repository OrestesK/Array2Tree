package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;
    private static Stage stage;
    private static int[] data;

    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        data = new int[] { 5, 4, 3, 2, 1, 5 };
        scene = new Scene(loadFXML("input"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("Array2Tree");
        stage.show();
    }

    static void setArray(String[] ar) {
        data = new int[ar.length];
        for (int i = 0; i < ar.length; i++) {
            data[i] = Integer.parseInt(ar[i]);
        }
        // new QuickSort(data);
        // new BinarySearchTree(data);
    }

    static int[] getData() {
        return data;
    }

    static void setData(int[] value) {
        data = value;
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}