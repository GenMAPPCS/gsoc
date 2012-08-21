package org.nrnb.pathexplorer.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;

public class RefreshTask extends AbstractNetworkViewTask{
	
	private CySwingAppAdapter adapter;
	
	public RefreshTask(CyNetworkView netView, CySwingAppAdapter adapter)
	{
		super(netView);	
		this.adapter = adapter;
	}
	
	public void run(TaskMonitor tm) throws Exception {
		
		CyNetworkManager myNetManager = adapter.getCyNetworkManager();
		Set<CyNetwork> allNets = new HashSet<CyNetwork>();
		List<CyNode> allNodes = new ArrayList<CyNode>();
		CyTable tempTable;
		CyRow row;
		allNets = myNetManager.getNetworkSet();
	  	System.out.println("Refreshin, setting IF to true for all nodes of all networks");
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
	  	}
	  	
	  //add code to refresh the visual mapping of all the nodes.
	}
}
