package org.nrnb.pathexplorer.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dialog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyTable;

import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.nrnb.pathexplorer.logic.InclusionFactorHandler;

@SuppressWarnings("serial")
public class ExcludeNodesDataInputDialog extends JDialog {

	private JComboBox nodeProperty;
	private JComboBox operator;
	private JComboBox nodePropertyValue;
	private JButton goButton;
	private JPanel panel1;
	private String selectedNodeProperty;
	private String selectedOperator;
	private CyColumn selectedColumn;
	private Object selectedNodePropertyVal;
	private Collection<CyColumn> allNodeTableColumns;
	private CySwingAppAdapter adapter;
	private CyNetwork myNet;
	
	public ExcludeNodesDataInputDialog(CyNetwork myNetwork, CySwingAppAdapter adapt)
	{
		//Initialize all variables except nodePropertyValues
		super();
		setTitle("Exclude Nodes with..");
		adapter = adapt;
		myNet = myNetwork;
		nodeProperty = new JComboBox();
		operator = new JComboBox();
		nodePropertyValue = new JComboBox();
		nodePropertyValue.setEditable(true);
		goButton = new JButton("Go");
		panel1 = new JPanel();
		selectedNodePropertyVal = new Object();
		this.allNodeTableColumns = new ArrayList<CyColumn>();
		
		//get all columns with node properties
		CyTable nodeTable = myNet.getDefaultNodeTable();
		this.allNodeTableColumns = nodeTable.getColumns();
		     
		//set values in the nodeProperty comboBoxes
		for(CyColumn currCol : allNodeTableColumns)
		{
			nodeProperty.addItem(currCol.getName());
		}
		
		//based on the selected node property, the options in operator comboBox change. Use listener.
		//String, Integer, Long, Double, Boolean, and Lists
		nodeProperty.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("1st drop down listener");
				selectedNodeProperty = (String) nodeProperty.getSelectedItem();
				System.out.println("selected item is " + selectedNodeProperty);
				int i = 0;
				for(CyColumn currCol : allNodeTableColumns)
				{
					i++;
					if(selectedNodeProperty.equals(currCol.getName()))
					{ 
						selectedColumn = currCol;
						System.out.println(selectedColumn.getType() + " "+ currCol.getName()+ ":"+ selectedNodeProperty +" "+ i);
					}
				}
				
				if(selectedColumn.getType().equals(Integer.class) || 
						selectedColumn.getType().equals(Long.class) ||
						selectedColumn.getType().equals(Double.class))
						//=, <, >, <=, >=, != 
				{
					System.out.println("Type is number");
					operator.removeAllItems();
					operator.addItem("=");
					operator.addItem("!=");
					operator.addItem("<");
					operator.addItem(">");
					operator.addItem("<=");
					operator.addItem(">=");
					
					nodePropertyValue.removeAll();
				}
				
				else if(selectedColumn.getType().equals(Boolean.class))
				{
					System.out.println("Type is boolean");
					operator.removeAllItems();
					nodePropertyValue.removeAllItems();
					
					operator.addItem("=");
					nodePropertyValue.addItem("True");
					nodePropertyValue.addItem("False");
				}
									
				else if(selectedColumn.getType().equals(String.class))
				{
					System.out.println("Type is string");
					operator.removeAllItems();
					operator.addItem("Equals");
					operator.addItem("Does not equal");
					
					ArrayList<String> tempList = new ArrayList<String>();
					List<String> valuesList = new ArrayList<String>();
					nodePropertyValue.removeAllItems();
					
					//get all the values in Column
					valuesList = selectedColumn.getValues(String.class);
					
					//add these in comboBox, without repeat
					for(String myVal : valuesList)
					{
						if(!myVal.equals(null) && !tempList.contains(myVal))
						{
							nodePropertyValue.addItem(myVal);
							tempList.add(myVal);
						}
					}	
				}
			}
        	}
				);
		
		goButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				//take the values, set the apt InclusionFactor
				selectedOperator = (String) operator.getSelectedItem();
				selectedNodePropertyVal = nodePropertyValue.getSelectedItem();
				InclusionFactorHandler myIFHandler = new InclusionFactorHandler(adapter);
				myIFHandler.handleIF(selectedColumn, selectedOperator, 
					selectedNodePropertyVal, myNet);
				dispose();
        	}
		}
				);
		
		panel1.setLayout(new GridLayout(1, 4, 15, 15));
		
		nodeProperty.setPreferredSize(new Dimension(25, 12));
		nodeProperty.setMaximumSize(new Dimension(25, 12));
		operator.setSize(12, 6);
		nodePropertyValue.setSize(25, 12);
		goButton.setSize(12, 6);
		
		panel1.add(nodeProperty);
		panel1.add(operator);
		panel1.add(nodePropertyValue);
		panel1.add(goButton);
		Container container = getContentPane();
		container.add(panel1);
		setSize(400, 100);
		setAlwaysOnTop(true);
		setResizable(false);
		setLocationRelativeTo(null);
        setVisible(true);
	}
}
