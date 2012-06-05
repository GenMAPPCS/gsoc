import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;

public class SteadyFlowImplementer {
	
	private List<LinkedList<CyNode>> allPaths;
	private CyNetworkView netView;
	private CyNetwork net;
	
	public SteadyFlowImplementer(List<LinkedList<CyNode>> allPaths, CyNetworkView netView )
	{
		this.allPaths = allPaths;
		this.netView = netView;
		this.net = netView.getModel();
	}
	
	public void implementSteadyFlow()
	{
		CyNode node1, node2;
		List<CyEdge> edgeList;
		CyEdge edge;
		ListIterator<CyNode> itr1, itr2;
		
		for(LinkedList<CyNode> myPath : allPaths)
		{
			itr1 = myPath.listIterator();
			node1 = (CyNode) itr1.next();
			itr2 = myPath.listIterator();
			MyNodeViewTaskFactory nodeFactory;
			MyEdgeViewTaskFactory edgeFactory;
			
			nodeFactory = new MyNodeViewTaskFactory();
			nodeFactory.createTaskIterator(netView.getNodeView(node1), netView);
			
			while(itr1.hasNext())
			{
				node1 = (CyNode) itr1.next();
				node2 = (CyNode) itr2.next();
				edgeList = net.getConnectingEdgeList(node2, node1, CyEdge.Type.OUTGOING);
			    edge = edgeList.get(0);
			    
			    nodeFactory = new MyNodeViewTaskFactory();
			    nodeFactory.createTaskIterator(netView.getNodeView(node1), netView);
			    
			    edgeFactory = new MyEdgeViewTaskFactory();
			    edgeFactory.createTaskIterator(netView.getEdgeView(edge), netView);
			    
			}
		}
		
	}
	
	

}
