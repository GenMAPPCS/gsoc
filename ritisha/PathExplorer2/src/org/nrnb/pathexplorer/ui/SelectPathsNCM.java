package org.nrnb.pathexplorer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.swing.CyMenuItem;
import org.cytoscape.application.swing.CyNodeViewContextMenuFactory;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;

public class SelectPathsNCM implements CyNodeViewContextMenuFactory{

	CySwingAppAdapter adapter;
	
	public SelectPathsNCM(CySwingAppAdapter adapter){
		this.adapter = adapter;		
	}
	
	public CyMenuItem createMenuItem(final CyNetworkView netView, final View<CyNode> nodeView)
	{
		JMenuItem menuItem = new JMenuItem("Select Paths");
		CyNetwork net;
		CyRow row;
		net = netView.getModel();
		CyTable tempTable = net.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
		row = tempTable.getRow(nodeView.getModel().getSUID());
		Object temp = new Object(); 
		temp = (Boolean)row.getRaw("inPaths");
		Boolean myBool = new Boolean(false);
		if(temp.equals(myBool))
			menuItem.setEnabled(false);
		
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				CyNetwork net;
				CyRow row;
				net = netView.getModel();
				CyTable tempTable = net.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
				List<CyNode> allNodes = new ArrayList<CyNode>();
				allNodes = net.getNodeList();
				ArrayList<CyNode> toSelect = new ArrayList<CyNode>();
				for(CyNode currNode : allNodes)
				{
					//if inPaths = true, put it in toSelect
					row = tempTable.getRow(currNode.getSUID());
					Object temp = new Object(); 
					temp = (Boolean)row.getRaw("inPaths");
					Boolean myBool = new Boolean(true);
					if(temp.equals(myBool))
						toSelect.add(currNode);
				}
				
				System.out.println("Selecting nodes: "+ toSelect.toString());
				for(CyNode currNode : toSelect)
				{
					row = net.getRow(currNode);
					row.set(CyNetwork.SELECTED, true);
				}
			}
		});
		float gravity = 4.0f;
		CyMenuItem selectPaths = new CyMenuItem(menuItem, gravity);
		return selectPaths;
	}
}