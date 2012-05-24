import org.cytoscape.app.AbstractCyApp;
import org.cytoscape.app.CyAppAdapter;

public class HideSingletonNodesApp extends AbstractCyApp 
{
	public HideSingletonNodesApp(CyAppAdapter adapter)
	{
		super(adapter);
		adapter.getCySwingApplication().addAction(new MenuAction(adapter));
	}
}
		