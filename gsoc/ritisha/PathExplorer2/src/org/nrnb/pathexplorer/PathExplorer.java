package org.nrnb.pathexplorer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.swing.CyNodeViewContextMenuFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableFactory;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.nrnb.pathexplorer.logic.MyNetAddedListener;
import org.nrnb.pathexplorer.ui.AddAsSource;
import org.nrnb.pathexplorer.ui.ClearPathsNCM;
import org.nrnb.pathexplorer.ui.ExcludeNode;
import org.nrnb.pathexplorer.ui.SelectPathsNCM;
import org.nrnb.pathexplorer.ui.SetAsTarget;
import org.nrnb.pathexplorer.view.MyNetViewTaskFactory;
import org.nrnb.pathexplorer.view.RefreshState;
import org.nrnb.pathexplorer.view.SelectPaths;
import org.cytoscape.model.events.NetworkAddedListener;

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
		CyRow row;
		Set<CyNetwork> allNets = new HashSet<CyNetwork>();
	  	CyTable tempTable;
	  	List<CyNode> allNodes = new ArrayList<CyNode>();
	  	VisualStyle tempVisualStyle;
	  	myVisMappingManager = adapter.getVisualMappingManager();
	  	myVisStyleFactory = adapter.getVisualStyleFactory();
	  	tempVisualStyle = myVisMappingManager.getCurrentVisualStyle();
		baseVisualStyle = myVisStyleFactory.createVisualStyle(tempVisualStyle);
	  	//Add inclusionFactor attribute for all node
	  	
	  	myNetManager = adapter.getCyNetworkManager();
	  	
	  	//for all existing networks in the system
	  	allNets = myNetManager.getNetworkSet();
	  	System.out.println("Got all networks, adding IF");
	  	for(CyNetwork currNet : allNets)
	  	{
	  		tempTable = currNet.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
	  		tempTable.createColumn("inclusionFactor", Boolean.class, true, true);
	  		tempTable.createColumn("inPaths", Boolean.class, true, false);
	  		
	  		allNodes = currNet.getNodeList();
	  		for(CyNode currNode : allNodes)
	  		{
	  			row = tempTable.getRow(currNode.getSUID());
		  		row.set("inclusionFactor", true);
		  		row.set("inPaths", false);
	  		}
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
	  	
	  	//Exclude Node in Node Context menu
	  	registrar.registerService(new ExcludeNode(adapter),CyNodeViewContextMenuFactory.class,
                new Properties());
	  	
	  	//Select Paths in Node Context Menu
	  	registrar.registerService(new SelectPathsNCM(adapter),CyNodeViewContextMenuFactory.class,
                new Properties());
	  	
	  	//Clear Paths in Node Context Menu
	  	registrar.registerService(new ClearPathsNCM(adapter),CyNodeViewContextMenuFactory.class,
                new Properties());
	  	
	   //Exclude nodes with.. in context menu of network
	  	Properties excludeNodesProps = new Properties();
	  	excludeNodesProps.setProperty("enableFor", "networkAndView");
	  	excludeNodesProps.setProperty("preferredAction", "NEW");
	  	excludeNodesProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	excludeNodesProps.setProperty("menuGravity", "1.0f");
	  	excludeNodesProps.setProperty("title", "Exclude Nodes With..");
	  	
	  	registrar.registerService(new MyNetViewTaskFactory(adapter), 
	  			NetworkViewTaskFactory.class, excludeNodesProps);
	  	
	  	//Select Paths in Network Context Menu
	  	Properties selectPathsProps = new Properties();
	  	selectPathsProps.setProperty("enableFor", "networkAndView");
	  	selectPathsProps.setProperty("preferredAction", "NEW");
	  	selectPathsProps.setProperty("menuGravity", "2.0f");
	  	selectPathsProps.setProperty("title", "Select Paths");
	  	
	  	registrar.registerService(new SelectPaths(adapter), 
	  			NetworkViewTaskFactory.class, selectPathsProps);
	  	
	  	//Clear Paths (Refresh button) in Network Context Menu
	  	Properties refreshProps = new Properties();
	  	refreshProps.setProperty("enableFor", "networkAndView");
	  	refreshProps.setProperty("preferredAction", "NEW");
	  	refreshProps.setProperty("menuGravity", "3.0f");
	  	refreshProps.setProperty("title", "Clear Paths");
	  	
	  	registrar.registerService(new RefreshState(adapter), 
	  			NetworkViewTaskFactory.class, refreshProps);
	}
}
