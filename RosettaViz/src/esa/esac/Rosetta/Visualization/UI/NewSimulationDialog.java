package esa.esac.Rosetta.Visualization.UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import esa.esac.Rosetta.Visualization.GlobalTools;
import esa.esac.Rosetta.Visualization.Universe;
import esa.esac.Rosetta.Visualization.DB.ConnectToDatabase;
import esa.esac.Rosetta.Visualization.DB.MaterialReader;
import esa.esac.Rosetta.Visualization.DB.VisualDataReader;
import esa.esac.Rosetta.Visualization.DB.VisualObjectReader;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpringLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import jme3test.light.TestPssmShadow;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class NewSimulationDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblStartDate;
	private JLabel lblEndDate;
	private JButton okButton;
	private JLabel lblTimeStep;
	
	private MainSimulationWindow mainSimWindow;
	private JButton cancelButton;
	final JSpinner speedSpinner;

	/**
	 * Create the dialog.
	 */
	
	public NewSimulationDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("New Simulation");
		setBounds(10, 100, 384, 223);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		{
			lblStartDate = new JLabel("Start Date");
			springLayout.putConstraint(SpringLayout.NORTH, lblStartDate, 26, SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.WEST, lblStartDate, 38, SpringLayout.WEST, getContentPane());
			getContentPane().add(lblStartDate);
		}
		{
			lblEndDate = new JLabel("End Date:");
			springLayout.putConstraint(SpringLayout.NORTH, lblEndDate, 0, SpringLayout.NORTH, lblStartDate);
			getContentPane().add(lblEndDate);
		}
		
		
		final JDateChooser startDateChooser = new JDateChooser();
		startDateChooser.setDateFormatString("MMM d, yyyy hh:mm:ss a");
		springLayout.putConstraint(SpringLayout.NORTH, startDateChooser, 6, SpringLayout.SOUTH, lblStartDate);
		springLayout.putConstraint(SpringLayout.WEST, startDateChooser, 0, SpringLayout.WEST, lblStartDate);
		getContentPane().add(startDateChooser);
		
		final JDateChooser endDateChooser = new JDateChooser();
		springLayout.putConstraint(SpringLayout.NORTH, endDateChooser, 6, SpringLayout.SOUTH, lblEndDate);
		springLayout.putConstraint(SpringLayout.EAST, lblEndDate, 0, SpringLayout.EAST, endDateChooser);
		springLayout.putConstraint(SpringLayout.EAST, endDateChooser, -24, SpringLayout.EAST, getContentPane());
		endDateChooser.setDateFormatString("MMM d, yyyy hh:mm:ss a");
		getContentPane().add(endDateChooser);
		{
			okButton = new JButton("OK");
			springLayout.putConstraint(SpringLayout.SOUTH, okButton, -10, SpringLayout.SOUTH, getContentPane());
			springLayout.putConstraint(SpringLayout.EAST, okButton, 0, SpringLayout.EAST, lblStartDate);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//System.out.println("-==========================- AM I HERE?? -==========================-");
					try {
						//System.out.println("-==========================- WHAT ABOUT HERE?? -==========================-");
						//System.out.println("start date chooser date: " + startDateChooser.getDate());
						//System.out.println("end date chooser date: " + endDateChooser.getDate());
						//if(!Universe.getApplication().dateArr.contains(startDateChooser.getDate()) || !Universe.getApplication().dateArr.contains(endDateChooser.getDate()) || !startDateChooser.getDate().before(endDateChooser.getDate()))
						if(!startDateChooser.getDate().before(endDateChooser.getDate()))
						{
							//System.out.println("-==========================- DATES WRONG?? -==========================-");
							Universe.getApplication().setRunning(false);	
							
							//custom title, error icon
							JOptionPane.showMessageDialog(null,
							    "Dates not in database! Please correct the input.",
							    "Input error",
							    JOptionPane.ERROR_MESSAGE);
							
							//System.out.println(startDateChooser.getDate() + " and " + endDateChooser.getDate());
							//dSystem.out.println("Start date before end date ? -> " + startDateChooser.getDate().before(endDateChooser.getDate()));
							
							//System.out.println("Start date valid ? -> " + Universe.getApplication().dateArr.contains(startDateChooser.getDate()));
							//System.out.println("End date valid ? -> " + Universe.getApplication().dateArr.contains(endDateChooser.getDate()));
							
							Universe.getApplication().setSimStart(false);
						}
						else
						{
							//System.out.println("-==========================- Dates ok! -==========================-");
							
							// close this window
							NewSimulationDialog.this.dispose();
		
							// read objects
							new VisualObjectReader();
							
							// start 3D window
							//mainSimWindow = new MainSimulationWindow();
							
							GlobalTools.executor.submit(new MainSimulationWindow());
							
							Universe.getApplication().setStartDate(startDateChooser.getDate());
							Universe.getApplication().setEndDate(endDateChooser.getDate());
							Universe.getApplication().setRunning(true);
							Universe.getApplication().setSimStart(true);
							
							//Universe.getApplication().simSpeed 
							//speedSpinner.get
							Universe.getApplication().simSpeed = (Integer)speedSpinner.getValue();
							
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
							    "Something went wrong. Check the log!",
							    "Unknown error type",
							    JOptionPane.ERROR_MESSAGE);
						
						e.printStackTrace();
					}
				}
			});
			getContentPane().add(okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					NewSimulationDialog.this.dispose();
				}
			});
			springLayout.putConstraint(SpringLayout.NORTH, cancelButton, 0, SpringLayout.NORTH, okButton);
			springLayout.putConstraint(SpringLayout.WEST, cancelButton, 6, SpringLayout.EAST, okButton);
			getContentPane().add(cancelButton);
			cancelButton.setActionCommand("Cancel");
		}
		{
			lblTimeStep = new JLabel("Time Step:");
			springLayout.putConstraint(SpringLayout.NORTH, lblTimeStep, 36, SpringLayout.SOUTH, startDateChooser);
			springLayout.putConstraint(SpringLayout.WEST, lblTimeStep, 0, SpringLayout.WEST, lblStartDate);
			getContentPane().add(lblTimeStep);
		}
		
		speedSpinner = new JSpinner();
		springLayout.putConstraint(SpringLayout.NORTH, speedSpinner, -1, SpringLayout.NORTH, lblTimeStep);
		springLayout.putConstraint(SpringLayout.WEST, speedSpinner, 19, SpringLayout.EAST, lblTimeStep);
		springLayout.putConstraint(SpringLayout.EAST, speedSpinner, 0, SpringLayout.EAST, cancelButton);
		speedSpinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		getContentPane().add(speedSpinner);
		
	}
}
