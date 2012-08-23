package org.nrnb.pathexplorer.tasks;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;
import org.nrnb.pathexplorer.ui.ExcludeNodesDataInputDialog;

public class ExcludeNetworkViewTask extends AbstractNetworkViewTask {
	
	private CySwingAppAdapter adapter;
	
	public ExcludeNetworkViewTask(CyNetworkView netView, CySwingAppAdapter adapter)
	{
		super(netView);	
		this.adapter = adapter;
	}
	
	public void run(TaskMonitor tm) throws Exception {
		new ExcludeNodesDataInputDialog(view.getModel(), adapter);
	}
}
