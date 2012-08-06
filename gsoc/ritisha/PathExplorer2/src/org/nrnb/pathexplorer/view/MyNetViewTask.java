package org.nrnb.pathexplorer.view;

import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.work.TaskMonitor;
import org.nrnb.pathexplorer.ui.ExcludeNodesDataInputDialog;

public class MyNetViewTask extends AbstractNetworkViewTask {
	
	private Long myNodeTableSUID;
	private CySwingAppAdapter adapter;
	
	public MyNetViewTask(CyNetworkView netView, Long myNodeTableSUID, CySwingAppAdapter adapter)
	{
		super(netView);	
		this.myNodeTableSUID = myNodeTableSUID;
		this.adapter = adapter;
	}
	
	public void run(TaskMonitor tm) throws Exception {
		ExcludeNodesDataInputDialog inputDialog = new ExcludeNodesDataInputDialog(view.getModel(),
				myNodeTableSUID, adapter);
	}
}