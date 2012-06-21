package org.nrnb.pathexplorer.flow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;
import org.nrnb.pathexplorer.view.MyEdgeViewTaskFactory;
import org.nrnb.pathexplorer.view.MyNodeViewTaskFactory;

public class SteadyFlowImplementer {
	
	private ArrayList<LinkedList<CyNode>> allPaths = new ArrayList<LinkedList<CyNode>>();
	private CyNetworkView netView;
	private CyNetwork net;
	
	public SteadyFlowImplementer(ArrayList<LinkedList<CyNode>> allPaths, CyNetworkView netView )
	{
		if(!allPaths.equals(null) && !netView.equals(null))
		{
			this.allPaths = allPaths;
			this.netView = netView;
			this.net = netView.getModel();
		}
		else
			System.out.println("All paths and network view null error");
	}
	
	public void implementSteadyFlow(CySwingAppAdapter adapter)
	{
		CyNode node1, node2;
		ArrayList<CyEdge> edgeList = new ArrayList<CyEdge>();
		CyEdge edge;
		Iterator<CyNode> itr1;
		TaskIterator tItr, tempTaskItr;
		TaskMonitor tm = null;
		
		MyNodeViewTaskFactory nodeFactory;
		nodeFactory = new MyNodeViewTaskFactory(adapter);
		
		MyEdgeViewTaskFactory edgeFactory;
		edgeFactory = new MyEdgeViewTaskFactory(adapter);
			
		for(LinkedList<CyNode> myPath : allPaths)
		{
			itr1 = myPath.iterator();
			node1 = (CyNode) itr1.next();
			tItr = nodeFactory.createTaskIterator(netView.getNodeView(node1), netView);
			if(itr1.hasNext())
			{
				node2 = (CyNode) itr1.next();
				tItr.append(nodeFactory.createTaskIterator(netView.getNodeView(node2), netView));
			}
			
			while(itr1.hasNext())
			{
				edgeList = (ArrayList<CyEdge>)net.getConnectingEdgeList(node1, node1=itr1.next() , CyEdge.Type.OUTGOING);
				
			    if(!edgeList.isEmpty())
			    {
			    	System.out.println("edges: "+edgeList.size()+", first edge list= "+edgeList.get(0).toString());
			    	edge = edgeList.get(0);
			    
			    	tempTaskItr = nodeFactory.createTaskIterator(netView.getNodeView(node1), netView);
			    	tItr.append(tempTaskItr);
			    	tempTaskItr = edgeFactory.createTaskIterator(netView.getEdgeView(edge), netView);
			    	tItr.append(tempTaskItr);
			    }
			    else
			    	System.out.println("empty edge list error");
			}
			while(tItr.hasNext())
			{
				try {
					tItr.next().run(tm);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}	
	}
}
