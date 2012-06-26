package org.nrnb.pathexplorer.view;

import org.cytoscape.model.CyEdge;
import org.cytoscape.task.AbstractEdgeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.work.TaskIterator;

public class MyEdgeViewTaskFactory extends AbstractEdgeViewTaskFactory{
	
	private VisualMappingManager visualMappingManager;
	public MyEdgeViewTaskFactory(VisualMappingManager visualMappingManager)
	{
		this.visualMappingManager = visualMappingManager;	
	}
	
	public TaskIterator createTaskIterator(View<CyEdge> edgeView, CyNetworkView netView) {
		return new TaskIterator(new MyEdgeViewTask(edgeView, netView, visualMappingManager));
	} 

}
