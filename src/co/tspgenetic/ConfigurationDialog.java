package co.tspgenetic;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class ConfigurationDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JSpinner population;
	private JSpinner mutationProb;
	private JSpinner selectiveP;
	private JSpinner parents;
	private final MainFrame parentFrame;

	/**
	 * Create the dialog.
	 */
	public ConfigurationDialog(MainFrame parent) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		parentFrame = parent;
		setModal(true);
		setTitle("Configuraci\u00F3n");
		setBounds(100, 100, 326, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Par\u00E1metros del Algoritmo Gen\u00E9tico", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new GridLayout(4, 2));
			{
				JLabel lblNewLabel = new JLabel("Tama\u00F1o de la poblaci\u00F3n");
				panel.add(lblNewLabel);
			}
			{
				population = new JSpinner();
				population.setModel(new SpinnerNumberModel(new Integer(50), new Integer(1), null, new Integer(1)));
				panel.add(population);
			}
			population.setValue(parent.getPopulationSize());
			{
				JLabel lblNewLabel_1 = new JLabel("Probabilidad de mutaci\u00F3n");
				panel.add(lblNewLabel_1);
			}
			{
				mutationProb = new JSpinner();
				mutationProb.setModel(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.1));
				panel.add(mutationProb);
			}
			mutationProb.setValue(parent.getMutationProbability());
			{
				JLabel lblNewLabel_2 = new JLabel("Presi\u00F3n selectiva");
				panel.add(lblNewLabel_2);
			}
			{
				selectiveP = new JSpinner();
				selectiveP.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(1)));
				panel.add(selectiveP);
			}
			selectiveP.setValue(parent.getSelectivePressure());
			{
				JLabel lblNmeroDeInvid = new JLabel("N\u00FAmero de padres");
				panel.add(lblNmeroDeInvid);
			}
			{
				parents = new JSpinner();
				parents.setModel(new SpinnerNumberModel(new Integer(2), new Integer(2), null, new Integer(1)));
				panel.add(parents);
			}
			parents.setValue(parent.getNumberOfParents());
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Aceptar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						parentFrame.setPopulationSize((Integer)population.getValue());
						parentFrame.setMutationProbability((Double)mutationProb.getValue());
						parentFrame.setSelectivePressure((Double)selectiveP.getValue());
						parentFrame.setNumberOfParents((Integer)parents.getValue());
						dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

}
