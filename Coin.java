//Barsha Shrestha
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
//class coin to paint the coins
public class Coin extends JPanel {
	private Graphics graphics;

	public Coin(Graphics g, int x, int y, int diam) {
		super();
		paintCoins(g, x, y, diam);
		graphics=g;
	}

	public Coin() {

	}

	// paint coins
	public void paintCoins(Graphics g, int x, int y, int diameter)

	{
		g.setColor(Color.gray);

		g.fillOval(x, y, diameter, diameter);

	}
	//paint with another color
	public void paintNewColorForSelectedCoin( Graphics g, int x, int y,
			int diameter, Color color) {
		graphics.setColor(color);

		graphics.fillOval(x, y, diameter, diameter);
	}

}
