package org.nrnb.pathexplorer.tasks;

import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;

public class SelectPathsNetworkViewTaskFactory extends AbstractNetworkViewTaskFactory {

	public SelectPathsNetworkViewTaskFactory() {
	}

	public boolean isReady(CyNetworkView networkView) {
		//add condition that a path exists
		return true;
	}

	public TaskIterator createTaskIterator(CyNetworkView netView) {
		return new TaskIterator(new SelectPathsTask(netView));
	}

}
