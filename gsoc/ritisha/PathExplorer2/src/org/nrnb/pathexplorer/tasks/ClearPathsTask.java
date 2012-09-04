package org.nrnb.pathexplorer.tasks;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.TaskMonitor;
import org.nrnb.pathexplorer.logic.TableHandler;

public class ClearPathsTask extends AbstractNetworkViewTask {

	private CyNetworkView netView;
	private CySwingAppAdapter adapter;

	public ClearPathsTask(CyNetworkView netView, CySwingAppAdapter adapter) {
		super(netView);
		this.adapter = adapter;
		this.netView = netView;
	}

	public void run(TaskMonitor tm) throws Exception {

		CyNetwork currNet = netView.getModel();
		List<CyNode> allNodes = new ArrayList<CyNode>();
		List<CyEdge> allEdges = new ArrayList<CyEdge>();
		CyRow row, row1;
		//hiddenNodeTable = currNet.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
		allNodes = currNet.getNodeList();
		for (CyNode currNode : allNodes) {
			row = TableHandler.hiddenNodeTable.getRow(currNode.getSUID());
			Boolean isNodeInPath = (Boolean)row.get(TableHandler.IN_PATH_COL, Boolean.class);
			if (isNodeInPath){
			row.set(TableHandler.IN_PATH_COL, false);
			row1 = currNet.getRow(currNode);
			row1.set(CyNetwork.SELECTED, false);
				// clear node override
				netView.getNodeView(currNode).clearValueLock(
					BasicVisualLexicon.NODE_BORDER_WIDTH);		
			}
		}
		//hiddenEdgeTable = currNet.getTable(CyEdge.class, CyNetwork.HIDDEN_ATTRS);
		allEdges = currNet.getEdgeList();
		for (CyEdge currEdge : allEdges) {
			row = TableHandler.hiddenEdgeTable.getRow(currEdge.getSUID());
			Boolean isEdgeInPath = (Boolean)row.get("isInPath", Boolean.class);
			if (isEdgeInPath){
			row.set(TableHandler.IN_PATH_COL, false);
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
