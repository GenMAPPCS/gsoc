package org.nrnb.pathexplorer.view;

import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.work.TaskIterator;

public class MyNodeViewTaskFactory extends AbstractNodeViewTaskFactory{
	
	private VisualMappingManager visualMappingManager;
	
	public MyNodeViewTaskFactory (VisualMappingManager visualMappingManager){
		this.visualMappingManager = visualMappingManager;	
	}
	
	public TaskIterator createTaskIterator(View<CyNode> nodeView, CyNetworkView netView) {
		return new TaskIterator(new MyNodeViewTask(nodeView, netView, visualMappingManager));
	}
	

}
