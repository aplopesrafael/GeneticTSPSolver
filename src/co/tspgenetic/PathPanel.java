package co.tspgenetic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class PathPanel extends JPanel {

	private List<int[]> coords;
	private List<Double> tour;
	private int maxX;
	private int maxY;

	public PathPanel() {
		super();
		this.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		paintGraph();
	}

	public void paintGraph() {
		Graphics2D g = (Graphics2D) getGraphics();
		paintCities(g);
		paintTour(g);
	}
	

	private void paintCities(Graphics2D g) {
		if (coords != null) {
			g.setColor(Color.BLUE);
			for (int[] city : coords) {
				g.fillOval((int) city[0] - 4, (int) city[1] - 4, 4, 4);
			}
		}

	}

	private void paintTour(Graphics2D g) {
		// boolean eq = true;
		// if (this.tour != null)
		// for(int i = 0; i < t.size(); i++) {
		// if (t.get(i).intValue() != this.tour.get(i).intValue()) {
		// eq = false;
		// break;
		// }
		// }
		//this.tour = Utils.doubleListDeepCopy(t);
		if (this.tour != null) {
			int[] city1;
			int[] city2;
			g.setColor(Color.BLACK);
			for (int i = 0; i < tour.size() - 1; i++) {

				city1 = coords.get(tour.get(i).intValue() - 1);
				city2 = coords.get(tour.get(i + 1).intValue() - 1);
				g.drawLine(city1[0], city1[1], city2[0], city2[1]);
				// g.setColor(Color.BLUE);
				// g.fillOval((int)city1[0]-4, (int)city1[1]-4, 4, 4);
			}
			city1 = coords.get(tour.get(tour.size() - 1).intValue() - 1);
			city2 = coords.get(tour.get(0).intValue() - 1);
			// g.setColor(Color.BLACK);
			g.drawLine(city1[0], city1[1], city2[0], city2[1]);
			// g.setColor(Color.BLUE);
			// g.fillOval((int)city1[0]-4, (int)city1[1]-4, 4, 4);

		}
	}

	public void reset() {
		this.tour = null;
		this.coords = null;
	}

	public List<int[]> getCoords() {
		return coords;
	}

	public void setCoords(List<int[]> coords) {
		if (coords != null) {
			this.coords = new ArrayList<int[]>();
			Dimension size = this.getSize();
			maxX = 0;
			maxY = 0;
			int padding = 15;
			for (int[] city : coords) {
				if (maxX < city[0])
					maxX = city[0];
				if (maxY < city[1])
					maxY = city[1];
			}
			for (int[] city : coords) {
				this.coords
						.add(new int[] {
								(int) (city[0] * (size.width - padding)
										/ (double) maxX + padding / 2.0),
								(int) (city[1] * (size.height - padding)
										/ (double) maxY + padding / 2.0) });
			}
		}
	}

	public List<Double> getTour() {
		return tour;
	}

	public void setTour(List<Double> tour) {
		this.tour = tour;
	}



}
