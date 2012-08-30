package org.nrnb.pathexplorer.logic;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.TaskMonitor;
import org.nrnb.pathexplorer.PathExplorer;
import org.nrnb.pathexplorer.tasks.ClearPathsTask;
import org.nrnb.pathexplorer.tasks.FindPathsNodeViewTask;

public class ExclusionHandler {

	CySwingAppAdapter adapter;
	CyApplicationManager myAppManager;

	public ExclusionHandler(CySwingAppAdapter adapt) {
		this.adapter = adapt;
		myAppManager = adapter.getCyApplicationManager();
	}
	@SuppressWarnings("unchecked")
	public void handleIF(CyColumn selectedCol, String selectedOp,
			Object selectedVal, CyNetwork myNet) throws Throwable {
		// get the default node table and myDefaultNodeTable and list of all
		// nodes in the network
		CyTable myDefaultNodeTable;
		CyNetworkView netView;
		View<CyNode> nodeView;
		List<CyNode> allNodes = new ArrayList<CyNode>();
		CyRow row1, row2, row3;
		myDefaultNodeTable = myNet.getDefaultNodeTable();
		allNodes = myNet.getNodeList();
		netView = myAppManager.getCurrentNetworkView();

		// based on type of selectedColumn, proceed further
		if (selectedCol.getType().equals(String.class)) {
			// if type is string, then selectedOp can be "Equals" or
			// "Does not equal"
			if (selectedOp.equals("Equals")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					if (selectedVal.equals(row1.getRaw(selectedCol.getName()))) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			} else if (selectedOp.equals("Does not equal")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					if (!selectedVal.equals(row1.getRaw(selectedCol.getName()))) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			}
		}

		else if (selectedCol.getType().equals(Boolean.class)) {
			// if type is Boolean, the selectedOp is only "="
			if (selectedVal.equals(true)) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					if (row1.getRaw(selectedCol.getName()).equals(true)) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			} else if (selectedVal.equals(false)) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					if (row1.getRaw(selectedCol.getName()).equals(false)) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			}
		}

		else if (selectedCol.getType().equals(Integer.class)) {
			// User-entered string needs to be converted
			Integer selectedValI = Integer.valueOf((String) selectedVal);

			// selectedOp can be =, <, >, <=, >=, !=
			if (selectedOp.equals("=")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					Integer currNodeValI = (Integer) row1.getRaw(selectedCol
							.getName());

					if (currNodeValI != null
							&& currNodeValI.compareTo(selectedValI) == 0) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			} else if (selectedOp.equals("!=")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					Integer currNodeValI = (Integer) row1.getRaw(selectedCol
							.getName());

					if (currNodeValI != null
							&& currNodeValI.compareTo(selectedValI) != 0) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			} else if (selectedOp.equals("<")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					Integer currNodeValI = (Integer) row1.getRaw(selectedCol
							.getName());

					if (currNodeValI != null
							&& currNodeValI.compareTo(selectedValI) < 0) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			} else if (selectedOp.equals(">")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					Integer currNodeValI = (Integer) row1.getRaw(selectedCol
							.getName());

					if (currNodeValI != null
							&& currNodeValI.compareTo(selectedValI) > 0) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			} else if (selectedOp.equals("<=")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					Integer currNodeValI = (Integer) row1.getRaw(selectedCol
							.getName());

					if (currNodeValI != null
							&& currNodeValI.compareTo(selectedValI) <= 0) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			} else if (selectedOp.equals(">=")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					Integer currNodeValI = (Integer) row1.getRaw(selectedCol
							.getName());

					if (currNodeValI != null
							&& currNodeValI.compareTo(selectedValI) >= 0) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			}
		}

		else if (selectedCol.getType().equals(Long.class)) {
			// User-entered string needs to be converted
			Long selectedValL = Long.valueOf((String) selectedVal);

			// selectedOp can be =, <, >, <=, >=, !=
			if (selectedOp.equals("=")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					Long currNodeValL = (Long) row1.getRaw(selectedCol
							.getName());

					if (currNodeValL != null
							&& currNodeValL.compareTo(selectedValL) == 0) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			} else if (selectedOp.equals("!=")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					Long currNodeValL = (Long) row1.getRaw(selectedCol
							.getName());

					if (currNodeValL != null
							&& currNodeValL.compareTo(selectedValL) != 0) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			} else if (selectedOp.equals("<")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					Long currNodeValL = (Long) row1.getRaw(selectedCol
							.getName());

					if (currNodeValL != null
							&& currNodeValL.compareTo(selectedValL) < 0) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			} else if (selectedOp.equals(">")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					Long currNodeValL = (Long) row1.getRaw(selectedCol
							.getName());

					if (currNodeValL != null
							&& currNodeValL.compareTo(selectedValL) > 0) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			} else if (selectedOp.equals("<=")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					Long currNodeValL = (Long) row1.getRaw(selectedCol
							.getName());

					if (currNodeValL != null
							&& currNodeValL.compareTo(selectedValL) <= 0) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			} else if (selectedOp.equals(">=")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					Long currNodeValL = (Long) row1.getRaw(selectedCol
							.getName());

					if (currNodeValL != null
							&& currNodeValL.compareTo(selectedValL) >= 0) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			}
		}

		else if (selectedCol.getType().equals(Double.class)) {
			// User-entered string needs to be converted
			Double selectedValD = Double.valueOf((String) selectedVal);

			// selectedOp can be =, <, >, <=, >=, !=
			if (selectedOp.equals("=")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					Double currNodeValD = (Double) row1.getRaw(selectedCol
							.getName());

					if (currNodeValD != null
							&& currNodeValD.compareTo(selectedValD) == 0) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			} else if (selectedOp.equals("!=")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					Double currNodeValD = (Double) row1.getRaw(selectedCol
							.getName());

					if (currNodeValD != null
							&& currNodeValD.compareTo(selectedValD) != 0) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			} else if (selectedOp.equals("<")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					Double currNodeValD = (Double) row1.getRaw(selectedCol
							.getName());

					if (currNodeValD != null
							&& currNodeValD
							.compareTo(selectedValD) < 0) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			} else if (selectedOp.equals(">")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					Double currNodeValD = (Double) row1.getRaw(selectedCol
							.getName());

					if (currNodeValD != null
							&& currNodeValD.compareTo(selectedValD) > 0) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			} else if (selectedOp.equals("<=")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					Double currNodeValD = (Double) row1.getRaw(selectedCol
							.getName());

					if (currNodeValD != null
							&& currNodeValD.compareTo(selectedValD) <= 0) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			} else if (selectedOp.equals(">=")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					Double currNodeValD = (Double) row1.getRaw(selectedCol
							.getName());

					if (currNodeValD != null
							&& currNodeValD.compareTo(selectedValD) >= 0) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			}
		}

		else {
			// List, selected operator can be Contains and Does not contain
			List<String> stringList = new ArrayList<String>();
			if (selectedOp.equals("Contains")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					stringList = (ArrayList<String>) row1.getRaw(selectedCol
							.getName());
					if (stringList != null && stringList.contains(selectedVal)) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			} else if (selectedOp.equals("Does not contain")) {
				for (CyNode currNode : allNodes) {
					row1 = myDefaultNodeTable.getRow(currNode.getSUID());
					stringList = (ArrayList<String>) row1.getRaw(selectedCol
							.getName());
					if (stringList != null && !stringList.contains(selectedVal)) {
						row2 = myDefaultNodeTable.getRow(currNode.getSUID());
						row2.set(TableHandler.EXCLUDED_COL, true);
						row3 = myNet.getRow(currNode);
						row3.set(CyNetwork.SELECTED, false);
						nodeView = netView.getNodeView(currNode);
						visuallyExcludeNode(nodeView, netView);
					}
				}
			}
		}

		// Then rerun last FindPaths call or clear path if excluded node =
		// source or target node from last FindPaths call
		if (PathExplorer.findPathsLastCalled) {
			TaskMonitor tm = null;
			ClearPathsTask refresher = new ClearPathsTask(netView, adapter);
			refresher.run(tm);
			FindAllPaths pathsFinder = new FindAllPaths(
					FindPathsNodeViewTask.netView,
					FindPathsNodeViewTask.nodeView.getModel(), adapter);
			pathsFinder.findAllPathsMethod(FindPathsNodeViewTask.direction);
		}
	}

	public void visuallyExcludeNode(View<CyNode> nodeView, CyNetworkView netView) {
		nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH, 0.0);
		nodeView.setLockedValue(BasicVisualLexicon.NODE_TRANSPARENCY, 20);

		// re-apply current visual style and refresh network view
		VisualMappingManager visualMappingManager = adapter
				.getVisualMappingManager();
		VisualStyle style = visualMappingManager.getCurrentVisualStyle();
		style.apply(netView);
		netView.updateView();
	}
}
