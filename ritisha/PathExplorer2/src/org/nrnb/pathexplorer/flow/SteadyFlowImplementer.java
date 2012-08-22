package org.nrnb.pathexplorer.flow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;
import org.nrnb.pathexplorer.view.MyEdgeViewTaskFactory;
import org.nrnb.pathexplorer.view.MyNodeViewTaskFactory;

public class SteadyFlowImplementer {
	
	//private LinkedList<CyNode> path = new LinkedList<CyNode>();
	private CyNetworkView netView;
	private CyNetwork net;
	private CySwingAppAdapter adapter;
	
	public SteadyFlowImplementer(CySwingAppAdapter adapter, CyNetworkView netView )
	{
		if(!netView.equals(null))
		{
			this.adapter = adapter;
			this.netView = netView;
			this.net = netView.getModel();
		}
		else
			System.out.println("All paths and network view null error");
	}
	
	public void implementSteadyFlow(LinkedList<CyNode> path)
	{
		CyNode node1;
		CyEdge edge;
		Iterator<CyNode> itr;
		TaskIterator tItr;
		TaskMonitor tm = null;
		ArrayList<CyEdge> edgeList;
		MyNodeViewTaskFactory nodeFactory;
		nodeFactory = new MyNodeViewTaskFactory();
		
		MyEdgeViewTaskFactory edgeFactory;
		edgeFactory = new MyEdgeViewTaskFactory();
			
		itr = path.iterator();
		node1 = (CyNode) itr.next();
		tItr = nodeFactory.createTaskIterator(netView.getNodeView(node1), netView);
			
		while(itr.hasNext())
		{
			edgeList = new ArrayList<CyEdge>(net.getConnectingEdgeList(node1, node1=itr.next(), CyEdge.Type.ANY));
			System.out.println("last node: " + node1.toString());
		    if(!edgeList.isEmpty())
		    {
		    	edge = edgeList.get(0);
		    
		    	tItr.append(nodeFactory.createTaskIterator(netView.getNodeView(node1), netView));
		    	tItr.append(edgeFactory.createTaskIterator(netView.getEdgeView(edge), netView));
		    }
		    else
		    	System.out.println("empty edge error");
		}
		do
		{
			try {
				tItr.next().run(tm);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(tItr.hasNext());
		
		//re-apply current visual style and refresh network view
		VisualMappingManager visualMappingManager = adapter.getVisualMappingManager();
        VisualStyle style = visualMappingManager.getCurrentVisualStyle();
        style.apply(netView);
		netView.updateView();
	}
}
