package org.nrnb.pathexplorer.tasks;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;

public class ExcludeNetworkViewTaskFactory extends AbstractNetworkViewTaskFactory {

	CySwingAppAdapter adapter;

	public ExcludeNetworkViewTaskFactory(CySwingAppAdapter adapter) {
		this.adapter = adapter;
	}

	public TaskIterator createTaskIterator(CyNetworkView netView) {
		return new TaskIterator(new ExcludeNetworkViewTask(netView, adapter));
	}

}
