package org.nrnb.pathexplorer.logic;

import java.util.ArrayList;
import java.util.LinkedList;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.CyNetworkView;

public class FindAllPaths {
	private CyNetwork net;
	private CyNetworkView netView;
	private CyNode theNode;
	private CySwingAppAdapter adapter;
	private SteadyStateFlow mySteadyFlow;
	private CyTable hiddenNodeTable;
	
	public static final String TO_HERE = "to";
	public static final String FROM_HERE = "from";
	

	// Constructor
	public FindAllPaths(CyNetworkView netView, CyNode node,
			CySwingAppAdapter adapter) {
		if (!netView.equals(null) && !node.equals(null)) {
			this.netView = netView;
			this.net = netView.getModel();
			this.theNode = node;
			this.adapter = adapter;
			this.mySteadyFlow = new SteadyStateFlow(this.adapter,
					this.netView);
			hiddenNodeTable = net
					.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
		} else
			System.out.println("Network and passed node Null error");

	}

	// Method to find all paths. Taking the source node, finds all simple paths
	// between it and all other nodes.
	public void allPathsMethod(String direction) {
		ArrayList<CyNode> allNodes = (ArrayList<CyNode>) net.getNodeList();
		// adding code for exclude nodes with...
		CyRow row;
		ArrayList<CyNode> nodeList = new ArrayList<CyNode>();
		for (CyNode currNode : allNodes) {
			// check the value of isExcludedFromPaths for that node, if true,
			// remove that node
			row = hiddenNodeTable.getRow(currNode.getSUID());
			Boolean isExcluded = (Boolean) row.get("isExcludedFromPaths",
					Boolean.class);
			if (isExcluded)
				nodeList.add(currNode);
		}
		allNodes.removeAll(nodeList);

		LinkedList<CyNode> visited = new LinkedList<CyNode>();
		for (CyNode eachNode : allNodes) {
			if (eachNode.equals(theNode))
				continue;
			visited.clear();
			if (direction.equals(FROM_HERE)) {
				visited.add(theNode);
				DFS(net, visited, eachNode);
			} else if (direction.equals(TO_HERE)) {
				visited.add(eachNode);
				DFS(net, visited, theNode);
			}
		}
	}

	// Method to find simplePaths
	private void DFS(CyNetwork net, LinkedList<CyNode> visited, CyNode destiNode) {
		CyNode last = visited.getLast();
		CyRow row;
		ArrayList<CyNode> adjNodes = new ArrayList<CyNode>();
		adjNodes = (ArrayList<CyNode>) net.getNeighborList(last,
				CyEdge.Type.OUTGOING);
		ArrayList<CyNode> nodeList = new ArrayList<CyNode>();
		// adding code for exclude nodes with..
		for (CyNode currNode : adjNodes) {
			// check the value of isExcludedFromPaths for that node, if true,
			// remove that node
			row = hiddenNodeTable.getRow(currNode.getSUID());
			Boolean isExcluded = (Boolean) row.get("isExcludedFromPaths",
					Boolean.class);
			if (isExcluded)
				nodeList.add(currNode);
		}
		adjNodes.removeAll(nodeList);

		for (CyNode currNode : adjNodes) {
			if (currNode.equals(destiNode)) {
				visited.addLast(currNode);
				System.out.println("\n******Path: " + visited.toString());
				mySteadyFlow.implementSteadyFlow(visited);
				visited.removeLast();
				break;
			}
		}

		for (CyNode currNode : adjNodes) {
			if (visited.contains(currNode) || currNode.equals(destiNode))
				continue;
			visited.addLast(currNode);
			DFS(net, visited, destiNode);
			visited.removeLast();
		}
	}
}