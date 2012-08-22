package org.nrnb.pathexplorer.view;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class MyNodeViewTask extends AbstractTask {
	private View<CyNode> nodeView;
	private CyNetworkView netView;
	private CyTable hiddenNodeTable;

	public MyNodeViewTask(View<CyNode> nodeView, CyNetworkView netView) {
		this.nodeView = nodeView;
		this.netView = netView;
		this.hiddenNodeTable = netView.getModel().getTable(CyNode.class,
				CyNetwork.HIDDEN_ATTRS);
	}

	public void run(TaskMonitor tm) throws Exception {
		// set isInPath to true
		CyRow row;
		row = hiddenNodeTable.getRow(nodeView.getModel().getSUID());
		row.set("isInPath", true);

		nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH, 20.0);
	}

	public void removeBorderMethod() {

		nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH, 0.0);

		netView.updateView();
	}

}
