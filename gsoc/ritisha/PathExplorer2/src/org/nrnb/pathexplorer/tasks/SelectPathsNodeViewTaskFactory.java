package org.nrnb.pathexplorer.tasks;

import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;
import org.nrnb.pathexplorer.logic.TableHandler;

public class SelectPathsNodeViewTaskFactory extends AbstractNodeViewTaskFactory {

	public SelectPathsNodeViewTaskFactory() {
	}

	public boolean isReady(View<CyNode> nodeView, CyNetworkView networkView) {
		//condition that clicked node is in a path
		CyRow row = TableHandler.hiddenNodeTable.getRow(nodeView.getModel().getSUID());
		Boolean isInPath = (Boolean) row.get(TableHandler.IN_PATH_COL,
				Boolean.class);
		return isInPath;
	}

	public TaskIterator createTaskIterator(View<CyNode> nodeView,
			CyNetworkView networkView) {
		return new TaskIterator(new SelectPathsTask(networkView));
	}

}
