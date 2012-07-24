package org.nrnb.pathexplorer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.cytoscape.model.CyTable;

import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyNode;

public class ExcludeNodesDataInputDialog extends JFrame {

	private JComboBox nodeProperty;
	private JComboBox operator;
	private JTextField nodePropertyValue;
	private JButton addButton;
	private JButton goButton;
	private JPanel panel1, panel2;
	private CyNetwork myNet;
	private String selectedNodeProperty;
	private String selectedOperator;
	private Collection<CyColumn> allColumns;
	
	public ExcludeNodesDataInputDialog(CyNetwork myNet)
	{
		super("Exclude Nodes with..");
		this.myNet = myNet;
		
		
		CyTable nodeTable = myNet.getDefaultNodeTable();
		allColumns = new ArrayList<CyColumn>();
		String defaultNodeProperty = "Node Property";
		     
		nodeProperty = new JComboBox();
		operator = new JComboBox();
		addButton = new JButton("Add");
		goButton = new JButton("Go");
		panel1 = new JPanel();
		panel2 = new JPanel();
		
		nodeProperty.addItem(defaultNodeProperty);
		
		allColumns = nodeTable.getColumns();
		for(CyColumn currCol : allColumns)
		{
			nodeProperty.addItem(currCol.getName()+" : "+currCol.getType());
		}
		nodeProperty.setSelectedItem(defaultNodeProperty);
		
		nodeProperty.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				selectedNodeProperty = (String) nodeProperty.getSelectedItem();
				
				//based on the selected node property, the options in operator comboBox change.
				//String, Integer, Long, Double, Boolean, and Lists
				if(selectedNodeProperty.endsWith("Integer") || selectedNodeProperty.endsWith("Long") ||
						selectedNodeProperty.endsWith("Double")) //=, <, >, <=, >=, != 
				{
					operator.addItem("Operator");
					operator.addItem("=");
					operator.addItem("!=");
					operator.addItem("<");
					operator.addItem(">");
					operator.addItem("<=");
					operator.addItem(">=");
				}
				
				if(selectedNodeProperty.endsWith("Boolean"))
					operator.addItem("=");
				
				if(selectedNodeProperty.endsWith("String"))
				{
					operator.addItem("Operator");
					operator.addItem("Equals");
					operator.addItem("Does not equal");
				}
			}
        	}
				);
		
		operator.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				selectedOperator = (String) operator.getSelectedItem();
			}
        	}
				);
		//The text feild is to be filled now. Add button clicked
		
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				for(CyColumn currCol : allColumns)
				{
					if(selectedNodeProperty.startsWith(currCol.getName()))
					{
						CyColumn myCol = currCol;
						break;
					}
				}
				
			}
        	}
				);
		
		
		
		
			
		
	}
	
	
}
