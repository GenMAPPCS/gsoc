package org.nrnb.pathexplorer.tasks;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;
import org.nrnb.pathexplorer.logic.TableHandler;

public class IncludeNodeViewTaskFactory extends AbstractNodeViewTaskFactory{

	CySwingAppAdapter adapter;
	
	public IncludeNodeViewTaskFactory(CySwingAppAdapter adapter){
		this.adapter = adapter;
	}
	
	public boolean isReady(View<CyNode> nodeView, CyNetworkView networkView) {
		//condition that clicked node is already excluded
		CyRow row = TableHandler.hiddenNodeTable.getRow(nodeView.getModel().getSUID());
		Boolean isExcluded = (Boolean) row.get("isExcludedFromPaths",
				Boolean.class);
		return isExcluded;
	}

	public TaskIterator createTaskIterator(View<CyNode> nodeView,
			CyNetworkView networkView) {
		return new TaskIterator(new IncludeNodeViewTask(nodeView, networkView, adapter));
	}

}
