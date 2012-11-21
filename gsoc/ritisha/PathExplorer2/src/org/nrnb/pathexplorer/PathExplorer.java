package org.nrnb.pathexplorer;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.events.NetworkAddedListener;
import org.cytoscape.property.CyProperty;
import org.cytoscape.property.SimpleCyProperty;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.session.CySession;
import org.cytoscape.session.CySessionManager;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.task.NodeViewTaskFactory;
import static org.cytoscape.work.ServiceProperties.MENU_GRAVITY;
import static org.cytoscape.work.ServiceProperties.IN_MENU_BAR;
import static org.cytoscape.work.ServiceProperties.PREFERRED_ACTION;
import static org.cytoscape.work.ServiceProperties.PREFERRED_MENU;
import static org.cytoscape.work.ServiceProperties.TITLE;
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
	
	
	CyNetworkManager myNetManager;
	public static String NodeBorderWidthInPaths = "NODE_BORDER_WIDTH_IN_PATHS";
	public static String EdgeWidthInPaths = "EDGE_WIDTH_IN_PATHS";
	public static Double EdgeWidthInPathsValue = 12.0;
	public static Double NodeBorderWidthInPathsValue = 20.0;
	public static Properties nodeBorderWidthProps = new Properties();
	public static Properties edgeWidthProps = new Properties();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PathExplorer(CySwingAppAdapter adapter)
	{
		super(adapter);
	  	final CyServiceRegistrar registrar = adapter.getCyServiceRegistrar();
		Set<CyNetwork> allNets = new HashSet<CyNetwork>();
		
	  	myNetManager = adapter.getCyNetworkManager();
	  	
	  	//for all existing networks in the system
	  	allNets = myNetManager.getNetworkSet();
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
	  		//create nodeBorderWidthProperty
	  		nodeBorderWidthProps.setProperty(NodeBorderWidthInPaths, NodeBorderWidthInPathsValue.toString());
	  		nodeBorderWidthProperty = new 
					SimpleCyProperty(NodeBorderWidthInPaths, 
							nodeBorderWidthProps, Float.TYPE, CyProperty.SavePolicy.SESSION_FILE_AND_CONFIG_DIR );
	  	}
	  	//if not null, set NodeBorderWidthInPathsValue from property
	  	else
	  	{
	  		flag = false;
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
	  		//create edgeWidthProperty
	  		edgeWidthProps.setProperty(EdgeWidthInPaths, EdgeWidthInPathsValue.toString());
	  		edgeWidthProperty = new 
					SimpleCyProperty(EdgeWidthInPaths, 
						edgeWidthProps, Float.TYPE, CyProperty.SavePolicy.SESSION_FILE_AND_CONFIG_DIR );
	  	}
	  	//if not null, set EdgeWidthInPathsValue from property
	  	else
	  	{
	  		flag = false;
	  		edgeWidthProps = edgeWidthProperty.getProperties();
	  		EdgeWidthInPathsValue = Double.valueOf((String)edgeWidthProps.get(EdgeWidthInPaths));
	  	}
	  	
	  	
	  	/*
	  	 * Registering NODE context menus
	  	 */
	  	
	  	//Find paths FROM here 
	  	Properties findPathsFromProps = new Properties();
	  	findPathsFromProps.setProperty(PREFERRED_ACTION, "NEW");
	  	findPathsFromProps.setProperty(PREFERRED_MENU, "PathExplorer[100]");
	  	findPathsFromProps.setProperty(MENU_GRAVITY, "6.0f");
	  	findPathsFromProps.setProperty(IN_MENU_BAR, "false");
	  	findPathsFromProps.setProperty(TITLE, "Find paths from here");
	  	
	  	registrar.registerService(new FindPathsNodeViewTaskFactory(adapter, CyEdge.Type.OUTGOING), 
	  			NodeViewTaskFactory.class, findPathsFromProps);
	  	  	
	  	//Find paths TO here 
	  	Properties findPathsToProps = new Properties();
	  	findPathsToProps.setProperty(PREFERRED_ACTION, "NEW");
	  	findPathsToProps.setProperty(PREFERRED_MENU, "PathExplorer[100]");
	  	findPathsToProps.setProperty(MENU_GRAVITY, "7.0f");
	  	findPathsToProps.setProperty(IN_MENU_BAR, "false");
	  	findPathsToProps.setProperty(TITLE, "Find paths to here");
	  	
	  	registrar.registerService(new FindPathsNodeViewTaskFactory(adapter, CyEdge.Type.INCOMING), 
	  			NodeViewTaskFactory.class, findPathsToProps);
	  	
	  	//Exclude node
	  	Properties excludeNodeProps = new Properties();
	  	excludeNodeProps.setProperty(PREFERRED_ACTION, "NEW");
	  	excludeNodeProps.setProperty(PREFERRED_MENU, "PathExplorer[100]");
	  	excludeNodeProps.setProperty(MENU_GRAVITY, "8.0f");
	  	excludeNodeProps.setProperty(IN_MENU_BAR, "false");
	  	excludeNodeProps.setProperty(TITLE, "Exclude node");
	  	
	  	registrar.registerService(new ExcludeNodeViewTaskFactory(adapter), 
	  			NodeViewTaskFactory.class, excludeNodeProps);
	  	
	  //Include node
	  	Properties includeNodeProps = new Properties();
	  	includeNodeProps.setProperty(PREFERRED_ACTION, "NEW");
	  	includeNodeProps.setProperty(PREFERRED_MENU, "PathExplorer[100]");
	  	includeNodeProps.setProperty(MENU_GRAVITY, "9.0f");
	  	includeNodeProps.setProperty(IN_MENU_BAR, "false");
	  	includeNodeProps.setProperty(TITLE, "Reinclude node");
	  	
	  	registrar.registerService(new IncludeNodeViewTaskFactory(adapter), 
	  			NodeViewTaskFactory.class, includeNodeProps);
	  	
	  	//Select Paths 
	  	Properties selectPathsNodeProps = new Properties();
	  	selectPathsNodeProps.setProperty(PREFERRED_ACTION, "NEW");
	  	selectPathsNodeProps.setProperty(PREFERRED_MENU, "PathExplorer[100]");
	  	selectPathsNodeProps.setProperty(MENU_GRAVITY, "10.0f");
	  	selectPathsNodeProps.setProperty(IN_MENU_BAR, "false");
	  	selectPathsNodeProps.setProperty(TITLE, "Select path");
	  	
	  	registrar.registerService(new SelectPathsNodeViewTaskFactory(), 
	  			NodeViewTaskFactory.class, selectPathsNodeProps);
	  	
	  	
	  	//Clear Paths (Refresh button) 
	  	Properties refreshNodeProps = new Properties();
	  	refreshNodeProps.setProperty(PREFERRED_ACTION, "NEW");
	  	refreshNodeProps.setProperty(PREFERRED_MENU, "PathExplorer[100]");
	  	refreshNodeProps.setProperty(MENU_GRAVITY, "11.0f");
	  	refreshNodeProps.setProperty(IN_MENU_BAR, "false");
	  	refreshNodeProps.setProperty(TITLE, "Clear path");
	  	
	  	registrar.registerService(new ClearPathsNodeViewTaskFactory(adapter), 
	  			NodeViewTaskFactory.class, refreshNodeProps);
	  	
	  //Settings  
	  	Properties settingsNodeProps = new Properties();
	  	settingsNodeProps.setProperty(PREFERRED_ACTION, "NEW");
	  	settingsNodeProps.setProperty(PREFERRED_MENU, "PathExplorer[100]");
	  	settingsNodeProps.setProperty(MENU_GRAVITY, "12.0f");
	  	settingsNodeProps.setProperty(IN_MENU_BAR, "false");
	  	settingsNodeProps.setProperty(TITLE, "Settings");
	  	
	  	registrar.registerService(new SettingsNodeViewTaskFactory(adapter), 
	  			NodeViewTaskFactory.class, settingsNodeProps);
	  	
	  
	  	/*
	  	 * Registering NETWORK context menus
	  	 */
	  	
	   //Exclude nodes with.. 
	  	Properties excludeNodesWithProps = new Properties();
	  	//excludeNodesWithProps.setProperty("enableFor", "networkAndView");
	  	excludeNodesWithProps.setProperty(PREFERRED_ACTION, "NEW");
	  	excludeNodesWithProps.setProperty(PREFERRED_MENU, "PathExplorer[100]");
	  	excludeNodesWithProps.setProperty(MENU_GRAVITY, "8.0f");
	  	excludeNodesWithProps.setProperty(IN_MENU_BAR, "false");
	  	excludeNodesWithProps.setProperty(TITLE, "Exclude nodes with...");
	  	
	  	registrar.registerService(new ExcludeNetworkViewTaskFactory(adapter), 
	  			NetworkViewTaskFactory.class, excludeNodesWithProps);
	  	
	  	 //Include all nodes
	  	Properties includeAllNodesProps = new Properties();
	  	includeAllNodesProps.setProperty(PREFERRED_ACTION, "NEW");
	  	includeAllNodesProps.setProperty(PREFERRED_MENU, "PathExplorer[100]");
	  	includeAllNodesProps.setProperty(MENU_GRAVITY, "9.0f");
	  	includeAllNodesProps.setProperty(IN_MENU_BAR, "false");
	  	includeAllNodesProps.setProperty(TITLE, "Reinclude all nodes");
	  	
	  	registrar.registerService(new IncludeNetworkViewTaskFactory(adapter), 
	  			NetworkViewTaskFactory.class, includeAllNodesProps);
	  	
	  	//Select Paths 
	  	Properties selectPathsProps = new Properties();
	  	//selectPathsProps.setProperty("enableFor", "networkAndView");
	  	selectPathsProps.setProperty(PREFERRED_ACTION, "NEW");
	  	selectPathsProps.setProperty(PREFERRED_MENU, "PathExplorer[100]");
	  	selectPathsProps.setProperty(MENU_GRAVITY, "10.0f");
	  	selectPathsProps.setProperty(IN_MENU_BAR, "false");
	  	selectPathsProps.setProperty(TITLE, "Select path");
	  	
	  	registrar.registerService(new SelectPathsNetworkViewTaskFactory(), 
	  			NetworkViewTaskFactory.class, selectPathsProps);
	  	
	  	//Clear Paths (Refresh button) 
	  	Properties refreshProps = new Properties();
	  	//refreshProps.setProperty("enableFor", "networkAndView");
	  	refreshProps.setProperty(PREFERRED_ACTION, "NEW");
	  	refreshProps.setProperty(PREFERRED_MENU, "PathExplorer[100]");
	  	refreshProps.setProperty(MENU_GRAVITY, "11.0f");
	  	refreshProps.setProperty(IN_MENU_BAR, "false");
	  	refreshProps.setProperty(TITLE, "Clear path");
	  	
	  	registrar.registerService(new ClearPathsNetworkViewTaskFactory(adapter), 
	  			NetworkViewTaskFactory.class, refreshProps);
	  	
	  	 //Settings  
	  	Properties settingsNetworkProps = new Properties();
	  	settingsNetworkProps.setProperty(PREFERRED_ACTION, "NEW");
	  	settingsNetworkProps.setProperty(PREFERRED_MENU, "PathExplorer[100]");
	  	settingsNetworkProps.setProperty(MENU_GRAVITY, "12.0f");
	  	settingsNetworkProps.setProperty(IN_MENU_BAR, "false");
	  	settingsNetworkProps.setProperty(TITLE, "Settings");
	  	
	  	registrar.registerService(new SettingsNetworkViewTaskFactory(adapter), 
	  			NetworkViewTaskFactory.class, settingsNetworkProps);
	}
}
