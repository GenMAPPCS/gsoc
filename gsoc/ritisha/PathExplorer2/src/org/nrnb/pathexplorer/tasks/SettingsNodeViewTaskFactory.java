package org.nrnb.pathexplorer.tasks;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class SettingsNodeViewTaskFactory extends AbstractNodeViewTaskFactory{

	CySwingAppAdapter adapter;

	public SettingsNodeViewTaskFactory(CySwingAppAdapter adapter) {
		this.adapter = adapter;
	}

	public boolean isReady(View<CyNode> nodeView, CyNetworkView networkView) {
		//enabled for all nodes
		return true;
	}

	public TaskIterator createTaskIterator(View<CyNode> nodeView,
			CyNetworkView networkView) {
		return new TaskIterator(new SettingsTask(networkView, adapter));
	}
}
