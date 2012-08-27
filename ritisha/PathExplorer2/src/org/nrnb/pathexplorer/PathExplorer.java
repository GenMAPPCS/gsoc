package org.nrnb.pathexplorer;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyTableFactory;
import org.cytoscape.model.events.NetworkAddedListener;
import org.cytoscape.property.CyProperty;
import org.cytoscape.property.SimpleCyProperty;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.session.CySession;
import org.cytoscape.session.CySessionManager;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.task.NodeViewTaskFactory;
import org.nrnb.pathexplorer.logic.FindAllPaths;
import org.nrnb.pathexplorer.logic.TableHandler;
import org.nrnb.pathexplorer.tasks.ClearPathsNetworkViewTaskFactory;
import org.nrnb.pathexplorer.tasks.ClearPathsNodeViewTaskFactory;
import org.nrnb.pathexplorer.tasks.ExcludeNetworkViewTaskFactory;
import org.nrnb.pathexplorer.tasks.ExcludeNodeViewTaskFactory;
import org.nrnb.pathexplorer.tasks.FindPathsNodeViewTaskFactory;
import org.nrnb.pathexplorer.tasks.IncludeNetworkViewTaskFactory;
import org.nrnb.pathexplorer.tasks.IncludeNodeViewTaskFactory;
import org.nrnb.pathexplorer.tasks.SelectPathsNetworkViewTaskFactory;
import org.nrnb.pathexplorer.tasks.SelectPathsNodeViewTaskFactory;
import org.nrnb.pathexplorer.tasks.SettingsNetworkViewTaskFactory;
import org.nrnb.pathexplorer.tasks.SettingsNodeViewTaskFactory;


public class PathExplorer extends AbstractCySwingApp {
	
