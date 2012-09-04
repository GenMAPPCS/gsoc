package org.nrnb.pathexplorer.logic;

import java.util.Set;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.nrnb.pathexplorer.PathExplorer;

/**
 * This class implements the highlighting of paths into or out from a selected
 * node as a static, steady-state flow.
 */
public class SteadyStateFlow {

	// private LinkedList<CyNode> path = new LinkedList<CyNode>();
	private CyNetworkView netView;
	//private CyNetwork net;
	private CySwingAppAdapter adapter;

	public SteadyStateFlow(CySwingAppAdapter adapter, CyNetworkView netView) {
		if (!netView.equals(null)) {
			this.adapter = adapter;
			this.netView = netView;
			//this.net = netView.getModel();
		} else
			System.out.println("All paths and network view null error");
	}

	public void steadyStateFlowImpl(Set<CyNode> pathNodes, Set<CyEdge> pathEdges) {
		for (CyNode n : pathNodes) {
			TableHandler.hiddenNodeTable.getRow(n.getSUID()).set(TableHandler.IN_PATH_COL, true);
			netView.getNodeView(n).setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH, PathExplorer.NodeBorderWidthInPathsValue.doubleValue());
		}
		for (CyEdge e : pathEdges){
			TableHandler.hiddenEdgeTable.getRow(e.getSUID()).set(TableHandler.IN_PATH_COL, true);
			netView.getEdgeView(e).setLockedValue(BasicVisualLexicon.EDGE_WIDTH, PathExplorer.EdgeWidthInPathsValue.doubleValue());
		}
		
		// re-apply current visual style and refresh network view
		VisualMappingManager visualMappingManager = adapter
				.getVisualMappingManager();
		VisualStyle style = visualMappingManager.getCurrentVisualStyle();
		style.apply(netView);
		netView.updateView();
	}

	/**
	 * Takes a LinkedList<CyNode> path and sets up the node and edge visual
	 * bypass task factories.
	 * 
	 * @param path
	 */
//	public void implementSteadyFlow(LinkedList<CyNode> path) {
//		CyNode node1;
//		Iterator<CyNode> itr;
//		TaskIterator tItr;
//		TaskMonitor tm = null;
//		ArrayList<CyEdge> edgeList;
//		SetVisualBypassNodeViewTaskFactory nodeFactory;
//		nodeFactory = new SetVisualBypassNodeViewTaskFactory();
//
//		SetVisualBypassEdgeViewTaskFactory edgeFactory;
//		edgeFactory = new SetVisualBypassEdgeViewTaskFactory();
//
//		itr = path.iterator();
//		node1 = (CyNode) itr.next();
//		tItr = nodeFactory.createTaskIterator(netView.getNodeView(node1),
//				netView);
//
//		while (itr.hasNext()) {
//			edgeList = new ArrayList<CyEdge>(net.getConnectingEdgeList(node1,
//					node1 = itr.next(), CyEdge.Type.ANY));
//			if (!edgeList.isEmpty()) {
//				for (CyEdge edge : edgeList) {
//					tItr.append(edgeFactory.createTaskIterator(
//							netView.getEdgeView(edge), netView));
//				}
//				tItr.append(nodeFactory.createTaskIterator(
//						netView.getNodeView(node1), netView));
//			} else
//				System.out.println("empty edge error");
//		}
//		do {
//			try {
//				tItr.next().run(tm);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} while (tItr.hasNext());
//
//		// re-apply current visual style and refresh network view
//		VisualMappingManager visualMappingManager = adapter
//				.getVisualMappingManager();
//		VisualStyle style = visualMappingManager.getCurrentVisualStyle();
//		style.apply(netView);
//		netView.updateView();
//	}
}
