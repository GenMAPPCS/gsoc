package org.nrnb.pathexplorer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyTable;
import org.nrnb.pathexplorer.logic.ExclusionHandler;

@SuppressWarnings("serial")
public class ExcludeNodesDataInputDialog extends JDialog {

	private JComboBox nodeProperty;
	private JComboBox operator;
	private JComboBox nodePropertyValue;
	private JButton goButton;
	private String selectedNodeProperty;
	private String selectedOperator;
	private CyColumn selectedColumn;
	private Object selectedNodePropertyVal;
	private Collection<CyColumn> allNodeTableColumns;
	private CySwingAppAdapter adapter;
	private CyNetwork myNet;

	public ExcludeNodesDataInputDialog(CyNetwork myNetwork,
			CySwingAppAdapter adapt) {
		// Initialize all variables except nodePropertyValues
		super();
		setTitle("Exclude Nodes with..");
		adapter = adapt;
		myNet = myNetwork;
		nodeProperty = new JComboBox();
		operator = new JComboBox();
		nodePropertyValue = new JComboBox();
		nodePropertyValue.setEditable(true);
		goButton = new JButton("Go");
		selectedNodePropertyVal = new Object();
		this.allNodeTableColumns = new ArrayList<CyColumn>();

		// get all columns with node properties
		CyTable nodeTable = myNet.getDefaultNodeTable();
		this.allNodeTableColumns = nodeTable.getColumns();

		// set values in the nodeProperty comboBoxes
		for (CyColumn currCol : allNodeTableColumns) {
			nodeProperty.addItem(currCol.getName());
		}

		// based on the selected node property, the options in operator comboBox
		// change. Use listener.
		// String, Integer, Long, Double, Boolean, and Lists
		nodeProperty.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				selectedNodeProperty = (String) nodeProperty.getSelectedItem();
				for (CyColumn currCol : allNodeTableColumns) {
					if (selectedNodeProperty.equals(currCol.getName())) {
						selectedColumn = currCol;
					}
				}

				if (selectedColumn.getType().equals(Integer.class)
						|| selectedColumn.getType().equals(Long.class)
						|| selectedColumn.getType().equals(Double.class))
				// =, <, >, <=, >=, !=
				{
					operator.removeAllItems();
					operator.addItem("=");
					operator.addItem("!=");
					operator.addItem("<");
					operator.addItem(">");
					operator.addItem("<=");
					operator.addItem(">=");

					nodePropertyValue.removeAll();
					nodePropertyValue.setEditable(true);
				}

				else if (selectedColumn.getType().equals(Boolean.class)) {
					operator.removeAllItems();
					nodePropertyValue.removeAllItems();
					nodePropertyValue.setEditable(false);

					operator.addItem("=");
					nodePropertyValue.addItem("True");
					nodePropertyValue.addItem("False");
				}

				else if (selectedColumn.getType().equals(String.class)) {
					operator.removeAllItems();
					operator.addItem("Equals");
					operator.addItem("Does not equal");

					ArrayList<String> stringList = new ArrayList<String>();
					List<String> valuesList = new ArrayList<String>();
					nodePropertyValue.removeAllItems();
					nodePropertyValue.setEditable(false);

					// get all the values in Column
					valuesList = selectedColumn.getValues(String.class);

					// add these in comboBox, without repeat
					for (String myVal : valuesList) {
						if (!myVal.equals(null) && !stringList.contains(myVal)) {
							nodePropertyValue.addItem(myVal);
							stringList.add(myVal);
						}
					}
				}
				
				else {
					//List, which can again be String, Integer, Long, Double, Boolean
					operator.removeAllItems();
					operator.addItem("Contains");
					operator.addItem("Does not contain");
					
					nodePropertyValue.removeAllItems();
					
					if (selectedColumn.getListElementType().equals(Integer.class)
							|| selectedColumn.getListElementType().equals(Long.class)
							|| selectedColumn.getListElementType().equals(Double.class)) {
					
						nodePropertyValue.setEditable(true);
					}
					
					else if(selectedColumn.getListElementType().equals(Boolean.class)) {
						nodePropertyValue.addItem("True");
						nodePropertyValue.addItem("False");
						nodePropertyValue.setEditable(false);
					}
					
					else if(selectedColumn.getListElementType().equals(String.class)){
				
						ArrayList<String> stringList = new ArrayList<String>();
						List<String> valuesList = new ArrayList<String>();
						nodePropertyValue.setEditable(false);
						@SuppressWarnings("rawtypes")
						Iterator<List> itr = selectedColumn.getValues(List.class).iterator();
						
						while(itr.hasNext())
						{
							valuesList = itr.next();
							for(String myVal : valuesList)
							{
								if(!myVal.equals(null) && !stringList.contains(myVal))
								{
									nodePropertyValue.addItem(myVal);
									stringList.add(myVal);
								}
							}
						}
					}
				}
			}
		});

		goButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// take the values, set the apt isExcludedFromPaths
				selectedOperator = (String) operator.getSelectedItem();
				selectedNodePropertyVal = nodePropertyValue.getSelectedItem();
				ExclusionHandler myIFHandler = new ExclusionHandler(adapter);
				try {
					myIFHandler.handleIF(selectedColumn, selectedOperator,
							selectedNodePropertyVal, myNet);
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
				
				dispose();
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
 
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addComponent(nodeProperty)
            .addComponent(operator)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(nodePropertyValue)
                .addComponent(goButton))
        );
        
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(nodeProperty)
                .addComponent(operator)
                .addComponent(nodePropertyValue))
			.addComponent(goButton)
        );
 
        setTitle("Exclude Nodes with..");
        pack();
		setAlwaysOnTop(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
