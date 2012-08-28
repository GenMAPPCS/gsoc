package org.nrnb.pathexplorer.tasks;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;
import org.nrnb.pathexplorer.logic.TableHandler;

public class SelectPathsNetworkViewTaskFactory extends AbstractNetworkViewTaskFactory {

	public SelectPathsNetworkViewTaskFactory() {
	}

	public boolean isReady(CyNetworkView netView) {
		//condition that a path exists
		CyNetwork net = netView.getModel();
		CyRow row;
		Boolean isInPath;
		List<CyNode> allNodes = new ArrayList<CyNode>();
		allNodes = net.getNodeList();
		for(CyNode currNode : allNodes)
		{
			row = TableHandler.hiddenNodeTable.getRow(currNode.getSUID());
			isInPath = (Boolean) row.get(TableHandler.IN_PATH_COL,
					Boolean.class);
			if(isInPath)
				return true;
		}
		return false;
	}

	public TaskIterator createTaskIterator(CyNetworkView netView) {
		return new TaskIterator(new SelectPathsTask(netView));
	}

}
