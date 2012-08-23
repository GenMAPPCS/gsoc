package org.nrnb.pathexplorer.tasks;

import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class ExcludeNodeViewTaskFactory extends AbstractNodeViewTaskFactory {

	
	public ExcludeNodeViewTaskFactory(){
	}
	
	public boolean isReady(View<CyNode> nodeView, CyNetworkView networkView) {
		//add condition that clicked node is not already excluded
		return true;
	}

	public TaskIterator createTaskIterator(View<CyNode> nodeView,
			CyNetworkView networkView) {
		return new TaskIterator(new ExcludeNodeViewTask(nodeView, networkView));
	}


}
