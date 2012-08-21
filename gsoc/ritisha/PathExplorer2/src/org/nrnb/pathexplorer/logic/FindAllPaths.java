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
	private CyNode theNode;
	private CySwingAppAdapter adapter;
	private SteadyFlowImplementer mySteadyFlow;
	private CyTable myNodeTable;
	
	//Constructor
	public FindAllPaths (CyNetworkView netView, CyNode node, CySwingAppAdapter adapter)
	{
		if(!netView.equals(null) && !node.equals(null))
		{
			this.netView = netView;
			this.net = netView.getModel();
			this.theNode = node;
			this.adapter = adapter;
			this.mySteadyFlow = new SteadyFlowImplementer(this.adapter, this.netView);
			myNodeTable = net.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
		}
		else
			System.out.println("Network and passed node Null error");
		
	}
	
	//Method to find all paths. Taking the source node, finds all simple paths between it and all other nodes.
	public void allPathsMethod_source()
	{
		ArrayList<CyNode> allNodes = (ArrayList<CyNode>) net.getNodeList();
		//adding code for exclude nodes with...
		CyRow row;
		ArrayList<CyNode> tempList = new ArrayList<CyNode>();
		for(CyNode currNode : allNodes)
		{
			//check what is the inclusionFactor for that node, if false, remove that node
			row = myNodeTable.getRow(currNode.getSUID());
			Object temp = new Object(); 
			temp = (Boolean)row.getRaw("inclusionFactor");
			Boolean myBool = new Boolean(false);
			if(temp.equals(myBool))
				tempList.add(currNode);
		}
		allNodes.removeAll(tempList);
		
		LinkedList<CyNode> visited = new LinkedList<CyNode>();
		for(CyNode destiNode : allNodes)
		{
			if(destiNode.equals(theNode))
				continue;
			visited.clear();
			visited.add(theNode);
			DFS(net, visited, destiNode);
		}
	}
	
	//Method to find all paths. Taking the target node, finds all simple paths between all other nodes and it 
		public void allPathsMethod_target()
		{
			ArrayList<CyNode> allNodes = (ArrayList<CyNode>) net.getNodeList();
			//adding code for exclude nodes with...
			CyRow row;
			ArrayList<CyNode> tempList = new ArrayList<CyNode>();
			for(CyNode currNode : allNodes)
			{
				//check what is the inclusionFactor for that node, if false, remove that node
				row = myNodeTable.getRow(currNode.getSUID());
				Object temp = new Object(); 
				temp = (Boolean)row.getRaw("inclusionFactor");
				Boolean myBool = new Boolean(false);
				if(temp.equals(myBool))
					tempList.add(currNode);
			}
			allNodes.removeAll(tempList);
			
			LinkedList<CyNode> visited = new LinkedList<CyNode>();
			for(CyNode sourceNode : allNodes)
			{
				if(sourceNode.equals(theNode))
					continue;
				visited.clear();
				visited.add(sourceNode);
				DFS(net, visited, theNode);	
			}
		}
		
	//Method to find simplePaths
	private void DFS(CyNetwork net, LinkedList<CyNode> visited, CyNode destiNode)
	{
		CyNode last = visited.getLast();
		CyRow row;
		ArrayList<CyNode> adjNodes = new ArrayList<CyNode>();
		adjNodes = (ArrayList<CyNode>)net.getNeighborList(last, CyEdge.Type.OUTGOING);
		ArrayList<CyNode> tempList = new ArrayList<CyNode>(); 
		//adding code for exclude nodes with..
		for(CyNode currNode : adjNodes)
		{
			//check what is the inclusionFactor for that node, if false, remove that node
			row = myNodeTable.getRow(currNode.getSUID());
			Object temp = new Object(); 
			temp = (Boolean)row.getRaw("inclusionFactor");
			Boolean myBool = new Boolean(false);
			if(temp.equals(myBool))
				tempList.add(currNode);
		}
		adjNodes.removeAll(tempList);
	
		for(CyNode currNode : adjNodes)
		{
			if(currNode.equals(destiNode))
			{
				visited.addLast(currNode);
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