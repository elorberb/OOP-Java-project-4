import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import java.awt.Canvas;

public class Gui extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(224, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWelcomToModularic = new JLabel("Welcome to AROMA Pizza!");
		lblWelcomToModularic.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		lblWelcomToModularic.setForeground(new Color(0, 0, 128));
		lblWelcomToModularic.setBounds(80, 17, 276, 77);
		contentPane.add(lblWelcomToModularic);
		
		JLabel lblKitchenWorkersWorking = new JLabel("Kitchen Workers working time:");
		lblKitchenWorkersWorking.setForeground(Color.BLUE);
		lblKitchenWorkersWorking.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblKitchenWorkersWorking.setBounds(41, 103, 267, 26);
		contentPane.add(lblKitchenWorkersWorking);
		
		JLabel lblNumberOfPizza = new JLabel("Number Of Pizza Guys:");
		lblNumberOfPizza.setForeground(Color.BLUE);
		lblNumberOfPizza.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNumberOfPizza.setBounds(64, 129, 176, 26);
		contentPane.add(lblNumberOfPizza);
		
		JButton btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int d1 = Integer.parseInt(textField.getText());
				int d2 = Integer.parseInt(textField_1.getText());
				Pizzeria p = new Pizzeria("assignment4_callsData.txt", d1, d2);
				

			}
		});
		btnStart.setFont(new Font("Verdana", Font.BOLD, 13));
		btnStart.setForeground(new Color(34, 139, 34));
		btnStart.setBounds(114, 194, 99, 26);
		contentPane.add(btnStart);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		btnExit.setForeground(new Color(220, 20, 60));
		btnExit.setFont(new Font("Verdana", Font.BOLD, 13));
		btnExit.setBounds(235, 194, 99, 26);
		contentPane.add(btnExit);
		
		textField = new JTextField();
		textField.setBounds(282, 110, 96, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(281, 137, 96, 20);
		contentPane.add(textField_1);
	}
}
