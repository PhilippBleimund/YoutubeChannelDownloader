package com.drachen.Lord;
import java.awt.EventQueue;

import javax.swing.JFrame;

import WebScrapper.downloadVideo;
import WebScrapper.ogermirror;
import YoutubeDownload.Downloader;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class MainGUI {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField2;
	private JTextField textField3;
	private JPanel panel_1;
	private JScrollPane scrollPane_1;
	private JPanel panel_2;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;

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
					MainGUI window = new MainGUI();
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
	public MainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 579, 433);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblNewLabel = new JLabel("Files dir:");
		frame.getContentPane().add(lblNewLabel);
		
		textField2 = new JTextField("D:\\OgerMirror");
		textField2.setPreferredSize(new Dimension(150, 20));
		frame.getContentPane().add(textField2);
		
		lblNewLabel_1 = new JLabel("           Final dir:");
		frame.getContentPane().add(lblNewLabel_1);
		
		textField3 = new JTextField("D:\\OgerMirror\\merged");
		textField3.setPreferredSize(new Dimension(150, 20));
		frame.getContentPane().add(textField3);
		
		lblNewLabel_2 = new JLabel("                        Channel Id:");
		frame.getContentPane().add(lblNewLabel_2);
		
		textField = new JTextField("UCm3_j4RLEzgMovQTRPx47MQ");
		textField.setPreferredSize(new Dimension(150, 20));
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("start Parsing");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Downloader loader = new Downloader();
				String[] videoIds = loader.getChannelVideos(textField.getText());
				String[] videoTitles = loader.getTitles();
				MyButton[] buttonArr = new MyButton[videoIds.length];
				for(int i=0;i<videoIds.length;i++) {
					buttonArr[i] = new MyButton(videoTitles[i], i);
					buttonArr[i].putClientProperty("index", i);
					buttonArr[i].addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							
							class Task extends SwingWorker<Void, Void> {
						        /*
						         * Main task. Executed in background thread.
						         */
						        @Override
						        public Void doInBackground() {
						        	MyButton b = (MyButton) e.getSource();
									loader.downloadAudio(videoIds[(int) b.getClientProperty("index")], textField2.getText());
									loader.downloadVideo(videoIds[(int) b.getClientProperty("index")], textField2.getText());
									
									File video = new File(textField2.getText() + "\\" + videoTitles[(int) b.getClientProperty("index")] + ".webm");
									if(!video.exists()) {
										video = new File(textField2.getText() + "\\" + videoTitles[(int) b.getClientProperty("index")] + ".mp4");
									}
									
									
									try {
										loader.mergeAudioToVideo(new File("C:\\Program Files\\ffmpeg\\bin\\ffmpeg.exe"), 
												new File(textField2.getText() + "\\" + videoTitles[(int) b.getClientProperty("index")] + ".m4a"),
												video,
												new File(textField3.getText()), 
												videoTitles[(int) b.getClientProperty("index")] + ".mp4");
									} catch (IOException | InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									System.out.println(videoIds[(int) b.getClientProperty("index")] + " finished");
									return null;
						        }
							}
							Task task = new Task();
							task.execute();
							
						}
						
					});
					buttonArr[i].setPreferredSize(new Dimension(135, 25));
					panel_2.add(buttonArr[i]);
					scrollPane_1.revalidate();
					scrollPane_1.repaint();
				}
			}
		});
		frame.getContentPane().add(btnNewButton);
		
		panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(400, 300));
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setPreferredSize(new Dimension(500, 300));
		panel_1.add(scrollPane_1, BorderLayout.NORTH);
		
		panel_2 = new JPanel();
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_2.setPreferredSize(new Dimension(600, 300));
		scrollPane_1.setViewportView(panel_2);
	}
	
	public class MyButton extends JButton {
		
		public MyButton(String label, int i) {
	        super(label);
	    }
		
	}
}
