package org.nrnb.pathexplorer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JMenuItem;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.swing.CyMenuItem;
import org.cytoscape.application.swing.CyNodeViewContextMenuFactory;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.nrnb.pathexplorer.PathExplorer;

public class ClearPathsNCM implements CyNodeViewContextMenuFactory{

	CySwingAppAdapter adapter;
	VisualMappingManager myVisMappingManager;
	
	public ClearPathsNCM(CySwingAppAdapter adapter){
		this.adapter = adapter;		
		myVisMappingManager = adapter.getVisualMappingManager();
	}
	
	public CyMenuItem createMenuItem(final CyNetworkView netView, final View<CyNode> nodeView)
	{
		JMenuItem menuItem = new JMenuItem("Clear Paths");
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
				CyNetworkManager myNetManager = adapter.getCyNetworkManager();
				Set<CyNetwork> allNets = new HashSet<CyNetwork>();
				List<CyNode> allNodes = new ArrayList<CyNode>();
				CyTable tempTable;
				CyRow row;
				allNets = myNetManager.getNetworkSet();
			  	System.out.println("Refreshing, setting IF to true and inPaths to false for all nodes of all networks");
			  	for(CyNetwork currNet : allNets)
			  	{
			  		tempTable = currNet.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
			  		allNodes = currNet.getNodeList();
			  		for(CyNode currNode : allNodes)
			  		{
			  			row = tempTable.getRow(currNode.getSUID());
				  		row.set("inclusionFactor", true);
				  		row.set("inPaths", false);
			  		}
			  		//refresh visual mapping
			  		myVisMappingManager.setVisualStyle(PathExplorer.baseVisualStyle, netView); //try commenting this line
			  		PathExplorer.baseVisualStyle.apply(netView);
			  		netView.updateView();
			  	}
			}
		});
		float gravity = 5.0f;
		CyMenuItem clearPaths = new CyMenuItem(menuItem, gravity);
		return clearPaths;
	}
}