	CyTableFactory myTableFactory;
	CyNetworkManager myNetManager;
	CyNetworkTableManager myNetTableManager;
	public static boolean findPathsLastCalled;
	public static String NodeBorderWidthInPaths = "NODE_BORDER_WIDTH_IN_PATHS";
	public static String EdgeWidthInPaths = "EDGE_WIDTH_IN_PATHS";
	public static Double EdgeWidthInPathsValue = 20.0;
	public static Double NodeBorderWidthInPathsValue = 12.0;
	public static Properties nodeBorderWidthProps = new Properties();
	public static Properties edgeWidthProps = new Properties();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PathExplorer(CySwingAppAdapter adapter)
	{
		super(adapter);
	  	final CyServiceRegistrar registrar = adapter.getCyServiceRegistrar();
		Set<CyNetwork> allNets = new HashSet<CyNetwork>();
		findPathsLastCalled = false;
		
	  	myNetManager = adapter.getCyNetworkManager();
	  	
	  	//for all existing networks in the system
	  	allNets = myNetManager.getNetworkSet();
	  	System.out.println("Got all networks, adding IF");
	  	for(CyNetwork currNet : allNets)
	  	{
	  		//initialize hidden tables for each network
	  		TableHandler.createColumns(currNet);

	  	}
	  	
	  	//for newly added networks
	  	registrar.registerService(new TableHandler(), NetworkAddedListener.class, new Properties());
	  	
	  	//Path properties
	  	CyProperty<Properties> nodeBorderWidthProperty = null;
	  	CyProperty<Properties> edgeWidthProperty = null;
	  	CySessionManager mySessionManager;
	  	mySessionManager = adapter.getCySessionManager();
	  	CySession session;
	  	session = mySessionManager.getCurrentSession();
	  	if(session.equals(null))
	  		System.out.println("session null");
	  	Set<CyProperty<?>> props = new HashSet<CyProperty<?>>();
	  	props = session.getProperties();
	  	if(props.equals(null))
	  		System.out.println("props null");
	  	boolean flag = false;
	  	
	  	//for node border width
	  	for (CyProperty<?> prop : props) {
	  	    if (prop.getName() != null){
	  	    	if (prop.getName().equals(NodeBorderWidthInPaths)) {
	  	        nodeBorderWidthProperty = (CyProperty<Properties>) prop;
	  	        flag = true;
	  	        break;
	  	    	}
	  	    }
	  	}
	  	
	  	if (!flag)
	  	{
	  		flag = false;
	  		//create nodeBorderWidthProperty
	  		nodeBorderWidthProps.setProperty(NodeBorderWidthInPaths, NodeBorderWidthInPathsValue.toString());
	  		nodeBorderWidthProperty = new 
					SimpleCyProperty(NodeBorderWidthInPaths, 
							nodeBorderWidthProps, Float.TYPE, CyProperty.SavePolicy.SESSION_FILE_AND_CONFIG_DIR );
	  	}
	  	//if not null, set NodeBorderWidthInPathsValue from property
	  	else
	  	{
	  		nodeBorderWidthProps = nodeBorderWidthProperty.getProperties();
	  		NodeBorderWidthInPathsValue = Double.valueOf((String)nodeBorderWidthProps.get(NodeBorderWidthInPaths));
	  	}
	  	
	  	//for edge width
	  	for (CyProperty<?> prop : props) {
	  	    if (prop.getName() != null && prop.getName().equals(EdgeWidthInPaths)) {
	  	        edgeWidthProperty = (CyProperty<Properties>) prop;
	  	        flag = true;
	  	        break;
	  	    }
	  	}
	  	
	  	if (!flag)
	  	{
	  		flag = false;
	  		//create edgeWidthProperty
	  		edgeWidthProps.setProperty(EdgeWidthInPaths, EdgeWidthInPathsValue.toString());
	  		edgeWidthProperty = new 
					SimpleCyProperty(EdgeWidthInPaths, 
						edgeWidthProps, Float.TYPE, CyProperty.SavePolicy.SESSION_FILE_AND_CONFIG_DIR );
	  	}
	  	//if not null, set EdgeWidthInPathsValue from property
	  	else
	  	{
	  		edgeWidthProps = edgeWidthProperty.getProperties();
	  		EdgeWidthInPathsValue = Double.valueOf((String)edgeWidthProps.get(EdgeWidthInPaths));
	  	}
	  	
	  	
	  	/*
	  	 * Registering NODE context menus
	  	 */
	  	
	  	//Find paths FROM here 
	  	Properties findPathsFromProps = new Properties();
	  	findPathsFromProps.setProperty("preferredAction", "NEW");
	  	findPathsFromProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	findPathsFromProps.setProperty("menuGravity", "6.0f");
	  	findPathsFromProps.setProperty("title", "Find paths from here");
	  	
	  	registrar.registerService(new FindPathsNodeViewTaskFactory(adapter, FindAllPaths.FROM_HERE), 
	  			NodeViewTaskFactory.class, findPathsFromProps);
	  	  	
	  	//Find paths TO here 
	  	Properties findPathsToProps = new Properties();
	  	findPathsToProps.setProperty("preferredAction", "NEW");
	  	findPathsToProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	findPathsToProps.setProperty("menuGravity", "7.0f");
	  	findPathsToProps.setProperty("title", "Find paths to here");
	  	
	  	registrar.registerService(new FindPathsNodeViewTaskFactory(adapter, FindAllPaths.TO_HERE), 
	  			NodeViewTaskFactory.class, findPathsToProps);
	  	
	  	//Exclude node
	  	Properties excludeNodeProps = new Properties();
	  	excludeNodeProps.setProperty("preferredAction", "NEW");
	  	excludeNodeProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	excludeNodeProps.setProperty("menuGravity", "8.0f");
	  	excludeNodeProps.setProperty("title", "Exclude node");
	  	
	  	registrar.registerService(new ExcludeNodeViewTaskFactory(adapter), 
	  			NodeViewTaskFactory.class, excludeNodeProps);
	  	
	  //Include node
	  	Properties includeNodeProps = new Properties();
	  	includeNodeProps.setProperty("preferredAction", "NEW");
	  	includeNodeProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	includeNodeProps.setProperty("menuGravity", "9.0f");
	  	includeNodeProps.setProperty("title", "Reinclude node");
	  	
	  	registrar.registerService(new IncludeNodeViewTaskFactory(adapter), 
	  			NodeViewTaskFactory.class, includeNodeProps);
	  	
	  	//Select Paths 
	  	Properties selectPathsNodeProps = new Properties();
	  	selectPathsNodeProps.setProperty("preferredAction", "NEW");
	  	selectPathsNodeProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	selectPathsNodeProps.setProperty("menuGravity", "10.0f");
	  	selectPathsNodeProps.setProperty("title", "Select paths");
	  	
	  	registrar.registerService(new SelectPathsNodeViewTaskFactory(), 
	  			NodeViewTaskFactory.class, selectPathsNodeProps);
	  	
	  	
	  	//Clear Paths (Refresh button) 
	  	Properties refreshNodeProps = new Properties();
	  	refreshNodeProps.setProperty("preferredAction", "NEW");
	  	refreshNodeProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	refreshNodeProps.setProperty("menuGravity", "11.0f");
	  	refreshNodeProps.setProperty("title", "Clear paths");
	  	
	  	registrar.registerService(new ClearPathsNodeViewTaskFactory(adapter), 
	  			NodeViewTaskFactory.class, refreshNodeProps);
	  	
	  //Settings  
	  	Properties settingsNodeProps = new Properties();
	  	settingsNodeProps.setProperty("preferredAction", "NEW");
	  	settingsNodeProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	settingsNodeProps.setProperty("menuGravity", "12.0f");
	  	settingsNodeProps.setProperty("title", "Settings");
	  	
	  	registrar.registerService(new SettingsNodeViewTaskFactory(adapter), 
	  			NodeViewTaskFactory.class, settingsNodeProps);
	  	
	  
	  	/*
	  	 * Registering NETWORK context menus
	  	 */
	  	
	   //Exclude nodes with.. 
	  	Properties excludeNodesWithProps = new Properties();
	  	excludeNodesWithProps.setProperty("enableFor", "networkAndView");
	  	excludeNodesWithProps.setProperty("preferredAction", "NEW");
	  	excludeNodesWithProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	excludeNodesWithProps.setProperty("menuGravity", "8.0f");
	  	excludeNodesWithProps.setProperty("title", "Exclude Nodes With..");
	  	
	  	registrar.registerService(new ExcludeNetworkViewTaskFactory(adapter), 
	  			NetworkViewTaskFactory.class, excludeNodesWithProps);
	  	
	  	 //Include all nodes
	  	Properties includeAllNodesProps = new Properties();
	  	includeAllNodesProps.setProperty("preferredAction", "NEW");
	  	includeAllNodesProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	includeAllNodesProps.setProperty("menuGravity", "9.0f");
	  	includeAllNodesProps.setProperty("title", "Reinclude all nodes");
	  	
	  	registrar.registerService(new IncludeNetworkViewTaskFactory(adapter), 
	  			NetworkViewTaskFactory.class, includeNodeProps);
	  	
	  	//Select Paths 
	  	Properties selectPathsProps = new Properties();
	  	selectPathsProps.setProperty("enableFor", "networkAndView");
	  	selectPathsProps.setProperty("preferredAction", "NEW");
	  	selectPathsProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	selectPathsProps.setProperty("menuGravity", "10.0f");
	  	selectPathsProps.setProperty("title", "Select paths");
	  	
	  	registrar.registerService(new SelectPathsNetworkViewTaskFactory(), 
	  			NetworkViewTaskFactory.class, selectPathsProps);
	  	
	  	//Clear Paths (Refresh button) 
	  	Properties refreshProps = new Properties();
	  	refreshProps.setProperty("enableFor", "networkAndView");
	  	refreshProps.setProperty("preferredAction", "NEW");
	  	refreshProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	refreshProps.setProperty("menuGravity", "11.0f");
	  	refreshProps.setProperty("title", "Clear paths");
	  	
	  	registrar.registerService(new ClearPathsNetworkViewTaskFactory(adapter), 
	  			NetworkViewTaskFactory.class, refreshProps);
	  	
	  	 //Settings  
	  	Properties settingsNetworkProps = new Properties();
	  	settingsNetworkProps.setProperty("preferredAction", "NEW");
	  	settingsNetworkProps.setProperty("preferredMenu", "PathExplorer[100]");
	  	settingsNetworkProps.setProperty("menuGravity", "12.0f");
	  	settingsNetworkProps.setProperty("title", "Settings");
	  	
	  	registrar.registerService(new SettingsNetworkViewTaskFactory(adapter), 
	  			NetworkViewTaskFactory.class, settingsNetworkProps);
	}
}
