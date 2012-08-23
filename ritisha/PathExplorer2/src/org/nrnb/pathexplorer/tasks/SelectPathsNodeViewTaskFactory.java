package org.nrnb.pathexplorer.tasks;

import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class SelectPathsNodeViewTaskFactory extends AbstractNodeViewTaskFactory {

	public SelectPathsNodeViewTaskFactory() {
	}

	public boolean isReady(View<CyNode> nodeView, CyNetworkView networkView) {
		//add condition that clicked node is in a path
		return true;
	}

	public TaskIterator createTaskIterator(View<CyNode> nodeView,
			CyNetworkView networkView) {
		return new TaskIterator(new SelectPathsTask(networkView));
	}

}
