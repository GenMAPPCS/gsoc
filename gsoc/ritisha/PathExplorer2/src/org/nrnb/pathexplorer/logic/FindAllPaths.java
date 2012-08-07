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
import org.nrnb.pathexplorer.flow.SteadyFlowImplementer;

public class FindAllPaths
{
	private CyNetwork net;
	private CyNetworkView netView;
	private CyNode sourceNode;
	private CySwingAppAdapter adapter;
	private SteadyFlowImplementer mySteadyFlow;
	private CyTable myNodeTable;
	//ArrayList<LinkedList<CyNode>> allPaths = new ArrayList<LinkedList<CyNode>>();
	//ArrayList<LinkedList<CyNode>> simplePaths = new ArrayList<LinkedList<CyNode>>();
	
	//Constructor
	public FindAllPaths (CyNetworkView netView, CyNode sourceNode, CySwingAppAdapter adapter)
	{
		if(!netView.equals(null) && !sourceNode.equals(null))
		{
			this.netView = netView;
			this.net = netView.getModel();
			this.sourceNode = sourceNode;
			this.adapter = adapter;
			this.mySteadyFlow = new SteadyFlowImplementer(this.adapter, this.netView);
			myNodeTable = net.getTable(CyNode.class, "IFTable");
		}
		else
			System.out.println("Network and Source node Null error");
		
	}
	
	//Method to find all paths. Taking the source node, finds all simple paths between it and all other nodes.
	public void allPathsMethod()
	{
		ArrayList<CyNode> allNodes = (ArrayList<CyNode>) net.getNodeList();
		//adding code for exclude nodes with...
		CyRow row;
		for(CyNode currNode : allNodes)
		{
			//check what is the inclusionFactor for that node, if false, remove that node
			row = myNodeTable.getRow(currNode);
			if(!(boolean)row.getRaw("inclusionFactor"))
				allNodes.remove(currNode);
		}
		
		LinkedList<CyNode> visited = new LinkedList<CyNode>();
		for(CyNode destiNode : allNodes)
		{
			if(destiNode.equals(sourceNode))
				continue;
			visited.clear();
			//simplePaths.clear();
			visited.add(sourceNode);
			DFS(net, visited, destiNode);
			//System.out.println("simplePath1 size: " + simplePaths.get(0).size());
			//if(!simplePaths.equals(null))
				//allPaths.addAll(simplePaths);	
		}
		/*System.out.println("allPaths: " + allPaths.size());
		System.out.println("first path: " + allPaths.get(0).size());
		return allPaths;*/
	}
	
	//Method to find simplePaths
	private void DFS(CyNetwork net, LinkedList<CyNode> visited, CyNode destiNode)
	{
		CyNode last = visited.getLast();
		CyRow row;
		ArrayList<CyNode> adjNodes = new ArrayList<CyNode>();
		adjNodes = (ArrayList<CyNode>)net.getNeighborList(last, CyEdge.Type.OUTGOING);
		
		//adding code for exclude nodes with..
		for(CyNode currNode : adjNodes)
		{
			//check what is the inclusionFactor for that node, if false, remove that node
			row = myNodeTable.getRow(currNode);
			if(!(boolean)row.getRaw("inclusionFactor"))
				adjNodes.remove(currNode);
		}
	
		for(CyNode currNode : adjNodes)
		{
			if(currNode.equals(destiNode))
			{
				visited.addLast(currNode);
				//System.out.println("Adding a path to simplePaths with size " + visited.size());
				//simplePaths.add(visited);
				System.out.println("\n******Path: " + visited.toString());
				mySteadyFlow.implementSteadyFlow(visited);
				visited.removeLast();
				break;
			}
		}
		
		for(CyNode currNode : adjNodes)
		{
			if(visited.contains(currNode) || currNode.equals(destiNode))
				continue;
			visited.addLast(currNode);
			DFS(net, visited, destiNode);
			visited.removeLast();
		}
	}
	
}