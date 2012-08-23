package org.nrnb.pathexplorer.tasks;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class FindPathsNodeViewTaskFactory extends AbstractNodeViewTaskFactory {

	CySwingAppAdapter adapter;
	String direction;
	
	public FindPathsNodeViewTaskFactory(CySwingAppAdapter adapter, String direction){
		this.adapter = adapter;
		this.direction = direction;
	}
	
	public boolean isReady(View<CyNode> nodeView, CyNetworkView networkView) {
		//add condition that clicked node is not excluded
		return true;
	}

	public TaskIterator createTaskIterator(View<CyNode> nodeView,
			CyNetworkView networkView) {
		return new TaskIterator(new FindPathsNodeViewTask(nodeView, networkView, adapter, direction));
	}


}
