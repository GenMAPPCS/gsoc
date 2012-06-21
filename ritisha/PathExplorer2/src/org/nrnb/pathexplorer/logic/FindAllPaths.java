package org.nrnb.pathexplorer.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

public class FindAllPaths
{
	private CyNetwork net;
	private CyNode sourceNode;
	ArrayList<LinkedList<CyNode>> allPaths = new ArrayList<LinkedList<CyNode>>();
	ArrayList<LinkedList<CyNode>> simplePaths = new ArrayList<LinkedList<CyNode>>();
	
	//Constructor
	public FindAllPaths (CyNetwork net, CyNode sourceNode)
	{
		if(!net.equals(null) && !sourceNode.equals(null))
		{
			this.net=net;
			this.sourceNode=sourceNode;
		}
		else
			System.out.println("Network and Source node Null error");
		
	}
	
	//Method to find all paths. Taking the source node, finds all simple paths between it and all other nodes. Puts all these simplePaths in allPaths
	public ArrayList<LinkedList<CyNode>> allPathsMethod()
	{
		ArrayList<CyNode> allNodes = (ArrayList<CyNode>) net.getNodeList();
		LinkedList<CyNode> visited = new LinkedList<CyNode>();
		ArrayList<LinkedList<CyNode>> temp = new ArrayList<LinkedList<CyNode>>(); //keeps track of simple paths
		for(CyNode destiNode : allNodes)
		{
			visited.clear();
			simplePaths.clear();
			temp.clear();
			visited.add(sourceNode);
			temp = DFS(net, visited, destiNode);
			if(!temp.equals(null))
				allPaths.addAll(temp);	
		}
		System.out.println("allPaths: " + allPaths.toString());
		return allPaths;
	}
	
	//Method to find simplePaths
	private ArrayList<LinkedList<CyNode>> DFS(CyNetwork net, LinkedList<CyNode> visited, CyNode destiNode)
	{
		CyNode last = visited.getLast();
		ArrayList<CyNode> adjNodes = new ArrayList<CyNode>();
		adjNodes = (ArrayList<CyNode>)net.getNeighborList(last, CyEdge.Type.OUTGOING);
		
		for(CyNode currNode : adjNodes)
		{
			if(visited.contains(currNode))
				continue;
			
			if(currNode.equals(destiNode))
			{
				visited.addLast(currNode);
				simplePaths.add(visited);
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
		System.out.println("simplePath: " + simplePaths.toString());
		return simplePaths;
	}
	
}