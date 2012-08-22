package org.nrnb.pathexplorer;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.swing.CyNodeViewContextMenuFactory;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableFactory;
import org.cytoscape.model.events.NetworkAddedListener;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.task.NodeViewTaskFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.nrnb.pathexplorer.logic.MyNetAddedListener;
import org.nrnb.pathexplorer.tasks.RefreshState;
import org.nrnb.pathexplorer.tasks.RefreshStateFromNode;
import org.nrnb.pathexplorer.tasks.SelectPaths;
import org.nrnb.pathexplorer.tasks.SelectPathsFromNode;
import org.nrnb.pathexplorer.ui.AddAsSource;
import org.nrnb.pathexplorer.tasks.ExcludeNode;
import org.nrnb.pathexplorer.ui.SetAsTarget;
import org.nrnb.pathexplorer.view.MyNetViewTaskFactory;

public class PathExplorer extends AbstractCySwingApp {
	
	CyTableFactory myTableFactory;
	CyNetworkManager myNetManager;
	CyNetworkTableManager myNetTableManager;
	VisualMappingManager myVisMappingManager;
	VisualStyleFactory myVisStyleFactory;
	public static VisualStyle baseVisualStyle;
	
	public PathExplorer(CySwingAppAdapter adapter)
	{
		super(adapter);
	  	final CyServiceRegistrar registrar = adapter.getCyServiceRegistrar();
		Set<CyNetwork> allNets = new HashSet<CyNetwork>();
	  	CyTable hiddenNodeTable;
	  	CyTable hiddenEdgeTable;
	  	VisualStyle currVisualStyle;
	  	myVisMappingManager = adapter.getVisualMappingManager();
	  	myVisStyleFactory = adapter.getVisualStyleFactory();
	  	currVisualStyle = myVisMappingManager.getCurrentVisualStyle();
		baseVisualStyle = myVisStyleFactory.createVisualStyle(currVisualStyle);
	  	
	  	myNetManager = adapter.getCyNetworkManager();
	  	
	  	//for all existing networks in the system
	  	allNets = myNetManager.getNetworkSet();
	  	System.out.println("Got all networks, adding IF");
	  	for(CyNetwork currNet : allNets)
	  	{
	  		hiddenNodeTable = currNet.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
	  		hiddenNodeTable.createColumn("isExcludedFromPaths", Boolean.class, true, Boolean.FALSE);
	  		hiddenNodeTable.createColumn("isInPath", Boolean.class, true, Boolean.FALSE);
	  		hiddenEdgeTable = currNet.getTable(CyEdge.class, CyNetwork.HIDDEN_ATTRS);
	  		hiddenEdgeTable.createColumn("isInPath", Boolean.class, true, Boolean.FALSE);
	  		
	  	}
	  	
	  	//for newly added networks
	  	registrar.registerService(new MyNetAddedListener(), NetworkAddedListener.class, new Properties());
	  	
	  	//registering all the context menus:-
	  	
	  	//Find paths from here (Add as Source) in Node context menu
	  	registrar.registerService(new AddAsSource(adapter),
	                                  CyNodeViewContextMenuFactory.class,
	                                  new Properties());
	  	
	  	//Find paths to here (Set as Target) in Node context menu
	  	registrar.registerService(new SetAsTarget(adapter),CyNodeViewContextMenuFactory.class,
                new Properties());
	  	
	  	
	  	
	   //Exclude nodes with.. in context menu of network
	  	Properties excludeNodesWithProps = new Properties();
	  	excludeNodesWithProps.setProperty("enableFor", "networkAndView");
	  	excludeNodesWithProps.setProperty("preferredAction", "NEW");
	  	excludeNodesWithProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	excludeNodesWithProps.setProperty("menuGravity", "8.0f");
	  	excludeNodesWithProps.setProperty("title", "Exclude Nodes With..");
	  	
	  	registrar.registerService(new MyNetViewTaskFactory(adapter), 
	  			NetworkViewTaskFactory.class, excludeNodesWithProps);
	  	
	  	//Exclude nodes in Node Context Menu
	  	Properties excludeNodeProps = new Properties();
	  	excludeNodeProps.setProperty("preferredAction", "NEW");
	  	excludeNodeProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	excludeNodeProps.setProperty("menuGravity", "8.0f");
	  	excludeNodeProps.setProperty("title", "Exclude node");
	  	
	  	registrar.registerService(new ExcludeNode(), 
	  			NodeViewTaskFactory.class, excludeNodeProps);
	  	
	  	
	  	//Select Paths in Network Context Menu
	  	Properties selectPathsProps = new Properties();
	  	selectPathsProps.setProperty("enableFor", "networkAndView");
	  	selectPathsProps.setProperty("preferredAction", "NEW");
	  	selectPathsProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	selectPathsProps.setProperty("menuGravity", "9.0f");
	  	selectPathsProps.setProperty("title", "Select Paths");
	  	
	  	registrar.registerService(new SelectPaths(), 
	  			NetworkViewTaskFactory.class, selectPathsProps);
	  	
	  	//Select Paths in Node Context Menu
	  	Properties selectPathsNodeProps = new Properties();
	  	selectPathsNodeProps.setProperty("preferredAction", "NEW");
	  	selectPathsNodeProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	selectPathsNodeProps.setProperty("menuGravity", "9.0f");
	  	selectPathsNodeProps.setProperty("title", "Select Paths");
	  	
	  	registrar.registerService(new SelectPathsFromNode(), 
	  			NodeViewTaskFactory.class, selectPathsNodeProps);
	  	
	  	//Clear Paths (Refresh button) in Network Context Menu
	  	Properties refreshProps = new Properties();
	  	refreshProps.setProperty("enableFor", "networkAndView");
	  	refreshProps.setProperty("preferredAction", "NEW");
	  	refreshProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	refreshProps.setProperty("menuGravity", "10.0f");
	  	refreshProps.setProperty("title", "Clear Paths");
	  	
	  	registrar.registerService(new RefreshState(adapter), 
	  			NetworkViewTaskFactory.class, refreshProps);
	  	
	  	
	  	//Clear Paths (Refresh button) in Node Context Menu
	  	Properties refreshNodeProps = new Properties();
	  	refreshNodeProps.setProperty("preferredAction", "NEW");
	  	refreshNodeProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	refreshNodeProps.setProperty("menuGravity", "10.0f");
	  	refreshNodeProps.setProperty("title", "Clear Paths");
	  	
	  	registrar.registerService(new RefreshStateFromNode(adapter), 
	  			NodeViewTaskFactory.class, refreshNodeProps);
	}
}
