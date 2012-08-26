package org.nrnb.pathexplorer.tasks;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;
import org.nrnb.pathexplorer.logic.TableHandler;

public class IncludeNetworkViewTaskFactory  extends AbstractNetworkViewTaskFactory{
	
	CySwingAppAdapter adapter;

	public IncludeNetworkViewTaskFactory(CySwingAppAdapter adapter) {
		this.adapter = adapter;
	}

	public boolean isReady(CyNetworkView netView) {
		//condition that atleast one node is excluded
		CyNetwork net = netView.getModel();
		CyRow row;
		Boolean isExcluded;
		List<CyNode> allNodes = new ArrayList<CyNode>();
		allNodes = net.getNodeList();
		for(CyNode currNode : allNodes)
		{
			row = TableHandler.hiddenNodeTable.getRow(currNode.getSUID());
			isExcluded = (Boolean) row.get("isExcludedFromPaths",
					Boolean.class);
			if(isExcluded)
				return true;
		}
		return false;
	}

	public TaskIterator createTaskIterator(CyNetworkView netView) {
		return new TaskIterator(new IncludeNetworkViewTask(netView, adapter));
	}

}
