package org.nrnb.pathexplorer.view;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class MyEdgeViewTask extends AbstractTask {
	private View<CyEdge> edgeView;
	private CyTable hiddenEdgeTable;

	public MyEdgeViewTask(View<CyEdge> edgeView, CyNetworkView netView) {
		this.edgeView = edgeView;
		this.hiddenEdgeTable = netView.getModel().getTable(CyEdge.class,
				CyNetwork.HIDDEN_ATTRS);
	}

	public void run(TaskMonitor tm) throws Exception {
		// set isInPath to true
		CyRow row;
		row = hiddenEdgeTable.getRow(edgeView.getModel().getSUID());
		row.set("isInPath", true);

		edgeView.setLockedValue(BasicVisualLexicon.EDGE_WIDTH, 12.0);
	}

}
