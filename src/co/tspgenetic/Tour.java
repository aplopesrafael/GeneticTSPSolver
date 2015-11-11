package co.tspgenetic;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Tour extends JFrame {

	private JPanel contentPane;
	private GraphSceneImpl graphScene;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public Tour(final List<Double> tour, final List<int[]> cityCoords, Double cost) {
		setTitle("Costo del tour: "+cost);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 414);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		graphScene = new GraphSceneImpl((int) (this.getWidth()-100),this.getHeight()-100);
		//cityCoords = Utils.EUC2D_TSPLIBFileToArrayList("berlin52.tsp");
		//tour = Arrays.asList(31.0, 17.0, 3.0, 18.0, 22.0, 1.0, 49.0, 32.0, 45.0, 19.0, 41.0, 8.0, 9.0, 10.0, 33.0, 43.0, 4.0, 5.0, 24.0, 48.0, 37.0, 34.0, 35.0, 36.0, 39.0, 40.0, 38.0, 15.0, 6.0, 25.0, 28.0, 27.0, 26.0, 47.0, 13.0, 14.0, 52.0, 11.0, 51.0, 12.0, 46.0, 44.0, 16.0, 29.0, 50.0, 20.0, 23.0, 21.0, 42.0, 2.0, 7.0, 30.0);
		scrollPane.setViewportView(graphScene.createView());
		graphScene.setCoords(cityCoords);
		graphScene.setTour(tour);
		graphScene.paintGraph();
		
	}

}
