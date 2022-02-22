package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javafx.animation.PathTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class AnimationController {
    private ArrayList<NodeAnimationClass> animationList;
    private int cycle = 0;

    private Circle fakeCircle;
    @FXML
    private VBox vbox;
    @FXML
    private HBox content;
    @FXML
    private HBox indexes;

    @FXML
    private Button repeat;

    @FXML
    private void switchToInput() throws IOException {
        App.setRoot("input");
        System.out.println(Arrays.toString(App.getData()));
    }

    @FXML
    private void repeat() {
        if (cycle < animationList.size()) {
            animate(animationList.get(cycle).from, animationList.get(cycle).to);
        } else {
            repeat.setText("Reset");
            repeat.setOnAction(e -> {
                try {
                    App.setRoot("animation");
                } catch (IOException ee) {
                }
            });
        }
    }

    public void initialize() {
        animationList = new ArrayList<>();
        addElements();
        number();
        new QuickSort(App.getData(), this);
    }

    public void addAnimation(int from, int to, boolean index) {
        animationList.add(index ? new NodeAnimationClass(from, to) : new NodeAnimationClass(1, 2));
    }

    public void testing(int a, int b, int c) { // swaps opposite indexes
        if (a > 0 && a < c / 2 && b < c && b > c / 2) {
            animate(a, b);
        }
    }

    public void fakeAnimate() { // when doing consecutive animations, if the elements switched are the same, the
                                // fast pace causes some errors, to fix this the program does a fake animation
                                // using a circle
        PathTransition a = new PathTransition();
        Node node = fakeCircle;
        a.setNode(node);
        a.setDuration(Duration.seconds(0.001));
        a.setOnFinished(e -> {
            cycle++;
            repeat();
        });
        ArcTo arcTo = new ArcTo();
        arcTo.setLargeArcFlag(false);
        arcTo.setSweepFlag(true);
        arcTo.setRadiusX(1);
        arcTo.setRadiusY(1);
        arcTo.setX(node.getLayoutX());
        arcTo.setY(node.getLayoutY() + 100);
        Path path = new Path();
        path.getElements().addAll(new MoveTo(node.getLayoutX(), node.getLayoutY()), arcTo);
        a.setPath(path);
        a.play();
    }

    public void animate(int from, int to) {
        int time = 2;
        PathTransition move1 = new PathTransition();
        PathTransition move2 = new PathTransition();
        Node node1 = content.getChildren().get(from);
        Node node2 = content.getChildren().get(to);
        highlight(node1, node2, Color.GREEN);
        move1.setNode(node1);
        move2.setNode(node2);
        move1.setDuration(Duration.seconds(Math.sqrt(Math.abs((from - to)) + time)));
        move2.setDuration(Duration.seconds(Math.sqrt(Math.abs((from - to)) + time)));
        move1.setPath(makePath(node1, node2, true));
        move2.setPath(makePath(node2, node1, false));
        move1.setOnFinished(e -> {
            highlight(node1, node2, Color.BLUE);
        });

        move2.setOnFinished(b -> {
            node1.setTranslateX(0);
            node2.setTranslateX(0);
            switchNode(from, to);
            if (cycle != animationList.size() - 1 && animationList.get(cycle).from != animationList.get(cycle + 1).from
                    && animationList.get(cycle).to != animationList.get(cycle + 1).to
                    && animationList.get(cycle).from != animationList.get(cycle + 1).to
                    && animationList.get(cycle).to != animationList.get(cycle + 1).from) {
                cycle++;
                repeat();
            } else {
                fakeAnimate();
            }
            // cycle++;
            // repeat();

        });
        move1.play();
        move2.play();
    }

    public Path makePath(Node from, Node to, boolean right) {
        // creating variables
        double distance = to.getLayoutX() - from.getLayoutX();
        double x = from.boundsInLocalProperty().get().getCenterX();
        double y = from.boundsInLocalProperty().get().getCenterY();
        double fromX = x;
        double fromY = y;
        double toX = x + distance; // 300
        double toY = y;
        // Creating destination and parameters
        ArcTo arcTo = new ArcTo();
        arcTo.setLargeArcFlag(false);
        arcTo.setSweepFlag(true);
        arcTo.setRadiusX((toX + fromX) / 2);
        arcTo.setRadiusY(right ? arcTo.getRadiusX() + 140 : arcTo.getRadiusX()); // distance / 2 + 200
        arcTo.setX(toX);
        arcTo.setY(toY);
        // creating path
        Path path = new Path();
        path.getElements().addAll(new MoveTo(fromX, fromY), arcTo);
        path.setStroke(Color.MAGENTA);
        path.getStrokeDashArray().setAll(5d, 5d);
        // content.getChildren().add(path);
        return path;
    }

    public void switchNode(int a, int b) {
        ObservableList<Node> workingCollection = FXCollections.observableArrayList(content.getChildren());
        Collections.swap(workingCollection, a, b);
        // Debugging
        // System.out.println(" ");
        // System.out.println("Switched: ");
        // for (Node i : workingCollection) {
        // Label aa = (Label) ((StackPane) i).getChildren().get(1);
        // System.out.print(aa.getText() + " ");
        // }
        content.getChildren().setAll(workingCollection);
    }

    public void highlight(Node a, Node b, Color c) {
        Circle circleA = (Circle) ((StackPane) a).getChildren().get(0);
        circleA.setFill(c);
        Circle circleB = (Circle) ((StackPane) b).getChildren().get(0);
        circleB.setFill(c);
    }

    public void number() {
        for (Node a : content.getChildren()) {
            StackPane aa = (StackPane) a;
            aa.setOnMouseClicked(e -> {
                System.out.println(content.getChildren().indexOf(aa));
            });
        }
    }

    public void addElements() {
        for (int i = 0; i < App.getData().length; i++) {
            // creates circle
            Circle circle = new Circle(30, 30, 30);
            circle.setFill(Color.BLUE);
            // creates text
            Label label = new Label(App.getData()[i] + "");
            label.setTextFill(Color.BLACK);
            // creates stack to join the two, since stack automatically centers its elements
            StackPane join = new StackPane();
            // add circle and text to stack
            join.getChildren().addAll(circle, label);
            // adds stack to HBox
            content.getChildren().add(join);

            // makes index
            Label index = new Label(i + "");
            indexes.getChildren().add(index);
            indexes.setSpacing(62);
        }
        fakeCircle = new Circle(10, 10, 10);
        fakeCircle.setVisible(false);
        vbox.getChildren().add(0, fakeCircle);
        content.setViewOrder(-1);

    }
}

class NodeAnimationClass {
    int from, to;

    public NodeAnimationClass(int from, int to) {
        this.from = from;
        this.to = to;
    }
}