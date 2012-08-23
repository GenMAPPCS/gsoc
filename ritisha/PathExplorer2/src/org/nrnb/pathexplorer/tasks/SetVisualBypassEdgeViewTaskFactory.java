package org.nrnb.pathexplorer.tasks;

import org.cytoscape.model.CyEdge;
import org.cytoscape.task.AbstractEdgeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class SetVisualBypassEdgeViewTaskFactory extends AbstractEdgeViewTaskFactory{
	
	public SetVisualBypassEdgeViewTaskFactory()
	{
	}
	
	public TaskIterator createTaskIterator(View<CyEdge> edgeView, CyNetworkView netView) {
		return new TaskIterator(new SetVisualBypassEdgeViewTask(edgeView, netView));
	} 

}
