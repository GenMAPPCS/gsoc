package org.nrnb.pathexplorer.logic;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.events.NetworkAddedEvent;
import org.cytoscape.model.events.NetworkAddedListener;

public class TableHandler implements NetworkAddedListener{
	
	CyNetworkTableManager myNetTableManager;
  	public static CyTable hiddenNodeTable;
  	public static CyTable hiddenEdgeTable;
	
	public TableHandler()
	{
		super();
	}
	
	public void handleEvent(NetworkAddedEvent e)
	{
		System.out.println("Got new net, adding columns to hidden table");
		createColumns(e.getNetwork());

	}
	
	public static void createColumns(CyNetwork currNet){
  		hiddenNodeTable = currNet.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
  		hiddenNodeTable.createColumn("isExcludedFromPaths", Boolean.class, true, Boolean.FALSE);
  		hiddenNodeTable.createColumn("isInPath", Boolean.class, true, Boolean.FALSE);
  		hiddenEdgeTable = currNet.getTable(CyEdge.class, CyNetwork.HIDDEN_ATTRS);
  		hiddenEdgeTable.createColumn("isInPath", Boolean.class, true, Boolean.FALSE);
	}

}
