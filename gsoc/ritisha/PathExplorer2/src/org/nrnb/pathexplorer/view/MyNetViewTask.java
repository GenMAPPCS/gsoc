package org.nrnb.pathexplorer.view;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;
import org.nrnb.pathexplorer.ui.ExcludeNodesDataInputDialog;

public class MyNetViewTask extends AbstractNetworkViewTask {
	
	private CySwingAppAdapter adapter;
	
	public MyNetViewTask(CyNetworkView netView, CySwingAppAdapter adapter)
	{
		super(netView);	
		this.adapter = adapter;
	}
	
	public void run(TaskMonitor tm) throws Exception {
		new ExcludeNodesDataInputDialog(view.getModel(), adapter);
	}
}
