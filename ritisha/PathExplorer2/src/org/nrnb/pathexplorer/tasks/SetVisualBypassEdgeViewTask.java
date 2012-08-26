package org.nrnb.pathexplorer.tasks;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.task.AbstractEdgeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.TaskMonitor;
import org.nrnb.pathexplorer.PathExplorer;

public class SetVisualBypassEdgeViewTask extends AbstractEdgeViewTask {
	private View<CyEdge> edgeView;
	private CyTable hiddenEdgeTable;

	public SetVisualBypassEdgeViewTask(View<CyEdge> edgeView, CyNetworkView netView) {
		super(edgeView, netView);
		this.edgeView = edgeView;
		this.hiddenEdgeTable = netView.getModel().getTable(CyEdge.class,
				CyNetwork.HIDDEN_ATTRS);
	}

	public void run(TaskMonitor tm) throws Exception {
		// set isInPath to true
		CyRow row;
		row = hiddenEdgeTable.getRow(edgeView.getModel().getSUID());
		row.set("isInPath", true);

		edgeView.setLockedValue(BasicVisualLexicon.EDGE_WIDTH, PathExplorer.EdgeWidthInPathsValue.doubleValue());
	}

}
