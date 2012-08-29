package org.nrnb.pathexplorer.tasks;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.task.AbstractNodeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskMonitor;
import org.nrnb.pathexplorer.PathExplorer;
import org.nrnb.pathexplorer.logic.ExclusionHandler;
import org.nrnb.pathexplorer.logic.FindAllPaths;
import org.nrnb.pathexplorer.logic.TableHandler;

public class ExcludeNodeViewTask extends AbstractNodeViewTask {

	CyNetworkView netView;
	View<CyNode> nodeView;
	CySwingAppAdapter adapter;
	
	public ExcludeNodeViewTask(View<CyNode> nodeView, CyNetworkView netView, CySwingAppAdapter adapter) {
		super(nodeView, netView);
		this.netView = netView;
		this.nodeView = nodeView;
		this.adapter = adapter;
	}

	public void run(TaskMonitor tm) throws Exception {
		CyNetwork net;
		CyNode node;
		net = netView.getModel();
		node = nodeView.getModel();
		CyTable defaultNodeTable = net.getDefaultNodeTable();
		CyRow row = defaultNodeTable.getRow(node.getSUID());
		row.set(TableHandler.EXCLUDED_COL, true);
		CyRow row1 = net.getRow(node);
		row1.set(CyNetwork.SELECTED, false);
		ExclusionHandler eh = new ExclusionHandler(adapter);
		eh.visuallyExcludeNode(nodeView, netView);

		// Then rerun last FindPaths call or clear path if excluded node =
		// source or target node from last FindPaths call
		if(PathExplorer.findPathsLastCalled)
		{
			ClearPathsTask refresher = new ClearPathsTask(netView, adapter);
			refresher.run(tm);
			FindAllPaths pathsFinder = new FindAllPaths(FindPathsNodeViewTask.netView , FindPathsNodeViewTask.nodeView.getModel(), adapter);
			pathsFinder.findAllPathsMethod(FindPathsNodeViewTask.direction);
		}
	}

}
