package org.nrnb.pathexplorer.tasks;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.task.AbstractNodeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.TaskMonitor;
import org.nrnb.pathexplorer.PathExplorer;

public class SetVisualBypassNodeViewTask extends AbstractNodeViewTask {
	private View<CyNode> nodeView;
	private CyNetworkView netView;
	private CyTable hiddenNodeTable;

	public SetVisualBypassNodeViewTask(View<CyNode> nodeView, CyNetworkView netView) {
		super(nodeView, netView);
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

		nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH, PathExplorer.nodeBorderWidthInPaths.doubleValue());
	}

	public void removeBorderMethod() {

		nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH, 0.0);

		netView.updateView();
	}

}
