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
	
	private LinkedList<CyNode> path = new LinkedList<CyNode>();
	private CyNetworkView netView;
	private CyNetwork net;
	
	public SteadyFlowImplementer(LinkedList<CyNode> path, CyNetworkView netView )
	{
		if(!path.equals(null) && !netView.equals(null))
		{
			this.path = path;
			this.netView = netView;
			this.net = netView.getModel();
		}
		else
			System.out.println("All paths and network view null error");
	}
	
	public void implementSteadyFlow(CySwingAppAdapter adapter)
	{
		CyNode node1;
		CyEdge edge;
		Iterator<CyNode> itr;
		TaskIterator tItr, tempTaskItr;
		TaskMonitor tm = null;
		ArrayList<CyEdge> edgeList = new ArrayList<CyEdge>();
		
		MyNodeViewTaskFactory nodeFactory;
		nodeFactory = new MyNodeViewTaskFactory(adapter);
		
		MyEdgeViewTaskFactory edgeFactory;
		edgeFactory = new MyEdgeViewTaskFactory(adapter);
			
		itr = path.iterator();
		node1 = (CyNode) itr.next();
		tItr = nodeFactory.createTaskIterator(netView.getNodeView(node1), netView);
			
		while(itr.hasNext())
		{
			edgeList = (ArrayList<CyEdge>)net.getConnectingEdgeList(node1, node1=itr.next() , CyEdge.Type.OUTGOING);
			
		    if(!edgeList.isEmpty())
		    {
		    	edge = edgeList.get(0);
		    
		    	tempTaskItr = nodeFactory.createTaskIterator(netView.getNodeView(node1), netView);
		    	tItr.append(tempTaskItr);
		    	tempTaskItr = edgeFactory.createTaskIterator(netView.getEdgeView(edge), netView);
		    	tItr.append(tempTaskItr);
		    }
		    else
		    	System.out.println("empty edge error");
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
