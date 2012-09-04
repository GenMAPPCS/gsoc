package org.nrnb.pathexplorer.logic;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.events.NetworkAddedEvent;
import org.cytoscape.model.events.NetworkAddedListener;

public class TableHandler implements NetworkAddedListener {

	CyNetworkTableManager myNetTableManager;
	public static CyTable defaultNodeTable;
	public static CyTable hiddenNodeTable;
	public static CyTable hiddenEdgeTable;
	public final static String EXCLUDED_COL = "isExcludedFromPaths";
	public final static String IN_PATH_COL = "isInPath";

	public TableHandler() {
		super();
	}

	public void handleEvent(NetworkAddedEvent e) {
		createColumns(e.getNetwork());

	}

	public static void createColumns(CyNetwork currNet) {
		defaultNodeTable = currNet.getDefaultNodeTable();
		//Note: has to be in DEFAULT_ATTRS for getNodesInState() method to be used
		if (defaultNodeTable.getColumn(EXCLUDED_COL) == null)
			defaultNodeTable.createColumn(EXCLUDED_COL, Boolean.class, true,
					Boolean.FALSE);
		
		hiddenNodeTable = currNet
				.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
		if (hiddenNodeTable.getColumn(IN_PATH_COL) == null)
			hiddenNodeTable.createColumn(IN_PATH_COL, Boolean.class, true,
					Boolean.FALSE);
		
		hiddenEdgeTable = currNet
				.getTable(CyEdge.class, CyNetwork.HIDDEN_ATTRS);
		if (hiddenEdgeTable.getColumn(IN_PATH_COL) == null)
			hiddenEdgeTable.createColumn(IN_PATH_COL, Boolean.class, true,
					Boolean.FALSE);
	}

}
