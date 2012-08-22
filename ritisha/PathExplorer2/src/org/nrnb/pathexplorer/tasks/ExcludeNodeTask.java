package org.nrnb.pathexplorer.tasks;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskMonitor;
import org.nrnb.pathexplorer.view.MyNodeViewTask;

public class ExcludeNodeTask extends AbstractNetworkViewTask{

	CyNetworkView netView;
	View<CyNode> nodeView;
	
	public ExcludeNodeTask(View<CyNode> nodeView, CyNetworkView netView)
	{
		super(netView);	
		this.netView = netView;
		this.nodeView = nodeView;
	}
	
	public void run(TaskMonitor tm) throws Exception {
		CyNetwork net;
		CyNode node;
		MyNodeViewTask removeBorder;
		net = netView.getModel();
		node = nodeView.getModel();
		CyTable hiddenNodeTable = net.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
		CyRow row = hiddenNodeTable.getRow(node.getSUID());
		row.set("isExcludedFromPaths", true);
		removeBorder = new MyNodeViewTask(nodeView, netView);
		removeBorder.removeBorderMethod();
	}

}
