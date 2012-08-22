package org.nrnb.pathexplorer.view;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;

public class SelectPaths extends AbstractNetworkViewTaskFactory {

	CySwingAppAdapter adapter;

	public SelectPaths(CySwingAppAdapter adapter) {
		this.adapter = adapter;
	}

	public boolean isReady(CyNetworkView networkView) {
		return networkView != null;
	}

	public TaskIterator createTaskIterator(CyNetworkView netView) {
		return new TaskIterator(new SelectPathsTask(netView));
	}

}
