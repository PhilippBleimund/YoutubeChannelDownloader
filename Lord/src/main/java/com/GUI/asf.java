package com.GUI;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class asf extends JPanel {

	/**
	 * Create the panel.
	 */
	public asf() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		
		JLabel lblNewLabel = new JLabel("sdkjlkljsdsdfjkkjsdkjlflkjsdkfjslkdfjslkdjflskdjflksdjflksjdflksdjfkljsdklfjsldkjflksdjflksjdfklsjdlkfjskdfjlksdjfklsdjfklsjdklfjklsjdfklsd");
		panel.add(lblNewLabel);

	}

}
