package org.nrnb.pathexplorer.view;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;

public class MyNetViewTaskFactory extends AbstractNetworkViewTaskFactory {
	
	CySwingAppAdapter adapter;
	
	public MyNetViewTaskFactory(CySwingAppAdapter adapter)
	{
		this.adapter = adapter;
	}
	
	public boolean isReady(CyNetworkView myNetView)
	{
		if(myNetView.equals(null))
			return false;
		else
			return true;
	}
	
	public TaskIterator createTaskIterator(CyNetworkView netView) 
	{
		return new TaskIterator(new MyNetViewTask(netView, adapter));
	}

}