package org.nrnb.pathexplorer.logic;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableFactory;
import org.cytoscape.model.events.NetworkAddedEvent;
import org.cytoscape.model.events.NetworkAddedListener;

public class MyNetAddedListener implements NetworkAddedListener{
	
	CyTableFactory myTableFactory;
	CyNetworkTableManager myNetTableManager;
	
	public MyNetAddedListener(CyTableFactory myTableFactory)
	{
		super();
		this.myTableFactory = myTableFactory;
	}
	
	public void handleEvent(NetworkAddedEvent e)
	{
		CyNetwork myNet = e.getNetwork();
		List<CyNode> allNodes = new ArrayList<CyNode>();
		CyRow row;
		CyTable tempTable = myTableFactory.createTable("IFTable", "Node", CyNode.class, false, true);
		tempTable.createColumn("inclusionFactor", boolean.class, true, true);
  		myNetTableManager.setTable(myNet, CyNode.class, "IFTable", tempTable);
  		
  		allNodes = myNet.getNodeList();
  		for(CyNode currNode : allNodes)
  		{
  			row = tempTable.getRow(currNode);
	  		row.set("inclusionFactor", true);
  		}
		
	}

}
