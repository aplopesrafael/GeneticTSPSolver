package co.tspgenetic;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.rangepolicies.RangePolicyMinimumViewport;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.util.Range;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import co.mechanism.core.AbstractOptimizer;
import co.mechanism.core.ICostFunction;
import co.mechanism.core.ISearchAgent;
import co.mechanism.optimizers.evolutionary.Individual;
import co.mechanism.optimizers.evolutionary.genetic.ElitismReplacementProvider;
import co.mechanism.optimizers.evolutionary.genetic.GeneticOptimizer;
import co.mechanism.optimizers.evolutionary.genetic.SUSWithRankSelectionProvider;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private int[][] distanceMatrix = null;
	private JButton btnEjecutar;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JButton btnNmeroIteraciones;
	private int iterations = 10000;
	private int currentIteration;
	private int populationSize = 400;
	private double mutationProbability = 0.3;
	private double selectivePressure = 10;
	private int numberOfParents = 400;
	private JButton btnNewButton;
	protected AbstractOptimizer<? extends ISearchAgent> gop;
	private Individual sol;
	protected Double startCity;
	ITrace2D trace;
	protected List<int[]> cityCoords;
	private PathPanel pathPanel;
	private GraphSceneImpl graphScene;
	protected Tour tourWindow;

	


	/**
	 * Create the frame.
	 */
	public MainFrame() {
//		
		//this.setExtendedState(Frame.MAXIMIZED_BOTH);
		setTitle("Algoritmo Gen\u00E9tico para el TSP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 870, 481);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension((int) (this.getWidth() * 0.5),
				this.getHeight()/2));
		
		scrollPane.setBorder(BorderFactory.createTitledBorder("Info"));
		Chart2D chart = new Chart2D();
		trace = new Trace2DLtd(100000);
		trace.setPhysicalUnits("Iteraciones", "Costo del tour");
		trace.setColor(Color.RED);
		chart.addTrace(trace);
		chart.getAxisY().setRangePolicy(new RangePolicyMinimumViewport(new Range(0, 100)));
		chart.setPreferredSize(new Dimension((int) (this.getWidth() * 0.6),
				this.getHeight()/2));
		//panel.add(chart,BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		panel.add(scrollPane, BorderLayout.EAST);
		panel.add(chart, BorderLayout.CENTER);
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		btnEjecutar = new JButton("Ejecutar");
		btnEjecutar.setEnabled(false);
		//btnEjecutar.setIcon(new ImageIcon(MainFrame.class.getResource("/com/sun/java/swing/plaf/motif/icons/ScrollRightArrowActive.gif")));
		btnEjecutar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.this.evolve();
			}
		});
		toolBar.add(btnEjecutar);
		
		JButton btnCargarArchivo = new JButton("Cargar Archivo");
		btnCargarArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				if (chooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
//					System.out.println(chooser.getSelectedFile().getName());
//					System.out.println(Arrays.toString(chooser.getSelectedFile().getName().split("\\.")));
					if ("tsp".equals(chooser.getSelectedFile().getName().split("\\.")[1])) {
						cityCoords = Utils.EUC2D_TSPLIBFileToArrayList(chooser.getSelectedFile().getPath());
						distanceMatrix = Utils.getDistanceMatrixFromCoords(cityCoords);
						startCity = null;
					} else {
						@SuppressWarnings("rawtypes")
						Map result = Utils.getDistanceMatrixFromFile(chooser.getSelectedFile().getPath());
						distanceMatrix = (int[][]) result.get("distances");
						startCity = (Double)result.get("startCity");
					}
					setTitle("Algoritmo Genético para el TSP - "+distanceMatrix.length+" Ciudades");
					tourWindow = new Tour(null,cityCoords,null);
					tourWindow.setVisible(true);
					btnEjecutar.setEnabled(true);
				}
				
				
			}
		});
		//btnCargarArchivo.setIcon(new ImageIcon(MainFrame.class.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")));
		toolBar.add(btnCargarArchivo);
		
		btnNmeroIteraciones = new JButton("N\u00FAmero Iteraciones:"+this.iterations);
		//btnNmeroIteraciones.setIcon(new ImageIcon(MainFrame.class.getResource("/com/sun/java/swing/plaf/windows/icons/DetailsView.gif")));
		btnNmeroIteraciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					iterations = Integer.parseInt(JOptionPane.showInputDialog(MainFrame.this, "Número de iteraciones"));
					btnNmeroIteraciones.setText("N\u00FAmero Iteraciones:"+MainFrame.this.iterations);
				}catch(Exception e){}
			}
		});
		toolBar.add(btnNmeroIteraciones);
		
		btnNewButton = new JButton("Par\u00E1metros");
		//btnNewButton.setIcon(new ImageIcon(MainFrame.class.getResource("/com/sun/java/swing/plaf/motif/icons/Question.gif")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ConfigurationDialog(MainFrame.this).setVisible(true);
			}
		});
		toolBar.add(btnNewButton);
		
		
	}

	
	
	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public double getMutationProbability() {
		return mutationProbability;
	}

	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	public double getSelectivePressure() {
		return selectivePressure;
	}

	public void setSelectivePressure(double selectivePressure) {
		this.selectivePressure = selectivePressure;
	}

	public int getNumberOfParents() {
		return numberOfParents;
	}

	public void setNumberOfParents(int numberOfParents) {
		this.numberOfParents = numberOfParents;
	}
	
	public void updateBoard(String msg){
		currentIteration++;
		textArea.append("Iteración "+currentIteration+" - "+msg);
	}
	
	public void updateTrace(double cost){
		trace.addPoint(currentIteration, cost);
	}

	private void evolve() {
		this.textArea.setText("");
		trace.removeAllPoints();
		currentIteration = 0;
		Thread t = new Thread(){
			public void run(){
				ICostFunction tsp = new TSPCostFunction(distanceMatrix);
				List<Double> min = Collections.nCopies(distanceMatrix.length, Double.valueOf(0));
				List<Double> max = Collections.nCopies(distanceMatrix.length, Double.valueOf(distanceMatrix.length));
				gop = new GeneticOptimizer(populationSize, mutationProbability, min, max,tsp,
						new SUSWithRankSelectionProvider(selectivePressure, numberOfParents),
						new TSPOnePointCrossoverProvider(),
						new ElitismReplacementProvider(),
						new TSP2OPTMutationProvider(), new TSPRandomPathProvider());
				for (int i = 0; i < iterations; i++) {
					gop.evolve();
					sol = ((Individual) gop.getBestSolution()).clone();
					SwingUtilities.invokeLater(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							List<Double> position = sol.getPosition();
							updateBoard("Costo: "+sol.getValue()+" Tour: "+position+System.getProperty("line.separator"));
							updateTrace(sol.getValue());
//							graphScene.setTour(position);
//							graphScene.paintGraph();
//							pathPanel.setTour(position);
//							pathPanel.repaint();
							
						}
						
					});
					
				}
				
				SwingUtilities.invokeLater(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						while(currentIteration < iterations) {}
						List<Double> position = sol.getPosition();
						if (startCity != null)
							Collections.rotate(position, -(position.indexOf(startCity)));
						textArea.append("Costo Final: "+sol.getValue()+" Tour Final: "+position+System.getProperty("line.separator"));
						tourWindow.dispose();
						new Tour(position,cityCoords,sol.getValue()).setVisible(true);
						
					}});

			}
			
		};
		t.start();
		// TODO Auto-generated method stub
				
	}

}
