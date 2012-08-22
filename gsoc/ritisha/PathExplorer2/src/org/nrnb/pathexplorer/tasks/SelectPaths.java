package org.nrnb.pathexplorer.tasks;

import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;

public class SelectPaths extends AbstractNetworkViewTaskFactory {

	public SelectPaths() {
	}

	public boolean isReady(CyNetworkView networkView) {
		return networkView != null;
	}

	public TaskIterator createTaskIterator(CyNetworkView netView) {
		return new TaskIterator(new SelectPathsTask(netView));
	}

}
