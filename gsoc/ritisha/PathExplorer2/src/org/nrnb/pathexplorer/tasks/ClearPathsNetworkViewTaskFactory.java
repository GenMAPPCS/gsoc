package org.nrnb.pathexplorer.tasks;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;

public class ClearPathsNetworkViewTaskFactory extends AbstractNetworkViewTaskFactory {
	CySwingAppAdapter adapter;

	public ClearPathsNetworkViewTaskFactory(CySwingAppAdapter adapter) {
		this.adapter = adapter;
	}

	public boolean isReady(CyNetworkView networkView) {
		//add condition that a path exists
		return true;
	}

	public TaskIterator createTaskIterator(CyNetworkView netView) {
		return new TaskIterator(new ClearPathsTask(netView, adapter));
	}
}
