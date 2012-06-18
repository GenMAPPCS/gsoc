package org.nrnb.pathexplorer.view;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyEdge;
import org.cytoscape.task.AbstractEdgeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class MyEdgeViewTaskFactory extends AbstractEdgeViewTaskFactory{
	
	private CySwingAppAdapter adapter;
	public MyEdgeViewTaskFactory(CySwingAppAdapter adapter)
	{
		this.adapter = adapter;	
	}
	
	public TaskIterator createTaskIterator(View<CyEdge> edgeView, CyNetworkView netView) {
		return new TaskIterator(new MyEdgeViewTask(edgeView, netView, adapter));
	} 

}
