package org.nrnb.pathexplorer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.property.CyProperty;
import org.cytoscape.property.SimpleCyProperty;
import org.cytoscape.view.model.CyNetworkView;
import org.nrnb.pathexplorer.PathExplorer;
import org.nrnb.pathexplorer.logic.ExclusionHandler;

public class SettingsDialog extends JDialog{

	JLabel headingLabel, nodeBorderWidthLabel, edgeWidthLabel;
	JTextField nodeBorderWidthInput, edgeWidthInput;
	JButton setButton;
	CyNetworkView netView;
	CySwingAppAdapter adapter;
	
	public SettingsDialog(CyNetworkView netView, CySwingAppAdapter adapter)
	{
		super();
		this.netView = netView;
		this.adapter = adapter;
		headingLabel = new JLabel("Set path properties");
		nodeBorderWidthLabel = new JLabel("Node Border Width");
		edgeWidthLabel = new JLabel("Edge Width");
		nodeBorderWidthInput = new JTextField(PathExplorer.nodeBorderWidthInPaths.toString());
		edgeWidthInput = new JTextField(PathExplorer.edgeWidthInPaths.toString());
		setButton = new JButton("Set");
		
		setButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// take the values, set the Properties and refresh networks
				PathExplorer.nodeBorderWidthInPaths = Double.valueOf(nodeBorderWidthInput.getText());
				PathExplorer.edgeWidthInPaths = Double.valueOf(edgeWidthInput.getText());
				
			}
		});
		
				
	}
}
