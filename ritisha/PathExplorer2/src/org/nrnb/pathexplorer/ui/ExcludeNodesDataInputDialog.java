package org.nrnb.pathexplorer.ui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyTable;

import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.nrnb.pathexplorer.logic.InclusionFactorHandler;

@SuppressWarnings("serial")
public class ExcludeNodesDataInputDialog extends JFrame {

	private JComboBox nodeProperty;
	private JComboBox operator;
	private JComboBox nodePropertyValueString;
	private JTextField nodePropertyValueNums;
	private JButton goButton;
	private JPanel panel1, panel2, panel3;
	private CyNetwork myNet;
	private String selectedNodeProperty;
	private String selectedOperator;
	private Collection<CyColumn> allNodeTableColumns;
	private CyColumn selectedColumn;
	private Object selectedNodePropertyVal;
	public ExcludeNodesDataInputDialog(CyNetwork myNetwork, CySwingAppAdapter adapt)
	{
		//Initialize all variables except nodePropertyValues
		super("Exclude Nodes with..");
		this.myNet = myNetwork;
		nodeProperty = new JComboBox();
		operator = new JComboBox();
		goButton = new JButton("Go");
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		selectedNodePropertyVal = new Object();
		nodePropertyValueNums = new JTextField();
		nodePropertyValueString = new JComboBox();
		this.allNodeTableColumns = new ArrayList<CyColumn>();
		
		panel1.validate();
		panel2.invalidate();
		
		nodeProperty.setSize(25, 12);
		operator.setSize(25, 12);
		nodePropertyValueNums.setSize(25, 12);
		nodePropertyValueString.setSize(25, 12);
		goButton.setSize(25, 12);
		
		//get all columns with node properties
		CyTable nodeTable = myNet.getDefaultNodeTable();
		this.allNodeTableColumns = nodeTable.getColumns();
		     
		//set values in the nodeProperty comboBoxe
		for(CyColumn currCol : allNodeTableColumns)
		{
			nodeProperty.addItem(currCol.getName());
		}
		
		//based on the selected node property, the options in operator comboBox change. Use listener.
		//String, Integer, Long, Double, Boolean, and Lists
		nodeProperty.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				panel2.removeAll();
				panel2.invalidate();
				selectedNodeProperty = (String) nodeProperty.getSelectedItem();
				
				Class<?> selectedNodePropertyType = null;
				for(CyColumn currCol : allNodeTableColumns)
				{
					if(currCol.getName().equals(selectedNodeProperty));
					{
						selectedNodePropertyType = currCol.getType(); 
						selectedColumn = currCol;
						
					}
				}
				
				if(selectedNodePropertyType.equals(Integer.TYPE) || 
						selectedNodePropertyType.equals(Long.TYPE) ||
						selectedNodePropertyType.equals(Double.TYPE))
						//=, <, >, <=, >=, != 
				{
					operator.removeAllItems();
					operator.addItem("=");
					operator.addItem("!=");
					operator.addItem("<");
					operator.addItem(">");
					operator.addItem("<=");
					operator.addItem(">=");
					
					//if it is nums, add text box for 3rd input
					nodePropertyValueNums = new JTextField();
					nodePropertyValueString = null;
					panel2.add(nodePropertyValueNums);
					panel2.add(goButton);
					panel2.validate();
					
					nodePropertyValueNums.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e)
						{
							selectedNodePropertyVal = nodePropertyValueNums.getText();
			        	}
					}
							);
			
				}
				
				else if(selectedNodePropertyType.equals(Boolean.TYPE))
				{
					operator.removeAllItems();
					nodePropertyValueString.removeAllItems();
					
					operator.addItem("=");
					
					//if it is bool, add comboBox for 3rd input
					nodePropertyValueString = new JComboBox();
					nodePropertyValueNums = null;
					panel2.add(nodePropertyValueString);
					panel2.add(goButton);
					panel2.validate();
					
					nodePropertyValueString.addItem("True");
					nodePropertyValueString.addItem("False");
					
					nodePropertyValueString.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e)
						{
							selectedNodePropertyVal = nodePropertyValueString.getSelectedItem();
			        	}
					}
							);

				}
					
				
				else if(selectedNodePropertyType.equals(String.class))
				{
					operator.removeAllItems();
					operator.addItem("Equals");
					operator.addItem("Does not equal");
					
					//if it is string, add comboBox for 3rd input
					nodePropertyValueString = new JComboBox();
					nodePropertyValueNums = null;
					panel2.add(nodePropertyValueString);
					panel2.add(goButton);
					panel2.validate();
					
					ArrayList<String> tempList = new ArrayList<String>();
					List<String> valuesList = new ArrayList<String>();
					nodePropertyValueString.removeAllItems();
					
					//get all the values in Column
					valuesList = selectedColumn.getValues(String.class);
					
					//add these in comboBox, without repeat
					for(String myVal : valuesList)
					{
						if(!myVal.equals(null) && !tempList.contains(myVal))
						{
							nodePropertyValueString.addItem(myVal);
							tempList.add(myVal);
						}
					}
					
					nodePropertyValueString.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e)
						{
							selectedNodePropertyVal = nodePropertyValueString.getSelectedItem();
			        	}
					}
							);
					
				}
			}
        	}
				);
		
		//get the selected operator
		operator.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				selectedOperator = (String) operator.getSelectedItem();
			}
        	}
				);
		
		goButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				//take the values, set the apt InclusionFactor
				InclusionFactorHandler myIFHandler = new InclusionFactorHandler();
				myIFHandler.handleIF(selectedColumn, selectedOperator, 
					selectedNodePropertyVal, myNet);
				dispose();
        	}
		}
				);
		
		panel1.setLayout(new GridLayout(1, 2, 15, 15));
		panel2.setLayout(new GridLayout(1, 2, 15, 15));
		panel3.setLayout(new GridLayout(1, 2, 15, 15));
		panel1.add(nodeProperty);
		panel1.add(operator);
		panel3.add(panel1);
		panel3.add(panel2);
		Container container = getContentPane();
		container.add(panel3);
		setSize(400, 100);
		setResizable(false);
		setLocationRelativeTo(null);
        setVisible(true);
	}
}
