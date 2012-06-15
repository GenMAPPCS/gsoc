package org.nrnb.pathexplorer.flow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.nrnb.pathexplorer.view.MyEdgeViewTaskFactory;
import org.nrnb.pathexplorer.view.MyNodeViewTaskFactory;

public class SteadyFlowImplementer {
	
	private List<LinkedList<CyNode>> allPaths;
	private CyNetworkView netView;
	private CyNetwork net;
	
	public SteadyFlowImplementer(List<LinkedList<CyNode>> allPaths, CyNetworkView netView )
	{
		if(!allPaths.equals(null) && !netView.equals(null))
		{
			this.allPaths = allPaths;
			this.netView = netView;
			this.net = netView.getModel();
		}
		else
			System.out.println("null error");
	}
	
	public void implementSteadyFlow()
	{
		CyNode node1;
		List<CyEdge> edgeList = new ArrayList<CyEdge>();
		CyEdge edge;
		Iterator<CyNode> itr1;
		
		for(LinkedList<CyNode> myPath : allPaths)
		{
			itr1 = myPath.iterator();
			node1 = (CyNode) itr1.next();
	
			MyNodeViewTaskFactory nodeFactory;
			MyEdgeViewTaskFactory edgeFactory;
			
			nodeFactory = new MyNodeViewTaskFactory();
			nodeFactory.createTaskIterator(netView.getNodeView(node1), netView);
			
			while(itr1.hasNext())
			{
				edgeList = net.getConnectingEdgeList(node1, node1=itr1.next() , CyEdge.Type.OUTGOING);
				
			    if(!edgeList.isEmpty())
			    {
			    	System.out.println("edges: "+edgeList.size()+", first edge list= "+edgeList.get(0).toString());
			    	edge = edgeList.get(0);
			    
			    	nodeFactory = new MyNodeViewTaskFactory();
			    	nodeFactory.createTaskIterator(netView.getNodeView(node1), netView);
			    
			    	edgeFactory = new MyEdgeViewTaskFactory();
			    	edgeFactory.createTaskIterator(netView.getEdgeView(edge), netView);
			    }
			    else
			    	System.out.println("empty edge list error");
			}
		}	
	}
}
