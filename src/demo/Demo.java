package demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import Controller.Controller;
import Controller.MyController;
import Model.Model;
import Model.MyModel;
import View.MyView;
import View.View;

public class Demo {

	public void run() {	
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
				
		View view = new MyView(in, out);
		Model model = new MyModel();
		
		Controller controller = new MyController(view, model);
		view.setController(controller);
		model.setController(controller);
		
		view.start();

	}

}
