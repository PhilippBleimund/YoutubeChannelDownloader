package com.GUI;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JLayeredPane;

public class customFolderStructurePanel extends JPanel {
	Random seed = new Random();
	JPanel chooseFile;
	/**
	 * Create the panel.
	 */
	public customFolderStructurePanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel TreePanel = new JPanel();
		add(TreePanel, BorderLayout.WEST);
		
		JTree tree = new JTree();
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("JTree") {
				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("colors");
						node_1.add(new DefaultMutableTreeNode("add File"));
						node_1.add(new DefaultMutableTreeNode("violet"));
						node_1.add(new DefaultMutableTreeNode("red"));
						node_1.add(new DefaultMutableTreeNode("yellow"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("sports");
						node_1.add(new DefaultMutableTreeNode("basketball"));
						node_1.add(new DefaultMutableTreeNode("soccer"));
						node_1.add(new DefaultMutableTreeNode("football"));
						node_1.add(new DefaultMutableTreeNode("hockey"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("food");
						node_1.add(new DefaultMutableTreeNode("hot dogs"));
						node_1.add(new DefaultMutableTreeNode("pizza"));
						node_1.add(new DefaultMutableTreeNode("ravioli"));
						node_1.add(new DefaultMutableTreeNode("bananas"));
					add(node_1);
				}
			}
		));
		
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
			    if (tp != null) {
			    	class ThreadFileChooser extends SwingWorker{

						@Override
						protected Object doInBackground() throws Exception {
							DefaultMutableTreeNode a = (DefaultMutableTreeNode) tp.getLastPathComponent();
							DefaultMutableTreeNode aParent = (DefaultMutableTreeNode) a.getParent();
							
							chooseFile.setVisible(!chooseFile.isVisible());
						    chooseFile.setBounds((tree.getPathBounds(tp)).x + (tree.getPathBounds(tp)).width, (tree.getPathBounds(tp)).y, 70, 60);
						    chooseFile.repaint();
						    
						    
					    	/*aParent.add(new DefaultMutableTreeNode("file"));
					    	aParent.remove(a);
					    	aParent.add(new DefaultMutableTreeNode("add File"));
					    	
					    	System.out.println(e.getPath().getLastPathComponent().toString());
					        System.out.println();
					        
					        DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
					        model.reload(aParent);*/
							return null;
						}
			    	}
			    	ThreadFileChooser Thread = new ThreadFileChooser();
			    	Thread.execute();
			    }
				
		    }
		});
		/*tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
		    @Override
		    public void valueChanged(TreeSelectionEvent e) {
		    	if(e.getPath().getLastPathComponent().toString().equals("add File")) {
		    		
		    		class ThreadFileChooser extends SwingWorker{

						@Override
						protected Object doInBackground() throws Exception {
							DefaultMutableTreeNode a = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
					    	DefaultMutableTreeNode aParent = (DefaultMutableTreeNode) a.getParent();
					    	
					    	chooseFile.setBounds((tree.getPathBounds(e.getPath())).x, (tree.getPathBounds(e.getPath())).y, 70, 60);
					    	chooseFile.setVisible(!chooseFile.isVisible());
					    	
					    	/*aParent.add(new DefaultMutableTreeNode("file"));
					    	aParent.remove(a);
					    	aParent.add(new DefaultMutableTreeNode("add File"));
					    	
					    	System.out.println(e.getPath().getLastPathComponent().toString());
					        System.out.println();
					        
					        DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
					        model.reload(aParent);
							return null;
						}
		    		}
		    		ThreadFileChooser Thread = new ThreadFileChooser();
		    		Thread.execute();
		    		
		    	}
		    }
		});*/
		TreePanel.add(tree);
		
		chooseFile = new JPanel();
		chooseFile.setVisible(true);
		chooseFile.setPreferredSize(new Dimension(70, 60));
		chooseFile.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		TreePanel.add(chooseFile);
		
		JButton VideoFile_btn = new JButton("Video");
		VideoFile_btn.setPreferredSize(new Dimension(70, 20));
		chooseFile.add(VideoFile_btn);
		
		JButton AudioFile_btn = new JButton("Audio");
		AudioFile_btn.setPreferredSize(new Dimension(70, 20));
		chooseFile.add(AudioFile_btn);
		
		JButton FolderFile_btn = new JButton("Folder");
		FolderFile_btn.setPreferredSize(new Dimension(70, 20));
		chooseFile.add(FolderFile_btn);
		
		JPanel controlPanel = new JPanel();
		add(controlPanel, BorderLayout.SOUTH);
	}
}
