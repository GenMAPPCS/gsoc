import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class MyNodeViewTask extends AbstractTask{
	private View<CyNode> nodeView;
	private CyNetworkView netView;

	public MyNodeViewTask(View<CyNode> nodeView, CyNetworkView netView) {
		this.nodeView = nodeView;
		this.netView = netView;
	}

	public void run(TaskMonitor tm) throws Exception {
		
		// Double node border size
		double newXSize =  nodeView.getVisualProperty(BasicVisualLexicon.NODE_BORDER_WIDTH)*2;
				
		nodeView.setVisualProperty(BasicVisualLexicon.NODE_BORDER_WIDTH, newXSize);
		
		netView.updateView();
	} 
	

}
