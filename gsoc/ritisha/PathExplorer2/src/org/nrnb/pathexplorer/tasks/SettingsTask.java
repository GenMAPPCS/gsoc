package org.nrnb.pathexplorer.tasks;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;
import org.nrnb.pathexplorer.ui.SettingsDialog;

public class SettingsTask extends AbstractNetworkViewTask{
	
	private CyNetworkView netView;
	private CySwingAppAdapter adapter;

	public SettingsTask(CyNetworkView netView, CySwingAppAdapter adapter) {
		super(netView);
		this.adapter = adapter;
		this.netView = netView;
	}

	public void run(TaskMonitor tm) throws Exception {

		new SettingsDialog(netView, adapter);
		
	}

}
