package org.nrnb.pathexplorer.view;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.TaskMonitor;

public class RefreshTask extends AbstractNetworkViewTask {

	private CyNetworkView netView;
	private CySwingAppAdapter adapter;

	public RefreshTask(CyNetworkView netView, CySwingAppAdapter adapter) {
		super(netView);
		this.adapter = adapter;
		this.netView = netView;
	}

	public void run(TaskMonitor tm) throws Exception {

		CyNetwork currNet = netView.getModel();
		List<CyNode> allNodes = new ArrayList<CyNode>();
		List<CyEdge> allEdges = new ArrayList<CyEdge>();
		CyTable hiddenNodeTable;
		CyTable hiddenEdgeTable;
		CyRow row;
		System.out
				.println("Refresh Task: clearing all path nodes and edges of current network");
		hiddenNodeTable = currNet.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
		allNodes = currNet.getNodeList();
		for (CyNode currNode : allNodes) {
			row = hiddenNodeTable.getRow(currNode.getSUID());
			Boolean isNodeInPath = (Boolean)row.get("isInPath", Boolean.class);
			if (isNodeInPath){
			row.set("isInPath", false);
				// clear node override
				netView.getNodeView(currNode).clearValueLock(
					BasicVisualLexicon.NODE_BORDER_WIDTH);		
			}
		}
		hiddenEdgeTable = currNet.getTable(CyEdge.class, CyNetwork.HIDDEN_ATTRS);
		allEdges = currNet.getEdgeList();
		for (CyEdge currEdge : allEdges) {
			row = hiddenEdgeTable.getRow(currEdge.getSUID());
			Boolean isEdgeInPath = row.get("isInPath", Boolean.class);
			if (isEdgeInPath){
			row.set("isInPath", false);
				// clear edge override
				netView.getEdgeView(currEdge).clearValueLock(
					BasicVisualLexicon.EDGE_WIDTH);
			}
		}

		// re-apply current visual style and refresh network view
		VisualMappingManager visualMappingManager = adapter.getVisualMappingManager();
        VisualStyle style = visualMappingManager.getCurrentVisualStyle();
        style.apply(netView);
		netView.updateView();
	}
}
