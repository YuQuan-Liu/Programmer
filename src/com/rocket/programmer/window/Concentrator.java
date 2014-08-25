package com.rocket.programmer.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dialog.ModalityType;
import javax.swing.JLabel;

public class Concentrator extends JDialog {
	private JLabel lblWorkingForIt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Concentrator dialog = new Concentrator();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Concentrator() {
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("集中器");
		setBounds(100, 100, 642, 540);
		getContentPane().setLayout(null);
		
		lblWorkingForIt = new JLabel("working for it......");
		lblWorkingForIt.setBounds(188, 212, 230, 15);
		getContentPane().add(lblWorkingForIt);
	}
}
