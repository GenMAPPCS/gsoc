package org.nrnb.pathexplorer.view;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;

public class SelectPathsTask extends AbstractNetworkViewTask{

	CyNetworkView netView;
	
	public SelectPathsTask(CyNetworkView netView)
	{
		super(netView);	
		this.netView = netView;
	}
	
	public void run(TaskMonitor tm) throws Exception {
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

}
