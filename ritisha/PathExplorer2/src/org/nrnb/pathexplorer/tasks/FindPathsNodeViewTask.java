package org.nrnb.pathexplorer.tasks;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskMonitor;
import org.nrnb.pathexplorer.logic.FindAllPaths;


public class FindPathsNodeViewTask extends AbstractNodeViewTask{

	public static CyNetworkView netView;
	public static View<CyNode> nodeView = null;
	CySwingAppAdapter adapter;
	public static CyEdge.Type direction = null;
	
	public FindPathsNodeViewTask(View<CyNode> nodeView, CyNetworkView netView, CySwingAppAdapter adapter, CyEdge.Type direction)
	{
		super(nodeView,netView);	
		FindPathsNodeViewTask.netView = netView;
		FindPathsNodeViewTask.nodeView = nodeView;
		this.adapter = adapter;
		FindPathsNodeViewTask.direction = direction;
	}
	
	public void run(TaskMonitor tm) throws Exception {
		ClearPathsTask refresher = new ClearPathsTask(netView, adapter);
		TaskMonitor tm1 = null;
		refresher.run(tm1);
		FindAllPaths pathsFinder = new FindAllPaths(netView , nodeView.getModel(), adapter);
		pathsFinder.findAllPathsMethod(direction);
	}

}

