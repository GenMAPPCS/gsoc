package org.nrnb.pathexplorer.tasks;

import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class SetVisualBypassNodeViewTaskFactory extends AbstractNodeViewTaskFactory{
	
	
	public SetVisualBypassNodeViewTaskFactory (){	
	}
	
	public TaskIterator createTaskIterator(View<CyNode> nodeView, CyNetworkView netView) {
		return new TaskIterator(new SetVisualBypassNodeViewTask(nodeView, netView));
	}
	

}
