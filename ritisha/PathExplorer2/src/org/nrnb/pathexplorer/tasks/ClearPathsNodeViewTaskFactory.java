package org.nrnb.pathexplorer.tasks;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class ClearPathsNodeViewTaskFactory extends AbstractNodeViewTaskFactory {
	
	CySwingAppAdapter adapter;

	public ClearPathsNodeViewTaskFactory(CySwingAppAdapter adapter) {
		this.adapter = adapter;
	}

	public boolean isReady(View<CyNode> nodeView, CyNetworkView networkView) {
		//add condition that clicked node is in a path
		return true;
	}

	public TaskIterator createTaskIterator(View<CyNode> nodeView,
			CyNetworkView networkView) {
		return new TaskIterator(new ClearPathsTask(networkView, adapter));
	}
}
