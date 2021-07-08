package com.GUI;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;

import YoutubeDownload.YoutubeConnector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JProgressBar;

public class newGUI {

	private JFrame frame;
	private JTextField workingDir_TField;
	
	private JTabbedPane downloadArea_tPane;
	private JTextField outputDir_TField;
	private JProgressBar progressBar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					
				}
				System.out.println(info.getClassName());
			}
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					newGUI window = new newGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public newGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel GeneralPanel = new JPanel();
		frame.getContentPane().add(GeneralPanel, BorderLayout.CENTER);
		GeneralPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel controlPanel = new JPanel();
		controlPanel.setPreferredSize(new Dimension(220, 500));
		GeneralPanel.add(controlPanel, BorderLayout.WEST);
		
		JLabel workingDir_lbl = new JLabel("working dir:");
		controlPanel.add(workingDir_lbl);
		
		workingDir_TField = new JTextField();
		controlPanel.add(workingDir_TField);
		workingDir_TField.setColumns(10);
		
		JButton setWorkingDir_btn = new JButton("set");
		controlPanel.add(setWorkingDir_btn);
		
		JButton newTab_btn = new JButton("new Tab");
		newTab_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JPanel newTab = new JPanel();
				newTab.add(new JLabel("xxx"+Math.random()));
				
				String title = "Tab "+(int)(Math.random()*100);
				MyTabHeader header = new MyTabHeader(title, downloadArea_tPane);
				int i = downloadArea_tPane.getTabCount();
				downloadArea_tPane.addTab("", newTab);
				downloadArea_tPane.setTabComponentAt(i, header);
			}
		});
		
		JLabel outputDir_lbl = new JLabel("output Dir:");
		controlPanel.add(outputDir_lbl);
		
		outputDir_TField = new JTextField();
		controlPanel.add(outputDir_TField);
		outputDir_TField.setColumns(10);
		
		JButton setOutputDIr_btn = new JButton("set");
		controlPanel.add(setOutputDIr_btn);
		
		JButton addNewEntry_btn = new JButton("add new");
		addNewEntry_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIManager.put("OptionPane.minimumSize", new Dimension(400, 350));
				NewEntryPanel EntryPanel = new NewEntryPanel();
				int selected = JOptionPane.showOptionDialog(null , EntryPanel, "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
			    if(selected == JOptionPane.OK_OPTION) {
			    	String item = (String)EntryPanel.comboBox.getSelectedItem();
			    	switch(item) {
			    	case "Videos":
			    		JPanel newTab = new JPanel();
			    		newTab.setLayout(new BorderLayout());
			    		
			    		JScrollPane scrollPane = new JScrollPane();
			    		newTab.add(scrollPane, BorderLayout.CENTER);
			    		
			    		JPanel panel = new JPanel();
			    		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			    		panel.setPreferredSize(new Dimension(newTab.getWidth(), 100));
			    		scrollPane.setViewportView(panel);
			    		
						String title = "Video";
						MyTabHeader header = new MyTabHeader(title, downloadArea_tPane);
						int i = downloadArea_tPane.getTabCount();
						downloadArea_tPane.addTab("", newTab);
						downloadArea_tPane.setTabComponentAt(i, header);
						progressBar.setIndeterminate(true);
						for(int j=0;j<EntryPanel.URLTable.getRowCount();j++) {
							YoutubeConnector connector = new YoutubeConnector();
							Object[] data = connector.getVideoInfo((String)EntryPanel.URLTable.getValueAt(j, 0));
							VideoElementPanel videoElement = new VideoElementPanel((String)data[0], (String)data[1], (Format)data[2], (Format)data[3], connector);
							videoElement.addListener(new DownloadListener() {
								@Override
								public void downloadAction() {
									class Download extends SwingWorker<Void, Void> {
								        /*
								         * Main task. Executed in background thread.
								         */
								        @Override
								        public Void doInBackground() {
								        	connector.downloadVideoAndAudio(workingDir_TField.getText(), (Format)data[2], (Format)data[3], (String)data[0]);
											return null;
								        }
								        
								        @Override
								        public void done() {
								        	progressBar.setIndeterminate(false);
								        }
									}
									progressBar.setIndeterminate(true);
									Download download = new Download();
									download.execute();
								}
							});
							
							panel.add(videoElement);
						}
						progressBar.setIndeterminate(false);
						break;
					case "Playlist":
						
						break;
					case "Channel":
						
						break;
			    	}
			    }
			}
		});
		controlPanel.add(addNewEntry_btn);
		controlPanel.add(newTab_btn);
		
		JButton customFolderStructure_btn = new JButton("custom folder structure");
		customFolderStructure_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIManager.put("OptionPane.minimumSize", new Dimension(400, 350));
				customFolderStructurePanel folderStructure = new customFolderStructurePanel();
				int selected = JOptionPane.showOptionDialog(null , folderStructure, "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
			    if(selected == JOptionPane.OK_OPTION) {
			    	
			    }
			}
		});
		controlPanel.add(customFolderStructure_btn);
		
		downloadArea_tPane = new JTabbedPane(JTabbedPane.TOP);
		GeneralPanel.add(downloadArea_tPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		downloadArea_tPane.addTab("New tab", null, panel, null);
		
		progressBar = new JProgressBar();
		frame.getContentPane().add(progressBar, BorderLayout.SOUTH);
	}
	
	private class MyTabHeader extends JPanel{
		private JLabel label = new JLabel("");
		private JButton button = new JButton("x");
		private JTabbedPane tabpane;
		private JPanel thispanel = this;
		public MyTabHeader(String title,JTabbedPane pane){
			super();
			button.setOpaque(false);
			button.setBorderPainted(false);
			button.setPreferredSize(new Dimension(15, 15));
			button.setMargin(new Insets(0,0,0,0));
			//button.setSize(new Dimension(5, 5));
			button.addMouseListener(new MouseListener() {
				@Override
				public void mouseEntered(MouseEvent e) {
					button.setOpaque(true);
					button.setBackground(Color.RED);
					button.setForeground(Color.WHITE);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					button.setOpaque(false);
					button.setForeground(Color.BLACK);
				}
				@Override
				public void mouseClicked(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
				}
				@Override
				public void mouseReleased(MouseEvent e) {
				}
				
			});
			thispanel.setOpaque(false);
			label.setText(title);
			tabpane=pane;
			add(label);
			add(button);
			
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int i = tabpane.indexOfTabComponent(thispanel);
					 if (i != -1) {
	                tabpane.remove(i);
	            }
				}
			});
		}
		
	}
}
