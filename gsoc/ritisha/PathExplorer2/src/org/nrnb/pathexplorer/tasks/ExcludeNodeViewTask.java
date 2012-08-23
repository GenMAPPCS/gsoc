package org.nrnb.pathexplorer.tasks;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.task.AbstractNodeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskMonitor;

public class ExcludeNodeViewTask extends AbstractNodeViewTask {

	CyNetworkView netView;
	View<CyNode> nodeView;

	public ExcludeNodeViewTask(View<CyNode> nodeView, CyNetworkView netView) {
		super(nodeView, netView);
		this.netView = netView;
		this.nodeView = nodeView;
	}

	public void run(TaskMonitor tm) throws Exception {
		CyNetwork net;
		CyNode node;
		SetVisualBypassNodeViewTask removeBorder;
		net = netView.getModel();
		node = nodeView.getModel();
		CyTable hiddenNodeTable = net.getTable(CyNode.class,
				CyNetwork.HIDDEN_ATTRS);
		CyRow row = hiddenNodeTable.getRow(node.getSUID());
		row.set("isExcludedFromPaths", true);
		removeBorder = new SetVisualBypassNodeViewTask(nodeView, netView);
		removeBorder.removeBorderMethod();

		// TODO: Then rerun last FindPaths call or clear path if excluded node =
		// source or target node from last FindPaths call
	}

}
