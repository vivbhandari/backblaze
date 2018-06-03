package backblaze.assignment1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JApplet;
import javax.swing.JPanel;

class Plotter extends JPanel {
	private static final long serialVersionUID = 1L;
	final double X_CENTER = 400;
	final double Y_CENTER = 300;
	InputReader input;
	double radius;
	int numOfPoints;
	double average;
	double angleIncrement;

	public Plotter(InputReader readInput) {
		this.input = readInput;
		radius = 150.0f;
		numOfPoints = input.getNumbers().size();
		average = input.getAverage();
		angleIncrement = (double) (2.0 * Math.PI / numOfPoints);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(10.0f));

		int j = 0;
		for (double angle = 0.0f; angle < 2.0
				* Math.PI; angle += angleIncrement) {
			double x = X_CENTER + (int) (radius * Math.cos(angle));
			double y = Y_CENTER + (int) (radius * Math.sin(angle));
			double number = input.getNumbers().get(j);

			if (number < average - 1.5) {
				g2.setColor(Color.cyan);
			} else if (number > average + 1.5) {
				g2.setColor(Color.magenta);
			} else {
				g2.setColor(Color.gray);
			}

			g2.draw(new Line2D.Double(X_CENTER, Y_CENTER, x, y));
			j++;
		}
	}// end paint

}// end class

public class Appletframe extends JApplet {
	private static final long serialVersionUID = 1L;
	private static String defaultFile = "../input/input1.txt";

	public void init() {
		String inputFilePath = getParameter("inputFilePath");
		if (inputFilePath == null) {
			inputFilePath = defaultFile;
		}
		this.setSize(900, 900);
		InputReader input = new InputReader(inputFilePath);
		Plotter plotter = new Plotter(input);
		add(plotter);
	}
}
