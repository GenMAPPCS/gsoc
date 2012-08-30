package org.nrnb.pathexplorer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
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

	private JComboBox nodeAttr;
	private JComboBox operator;
	private JComboBox nodeAttrValue;
	private JButton goButton;
	private String selectedNodeAttr;
	private String selectedOperator;
	private CyColumn selectedColumn;
	private Object selectedNodeAttrValue;
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
		nodeAttr = new JComboBox();
		operator = new JComboBox();
		nodeAttrValue = new JComboBox();
		nodeAttrValue.setEditable(true);
		goButton = new JButton("Go");
		selectedNodeAttrValue = new Object();
		this.allNodeTableColumns = new ArrayList<CyColumn>();

		// get all columns with node properties
		CyTable nodeTable = myNet.getDefaultNodeTable();
		this.allNodeTableColumns = nodeTable.getColumns();

		// set values in the nodeProperty comboBoxes
		for (CyColumn currCol : allNodeTableColumns) {
			nodeAttr.addItem(currCol.getName());
		}

		// based on the selected node property, the options in operator comboBox
		// change. Use listener.
		// String, Integer, Long, Double, Boolean, and Lists
		nodeAttr.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				selectedNodeAttr = (String) nodeAttr.getSelectedItem();
				for (CyColumn currCol : allNodeTableColumns) {
					if (selectedNodeAttr.equals(currCol.getName())) {
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

					nodeAttrValue.removeAllItems();
					nodeAttrValue.setEditable(true);
				}

				else if (selectedColumn.getType().equals(Boolean.class)) {
					operator.removeAllItems();
					nodeAttrValue.removeAllItems();
					nodeAttrValue.setEditable(false);

					operator.addItem("=");
					nodeAttrValue.addItem("True");
					nodeAttrValue.addItem("False");
				}

				else if (selectedColumn.getType().equals(String.class)) {
					operator.removeAllItems();
					operator.addItem("Equals");
					operator.addItem("Does not equal");

					ArrayList<String> stringList = new ArrayList<String>();
					List<String> valuesList = new ArrayList<String>();
					nodeAttrValue.removeAllItems();
					nodeAttrValue.setEditable(false);

					// get all the values in Column
					valuesList = selectedColumn.getValues(String.class);

					// add these in comboBox, without repeat
					for (String myVal : valuesList) {
						if (!myVal.equals(null) && !stringList.contains(myVal)) {
							nodeAttrValue.addItem(myVal);
							stringList.add(myVal);
						}
					}
				}
				
				else {
					//List, which can again be String, Integer, Long, Double, Boolean
					operator.removeAllItems();
					operator.addItem("Contains");
					operator.addItem("Does not contain");
					
					nodeAttrValue.removeAllItems();
					
					if (selectedColumn.getListElementType().equals(Integer.class)
							|| selectedColumn.getListElementType().equals(Long.class)
							|| selectedColumn.getListElementType().equals(Double.class)) {
					
						nodeAttrValue.setEditable(true);
					}
					
					else if(selectedColumn.getListElementType().equals(Boolean.class)) {
						nodeAttrValue.addItem("True");
						nodeAttrValue.addItem("False");
						nodeAttrValue.setEditable(false);
					}
					
					else if(selectedColumn.getListElementType().equals(String.class)){
				
						ArrayList<String> stringList = new ArrayList<String>();
						nodeAttrValue.setEditable(false);
						@SuppressWarnings("rawtypes")
						List<List> valuesList = new ArrayList<List>();
						valuesList = selectedColumn.getValues(List.class);
						for (List<String> list: valuesList){
							for (String myVal: list){
								if(!myVal.equals(null) && !stringList.contains(myVal))
								{
									nodeAttrValue.addItem(myVal);
									stringList.add(myVal);
								}
							}
							
						}
					}
				}
				// resize dialog to fit new components
				validate();
				pack();
			}
		});

		goButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// take the values, set the apt isExcludedFromPaths
				selectedOperator = (String) operator.getSelectedItem();
				selectedNodeAttrValue = nodeAttrValue.getSelectedItem();
				ExclusionHandler myIFHandler = new ExclusionHandler(adapter);
				try {
					myIFHandler.handleIF(selectedColumn, selectedOperator,
							selectedNodeAttrValue, myNet);
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
            .addComponent(nodeAttr)
            .addComponent(operator)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(nodeAttrValue)
                .addComponent(goButton))
        );
        
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(nodeAttr)
                .addComponent(operator)
                .addComponent(nodeAttrValue))
			.addComponent(goButton)
        );
 
        setTitle("Exclude nodes with...");
        pack();
		setAlwaysOnTop(true);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		
		//trigger initial dialog
		nodeAttr.setSelectedItem(nodeAttr.getItemAt(0));

	}
}
