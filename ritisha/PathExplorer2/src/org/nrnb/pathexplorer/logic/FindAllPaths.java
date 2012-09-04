package org.nrnb.pathexplorer.logic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.model.CyNetworkView;

public class FindAllPaths {
	private CyNetwork net;
	private CyNetworkView netView;
	private CyNode theNode;
	private CySwingAppAdapter adapter;
	private SteadyStateFlow mySteadyFlow;

	// Constructor
	public FindAllPaths(CyNetworkView netView, CyNode node,
			CySwingAppAdapter adapter) {
		if (!netView.equals(null) && !node.equals(null)) {
			this.netView = netView;
			this.net = netView.getModel();
			this.theNode = node;
			this.adapter = adapter;
			this.mySteadyFlow = new SteadyStateFlow(this.adapter, this.netView);

		} else
			System.out.println("Network and passed node Null error");

	}

	public void BFS(Set<CyNode> excluded, CyEdge.Type direction) {
		Set<CyNode> pathNodes = new HashSet<CyNode>();
		Set<CyEdge> pathEdges = new HashSet<CyEdge>();
		Stack<CyNode> pending = new Stack<CyNode>();

		pending.push(theNode);

		while (!pending.isEmpty()) {
			CyNode currNode = pending.pop();
			if (pathNodes.contains(currNode) || excluded.contains(currNode))
				continue;

			pending.addAll(net.getNeighborList(currNode, direction));
			pathEdges.addAll(net.getAdjacentEdgeList(currNode, direction));
			pathNodes.add(currNode);			
		}
		mySteadyFlow.steadyStateFlowImpl(pathNodes, pathEdges);
	}

	public void findAllPathsMethod(CyEdge.Type direction){
		Set<CyNode> excluded = new HashSet<CyNode>();
		List<CyNode> nodeList = CyTableUtil.getNodesInState(net, TableHandler.EXCLUDED_COL, true);
		if (nodeList != null)
			excluded.addAll(nodeList);
			
		BFS(excluded, direction);
		
	}
	
	
	// Method to find all paths. Taking the source node, finds all simple paths
	// between it and all other nodes.
//	public void allPathsMethod(String direction) {
//		ArrayList<CyNode> allNodes = (ArrayList<CyNode>) net.getNodeList();
//		// adding code for exclude nodes with...
//		CyRow row;
//		ArrayList<CyNode> nodeList = new ArrayList<CyNode>();
//		for (CyNode currNode : allNodes) {
//			// check the value of isExcludedFromPaths for that node, if true,
//			// remove that node
//			row = hiddenNodeTable.getRow(currNode.getSUID());
//			Boolean isExcluded = (Boolean) row.get(TableHandler.EXCLUDED_COL,
//					Boolean.class);
//			if (isExcluded)
//				nodeList.add(currNode);
//		}
//		allNodes.removeAll(nodeList);
//
//		LinkedList<CyNode> visited = new LinkedList<CyNode>();
//		for (CyNode eachNode : allNodes) {
//			if (eachNode.equals(theNode))
//				continue;
//			visited.clear();
//			if (direction.equals(FROM_HERE)) {
//				visited.add(theNode);
//				DFS(net, visited, eachNode);
//			} else if (direction.equals(TO_HERE)) {
//				visited.add(eachNode);
//				DFS(net, visited, theNode);
//			}
//		}
//	}
//
//	// Method to find simplePaths
//	private void DFS(CyNetwork net, LinkedList<CyNode> visited, CyNode destiNode) {
//		CyNode last = visited.getLast();
//		CyRow row;
//		ArrayList<CyNode> adjNodes = new ArrayList<CyNode>();
//		adjNodes = (ArrayList<CyNode>) net.getNeighborList(last,
//				CyEdge.Type.OUTGOING);
//		ArrayList<CyNode> nodeList = new ArrayList<CyNode>();
//		// adding code for exclude nodes with..
//		for (CyNode currNode : adjNodes) {
//			// check the value of isExcludedFromPaths for that node, if true,
//			// remove that node
//			row = hiddenNodeTable.getRow(currNode.getSUID());
//			Boolean isExcluded = (Boolean) row.get(TableHandler.EXCLUDED_COL,
//					Boolean.class);
//			if (isExcluded)
//				nodeList.add(currNode);
//		}
//		adjNodes.removeAll(nodeList);
//
//		for (CyNode currNode : adjNodes) {
//			if (currNode.equals(destiNode)) {
//				visited.addLast(currNode);
//				System.out.println("\n******Path: " + visited.toString());
//				mySteadyFlow.implementSteadyFlow(visited);
//				visited.removeLast();
//				break;
//			}
//		}
//
//		for (CyNode currNode : adjNodes) {
//			if (visited.contains(currNode) || currNode.equals(destiNode))
//				continue;
//			visited.addLast(currNode);
//			DFS(net, visited, destiNode);
//			visited.removeLast();
//		}
//	}
}