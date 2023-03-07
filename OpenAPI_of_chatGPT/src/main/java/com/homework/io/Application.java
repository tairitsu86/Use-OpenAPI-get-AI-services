package com.homework.io;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.chatGPT.openAPI.OpenAPI;
import com.google.smtp.MailSender;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class Application extends JFrame {
	private static final long serialVersionUID = -6869760752482730054L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JButton button;
	private JTextPane input;
	private JTextArea output;
	private OpenAPI oa = new OpenAPI();
	private MailSender ms = new MailSender();
	private String to="",question,result,emailMessage="If you want get the record by email, you can send SETEMAIL:your email to set Email!\r\nEx: SETEMAIL=aaa@email.com\r\nNow setting Email:";
	private JTextArea emailText;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application frame = new Application();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Application() {
		setTitle("OpenAI");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 665, 714);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 20, 613, 469);
		contentPane.add(scrollPane);
		
		output = new JTextArea();
		output.setEditable(false);
		output.setFont(new Font("Monospaced", Font.PLAIN, 18));
		output.setBackground(new Color(255, 255, 255));
		output.setText("System:Welcome to OpenAI!\nSystem:This program write by java!\nSystem:Now ask something!\n");
		scrollPane.setViewportView(output);
		
		button = new JButton("Send Question");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				question = input.getText();
				input.setText("");
				if(question.substring(0, 9).equals("SETEMAIL=")) {
					to = question.substring(9);
					output.setText(output.getText()+"System:Set email:"+to+" success!\n");
					emailText.setText(emailMessage+to);
					return;
				}
				output.setText(output.getText()+"User:"+question+"\n");
				result = oa.ask(question);
				output.setText(output.getText()+"AI:"+result+"\n");
				ms.send(to, "The result of question:"+question,"You ask:\n"+question+"\nAI's respone:\n"+result);
			}
		});
		button.setFont(new Font("新細明體", Font.PLAIN, 18));
		button.setBounds(492, 499, 142, 100);
		contentPane.add(button);
		
		input = new JTextPane();
		input.setFont(new Font("新細明體", Font.PLAIN, 18));
		input.setBackground(Color.WHITE);
		input.setBounds(21, 499, 463, 100);
		contentPane.add(input);
		
		emailText = new JTextArea();
		emailText.setText(emailMessage+to);
		emailText.setEditable(false);
		emailText.setBackground(Color.LIGHT_GRAY);
		emailText.setBounds(21, 611, 613, 56);
		contentPane.add(emailText);
	}
}
