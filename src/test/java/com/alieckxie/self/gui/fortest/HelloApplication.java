package com.alieckxie.self.gui.fortest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HelloApplication extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Label label = new Label("Hello Application!");
		label.setFont(new Font(100));
		stage.setTitle("Hello Application");
		Button button = new Button("Red");
		button.setOnAction(event -> {
			label.setTextFill(Color.RED);
			System.out.println("123");
		});
		StackPane pane = new StackPane();
		pane.getChildren().add(label);
		pane.getChildren().add(button);
		Scene scene = new Scene(pane, 3000, 500);
		stage.setScene(scene);
		stage.show();
	}

}
