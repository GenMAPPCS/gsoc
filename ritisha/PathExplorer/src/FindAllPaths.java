

import java.util.LinkedList;
import java.util.List;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

public class FindAllPaths
{
	private CyNetwork net;
	private CyNode sourceNode;
	List<LinkedList<CyNode>> allPaths;
	
	//Constructor
	public FindAllPaths (CyNetwork net, CyNode sourceNode)
	{
		this.net=net;
		this.sourceNode=sourceNode;
	}
	
	//Method to find all paths. Taking the source node, finds all simple paths between it and all other nodes. Puts all these simplePaths in allPaths
	public List<LinkedList<CyNode>> allPathsMethod()
	{
		List<CyNode> allNodes = net.getNodeList();
		LinkedList<CyNode> visited = new LinkedList<CyNode>();
		
		for(CyNode destiNode : allNodes)
		{
			visited.clear();
			visited.add(sourceNode);
			allPaths.addAll(DFS(net, visited, destiNode));
		}
		return allPaths;
	}
	
	//Method to find simplePaths
	private List<LinkedList<CyNode>> DFS(CyNetwork net, LinkedList<CyNode> visited, CyNode destiNode)
	{
		CyNode last = visited.getLast();
		List<CyNode> adjNodes = net.getNeighborList(last, CyEdge.Type.OUTGOING);
		LinkedList<CyNode> tempPath = new LinkedList<CyNode>();
		List<LinkedList<CyNode>> simplePaths = null;
		
		for(CyNode currNode : adjNodes)
		{
			if(visited.contains(currNode))
				continue;
			
			if(currNode.equals(destiNode))
			{
				visited.addLast(currNode);
				tempPath.addAll(visited);
				simplePaths.add(tempPath);
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
		
		return simplePaths;
	}
	
}