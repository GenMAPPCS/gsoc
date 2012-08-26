package org.nrnb.pathexplorer.tasks;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskMonitor;
import org.nrnb.pathexplorer.PathExplorer;
import org.nrnb.pathexplorer.logic.FindAllPaths;


public class FindPathsNodeViewTask extends AbstractNodeViewTask{

	public static CyNetworkView netView;
	public static View<CyNode> nodeView;
	CySwingAppAdapter adapter;
	public static String direction;
	
	public FindPathsNodeViewTask(View<CyNode> nodeView, CyNetworkView netView, CySwingAppAdapter adapter, String direction)
	{
		super(nodeView,netView);	
		FindPathsNodeViewTask.netView = netView;
		FindPathsNodeViewTask.nodeView = nodeView;
		this.adapter = adapter;
		FindPathsNodeViewTask.direction = direction;
		PathExplorer.findPathsLastCalled = true;
	}
	
	public void run(TaskMonitor tm) throws Exception {
		ClearPathsTask refresher = new ClearPathsTask(netView, adapter);
		refresher.run(tm);
		FindAllPaths pathsFinder = new FindAllPaths(netView , nodeView.getModel(), adapter);
		pathsFinder.allPathsMethod(direction);
	}

}

