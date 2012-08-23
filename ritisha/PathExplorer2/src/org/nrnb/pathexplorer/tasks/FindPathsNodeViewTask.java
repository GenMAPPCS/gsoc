package org.nrnb.pathexplorer.tasks;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskMonitor;
import org.nrnb.pathexplorer.logic.FindAllPaths;


public class FindPathsNodeViewTask extends AbstractNodeViewTask{

	CyNetworkView netView;
	View<CyNode> nodeView;
	CySwingAppAdapter adapter;
	String direction;
	
	public FindPathsNodeViewTask(View<CyNode> nodeView, CyNetworkView netView, CySwingAppAdapter adapter, String direction)
	{
		super(nodeView,netView);	
		this.netView = netView;
		this.nodeView = nodeView;
		this.adapter = adapter;
		this.direction = direction;
	}
	
	public void run(TaskMonitor tm) throws Exception {
		FindAllPaths pathsFinder = new FindAllPaths(netView , nodeView.getModel(), adapter);
		pathsFinder.allPathsMethod(direction);
	}

}

