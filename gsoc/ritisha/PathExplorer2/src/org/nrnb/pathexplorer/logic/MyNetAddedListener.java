package org.nrnb.pathexplorer.logic;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.events.NetworkAddedEvent;
import org.cytoscape.model.events.NetworkAddedListener;

public class MyNetAddedListener implements NetworkAddedListener{
	
	CyNetworkTableManager myNetTableManager;
	
	public MyNetAddedListener()
	{
		super();
	}
	
	public void handleEvent(NetworkAddedEvent e)
	{
		CyNetwork myNet = e.getNetwork();
		List<CyNode> allNodes = new ArrayList<CyNode>();
		CyRow row;
		CyTable tempTable = myNet.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
		tempTable.createColumn("inclusionFactor", Boolean.class, true, true);
  		
  		allNodes = myNet.getNodeList();
  		for(CyNode currNode : allNodes)
  		{
  			row = tempTable.getRow(currNode.getSUID());
	  		row.set("inclusionFactor", true);
  		}
		
	}

}
