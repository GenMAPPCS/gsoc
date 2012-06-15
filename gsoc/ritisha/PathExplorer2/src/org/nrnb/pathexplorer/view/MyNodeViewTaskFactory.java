package org.nrnb.pathexplorer.view;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class MyNodeViewTaskFactory extends AbstractNodeViewTaskFactory{
	
	private CySwingAppAdapter adapter;
	public MyNodeViewTaskFactory (CySwingAppAdapter adapter){
		this.adapter = adapter;
		
	}
	public TaskIterator createTaskIterator(View<CyNode> nodeView, CyNetworkView netView) {
		return new TaskIterator(new MyNodeViewTask(nodeView, netView, adapter));
	}
	

}
