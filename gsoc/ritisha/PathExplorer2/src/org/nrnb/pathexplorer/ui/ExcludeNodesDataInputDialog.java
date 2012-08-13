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
	private JPanel panel1;
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
		selectedNodePropertyVal = new Object();
		nodePropertyValueNums = new JTextField();
		nodePropertyValueString = new JComboBox();
		
		//get all columns with node properties
		CyTable nodeTable = myNet.getDefaultNodeTable();
		this.allNodeTableColumns = new ArrayList<CyColumn>();
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
					operator.addItem("=");
					operator.addItem("!=");
					operator.addItem("<");
					operator.addItem(">");
					operator.addItem("<=");
					operator.addItem(">=");
					
					//if it is nums, add text box for 3rd input
					nodePropertyValueNums = new JTextField();
					nodePropertyValueString = null;
					
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
					operator.addItem("=");
					
					//if it is bool, add comboBox for 3rd input
					nodePropertyValueString = new JComboBox();
					nodePropertyValueNums = null;
					
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
					operator.addItem("Equals");
					operator.addItem("Does not equal");
					
					//if it is string, add comboBox for 3rd input
					nodePropertyValueString = new JComboBox();
					nodePropertyValueNums = null;
					
					ArrayList<String> tempList = new ArrayList<String>();
					List<String> valuesList = new ArrayList<String>();
					
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
        	}
		}
				);
		
		panel1.setLayout(new GridLayout(1, 4, 80, 15));
		panel1.add(nodeProperty);
		panel1.add(operator);
		if(nodePropertyValueNums.equals(null))
			panel1.add(nodePropertyValueString);
		else if(nodePropertyValueString.equals(null))
			panel1.add(nodePropertyValueNums);
		panel1.add(goButton);
		
		Container container = getContentPane();
		container.add(panel1);
		setSize(400, 400);
        setVisible(true);
	}
	
	
}
