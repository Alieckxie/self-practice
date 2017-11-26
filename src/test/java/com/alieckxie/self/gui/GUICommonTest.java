package com.alieckxie.self.gui;

import org.junit.Test;

import com.alieckxie.self.gui.fortest.HelloApplication;

import javafx.application.Application;

public class GUICommonTest {

	@Test
	public void testStage() {
		Application.launch(HelloApplication.class, new String[] {});
	}

}
