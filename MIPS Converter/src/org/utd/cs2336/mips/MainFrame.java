/**
 * UTD CS 2336
 * Assignment 4 - Spring 2016
 * @author Parijat Singh
 * email: parijat.sigh@gmail.com * 
 */

package org.utd.cs2336.mips;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;


public class MainFrame extends JFrame {
	
	JTextPane resultPane;
	JPanel resultPanel;
	Converter con;
	MainFrame(Converter converter){
		this.con = converter;
		setTitle("MIPS Converter");
        setSize(800,500);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.white);
        
        JLabel titleLable = new JLabel();
        titleLable.setText("MIPS Converter");
        titleLable.setHorizontalAlignment(JLabel.CENTER);
        titleLable.setFont(new Font("Arial", Font.BOLD, 32));
        titleLable.setBorder(new EmptyBorder(10, 10, 10, 10));
        titleLable.setForeground(Color.blue);
        
        contentPane.add(titleLable, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2,1,5,5)); 
        mainPanel.setBackground(Color.white);
        contentPane.add(mainPanel, BorderLayout.CENTER);
        
        JPanel inputPanel = new JPanel();
        mainPanel.add(inputPanel);
        //inputPanel.setSize(400, 100);
        inputPanel.setBackground(Color.black);
        inputPanel.setLayout(new GridLayout(1,2));
        
        //inputPanel.add(titleLable, BorderLayout.NORTH);
        JPanel instrPanel = new JPanel();
        instrPanel.setBorder(new EmptyBorder(10, 50, 10, 50));
        instrPanel.setBackground(Color.white);
        instrPanel.setLayout(new GridLayout(2,1));
        inputPanel.add(instrPanel);
        JPanel instrInfoPanel = new JPanel();
        instrInfoPanel.setLayout(new GridLayout(2,1));
        instrInfoPanel.setBackground(Color.blue);
        instrPanel.add(instrInfoPanel);
        
        JLabel instrInfoText = new JLabel();
        instrInfoText.setBorder(new EmptyBorder(30, 0, 0, 0));
        instrInfoText.setText("Instruction => Hex");
        instrInfoText.setHorizontalAlignment(JLabel.CENTER);
        instrInfoText.setFont(new Font("Arial", Font.BOLD, 20));
        instrInfoText.setForeground(Color.white);
        //titleLable.setBorder(new EmptyBorder(10, 10, 10, 10));
        instrInfoPanel.add(instrInfoText);
        JLabel instrInfoExampl = new JLabel();
        //instrInfoExampl.setBorder(new EmptyBorder(30, 0, 0, 0));
        instrInfoExampl.setText("(e.g. addi $t1, $gp, 0x1b)");
        instrInfoExampl.setHorizontalAlignment(JLabel.CENTER);
        instrInfoExampl.setFont(new Font("Arial", Font.BOLD, 11));
        instrInfoExampl.setForeground(Color.white);
        instrInfoPanel.add(instrInfoExampl);
        
        JPanel instructionInputPanel = new JPanel();
        //instructionInputPanel.set
        instructionInputPanel.setBackground(Color.blue);
        instructionInputPanel.setLayout(new FlowLayout());
        instrPanel.add(instructionInputPanel);
        
        final JTextField instrInputField = new JTextField(20);
        instrInputField.setBorder(new EmptyBorder(2, 2, 2, 2));
        instrInputField.setMinimumSize(instrInputField.getPreferredSize());
        JButton instrInputButton = new JButton("Convert");
        instrInputButton.setSize(30, 20);
        instrInputButton.setBackground(Color.white);
        instructionInputPanel.add(instrInputField);
        instructionInputPanel.add(instrInputButton);
        
        instrInputButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String input = instrInputField.getText();
				InstructionData data;
				try {
					
					data = con.mipsToHex(input);
					resultPane.setText(data.mi.instrName.toUpperCase() + "\t"
							+ data.mi.description + "\t" + data.mi.detail
							+ "\nInstruction : " + data.instrForm
							+ "\nHex: " + data.hexForm + "\nBinary : "
							+ data.binaryForm);	

					
					//If the panel is already there then remove it
					resultPanel.remove(1);

					if(data.mi.instrType.equals("R")){
						resultPanel.add(createRTypePanel(data));
					}
					if(data.mi.instrType.equals("I")){
						resultPanel.add(createITypePanel(data));
					}
					if(data.mi.instrType.equals("J")){
						resultPanel.add(createJTypePanel(data));
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					resultPane.setText("ERROR:\n"+e1.getMessage());
					resultPanel.remove(1);
					resultPanel.add(new JPanel());
				}
			}
		});
        
        //inputPanel.add(titleLable, BorderLayout.NORTH);
        JPanel hexPanel = new JPanel();
        hexPanel.setBorder(new EmptyBorder(10, 50, 10, 50));
        hexPanel.setBackground(Color.white);
        hexPanel.setLayout(new GridLayout(2,1));
        inputPanel.add(hexPanel);
        JPanel hexInfoPanel = new JPanel();
        hexInfoPanel.setBackground(Color.blue);
        hexInfoPanel.setLayout(new GridLayout(2, 1));
        hexPanel.add(hexInfoPanel);
        
        JLabel hexInfoText = new JLabel();
        hexInfoText.setBorder(new EmptyBorder(30, 0, 0, 0));
        hexInfoText.setText("Hex => Instruction");
        hexInfoText.setHorizontalAlignment(JLabel.CENTER);
        hexInfoText.setFont(new Font("Arial", Font.BOLD, 20));
        hexInfoText.setForeground(Color.white);
        //titleLable.setBorder(new EmptyBorder(10, 10, 10, 10));
        hexInfoPanel.add(hexInfoText);

        JLabel hexInfoExampl = new JLabel();
        //instrInfoExampl.setBorder(new EmptyBorder(30, 0, 0, 0));
        hexInfoExampl.setText("(e.g. 0x025B4822)");
        hexInfoExampl.setHorizontalAlignment(JLabel.CENTER);
        hexInfoExampl.setFont(new Font("Arial", Font.BOLD, 11));
        hexInfoExampl.setForeground(Color.white);
        hexInfoPanel.add(hexInfoExampl);
        
        
        JPanel hexInputPanel = new JPanel();
        hexInputPanel.setBorder(new EmptyBorder(10, 50, 10, 50));
        hexInputPanel.setLayout(new FlowLayout());
        hexInputPanel.setBackground(Color.blue);
        hexPanel.add(hexInputPanel,BorderLayout.CENTER);

        
        final JTextField hexInputField = new JTextField(20);
        hexInputField.setBorder(new EmptyBorder(2, 2, 2, 2));
        JButton hexInputButton = new JButton("Convert");
        hexInputButton.setBackground(Color.white);
        hexInputButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String input = hexInputField.getText();
				InstructionData data;
				try {
					data = con.hexToMips(input);

					resultPane.setText(data.mi.instrName.toUpperCase() + "\t"
							+ data.mi.description + "\t" + data.mi.detail
							+ "\nInstruction : " + data.instrForm
							+ "\nHex: " + data.hexForm + "\nBinary : "
							+ data.binaryForm);					

					resultPanel.remove(1);
					
					if(data.mi.instrType.equals("R")){
						resultPanel.add(createRTypePanel(data));
					}
					if(data.mi.instrType.equals("I")){
						resultPanel.add(createITypePanel(data));
					}
					if(data.mi.instrType.equals("J")){
						resultPanel.add(createJTypePanel(data));
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					resultPane.setText("ERROR:\n"+e1.getMessage());
					resultPanel.remove(1);
					resultPanel.add(new JPanel());
				}
			}
		});
        hexInputPanel.add(hexInputField);
        hexInputPanel.add(hexInputButton);
        
        resultPanel = new JPanel();
        resultPanel.setBackground(Color.white);
        resultPanel.setLayout(new GridLayout(2, 1));
        
        resultPane = new JTextPane();
        resultPane.setBorder(new EmptyBorder(10, 50, 10, 50));
        resultPane.setFont(new Font("Arial", Font.BOLD, 16));
        resultPane.setEditable(false);
        
        resultPanel.add(resultPane);
        JTextPane p = new JTextPane();
        p.setBackground(Color.white);
        resultPanel.add(p);
        mainPanel.add(resultPanel);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //pack();
        setVisible(true);
	}
	
	/**
	 * Creates a new panel for R type instructions 
	 * @param data
	 * @return 
	 */
	
	public JPanel createRTypePanel(InstructionData data){
		
		JPanel p = new JPanel();
		p.setBackground(Color.white);
        p.setBorder(new EmptyBorder(10, 50, 10, 50));
		p.setLayout(new GridLayout(3, 6));
		
		/*
		 * The first row is for the binary translation
		 */
		JTextField opcodeBinary = new JTextField(6);
		opcodeBinary.setHorizontalAlignment(JTextField.CENTER);
		opcodeBinary.setText(data.binaryForm.substring(0, 6));
		opcodeBinary.setBorder(null);
		JTextField rsBinary = new JTextField(5);
		rsBinary.setHorizontalAlignment(JTextField.CENTER);
		rsBinary.setText(data.binaryForm.substring(6,11));
		rsBinary.setBorder(null);
		JTextField rtBinary = new JTextField(5);
		rtBinary.setHorizontalAlignment(JTextField.CENTER);
		rtBinary.setText(data.binaryForm.substring(11,16));
		rtBinary.setBorder(null);
		JTextField rdBinary = new JTextField(5);
		rdBinary.setHorizontalAlignment(JTextField.CENTER);
		rdBinary.setText(data.binaryForm.substring(16,21));
		rdBinary.setBorder(null);
		JTextField shamtBinary = new JTextField(5);
		shamtBinary.setHorizontalAlignment(JTextField.CENTER);
		shamtBinary.setText(data.binaryForm.substring(21,26));
		shamtBinary.setBorder(null);
		JTextField functBinary = new JTextField(6);
		functBinary.setHorizontalAlignment(JTextField.CENTER);
		functBinary.setText(data.binaryForm.substring(26,32));
		functBinary.setBorder(null);
		
		p.add(opcodeBinary); p.add(rsBinary); p.add(rtBinary); p.add(rdBinary); p.add(shamtBinary); p.add(functBinary);
		
		/*
		 * The second row follows the basic instruction formats
		 */
		JTextField opcode = new JTextField(6);
		opcode.setHorizontalAlignment(JTextField.CENTER);
		opcode.setText("SPECIAL");
		JTextField rs = new JTextField(5);
		rs.setHorizontalAlignment(JTextField.CENTER);
		rs.setText(data.rs);
		JTextField rt = new JTextField(5);
		rt.setHorizontalAlignment(JTextField.CENTER);
		rt.setText(data.rt);
		JTextField rd = new JTextField(5);
		rd.setHorizontalAlignment(JTextField.CENTER);
		rd.setText(data.rd);
		JTextField shamt = new JTextField(5);
		shamt.setHorizontalAlignment(JTextField.CENTER);
		shamt.setText(data.shamt);
		JTextField funct = new JTextField(6);
		funct.setHorizontalAlignment(JTextField.CENTER);
		funct.setText(data.mi.instrName.toUpperCase());
		
		p.add(opcode); p.add(rs); p.add(rt); p.add(rd); p.add(shamt); p.add(funct); 
		
		/*
		 * The last row shows the ranges
		 */
		JTextField opcodeRange = new JTextField(6);
		opcodeRange.setHorizontalAlignment(JTextField.CENTER);
		opcodeRange.setText("31-26");
		opcodeRange.setBorder(null);
		JTextField rsRange = new JTextField(5);
		rsRange.setHorizontalAlignment(JTextField.CENTER);
		rsRange.setText("25-21");
		rsRange.setBorder(null);
		JTextField rtRange = new JTextField(5);
		rtRange.setHorizontalAlignment(JTextField.CENTER);
		rtRange.setText("20-16");
		rtRange.setBorder(null);
		JTextField rdRange = new JTextField(5);
		rdRange.setHorizontalAlignment(JTextField.CENTER);
		rdRange.setText("15-11");
		rdRange.setBorder(null);
		JTextField shamtRange = new JTextField(5);
		shamtRange.setHorizontalAlignment(JTextField.CENTER);
		shamtRange.setText("10-6");
		shamtRange.setBorder(null);
		JTextField functRange = new JTextField(6);
		functRange.setHorizontalAlignment(JTextField.CENTER);
		functRange.setText("5-0");
		functRange.setBorder(null);
		
		p.add(opcodeRange); p.add(rsRange); p.add(rtRange); p.add(rdRange); p.add(shamtRange); p.add(functRange);

		
		return p;
		
	}
	
	/**
	 * Creates a new panel for I type instructions 
	 * @param data
	 * @return
	 */
	public JPanel createITypePanel(InstructionData data){
		
		JPanel p = new JPanel();
		p.setBackground(Color.white);
        p.setBorder(new EmptyBorder(10, 50, 10, 50));
		p.setLayout(new GridLayout(3,4));

		JTextField opcodeBinary = new JTextField(6);
		opcodeBinary.setHorizontalAlignment(JTextField.CENTER);
		opcodeBinary.setText(data.binaryForm.substring(0, 6));
		opcodeBinary.setBorder(null);
		JTextField rsBinary = new JTextField(5);
		rsBinary.setHorizontalAlignment(JTextField.CENTER);
		rsBinary.setText(data.binaryForm.substring(6,11));
		rsBinary.setBorder(null);
		JTextField rtBinary = new JTextField(5);
		rtBinary.setHorizontalAlignment(JTextField.CENTER);
		rtBinary.setText(data.binaryForm.substring(11,16));
		rtBinary.setBorder(null);
		JTextField immBinary = new JTextField(5);
		immBinary.setHorizontalAlignment(JTextField.CENTER);
		immBinary.setText(data.binaryForm.substring(26,32));
		immBinary.setBorder(null);
		
		p.add(opcodeBinary); p.add(rsBinary); p.add(rtBinary); p.add(immBinary); 
		
		JTextField opcode = new JTextField(6);
		opcode.setHorizontalAlignment(JTextField.CENTER);
		opcode.setText(data.mi.instrName.toUpperCase());
		JTextField rs = new JTextField(5);
		rs.setHorizontalAlignment(JTextField.CENTER);
		rs.setText(data.rs);
		JTextField rt = new JTextField(5);
		rt.setHorizontalAlignment(JTextField.CENTER);
		rt.setText(data.rt);
		JTextField imm = new JTextField(6);
		imm.setHorizontalAlignment(JTextField.CENTER);
		imm.setText("immediate");
		
		p.add(opcode); p.add(rs); p.add(rt); p.add(imm);  
		

		JTextField opcodeRange = new JTextField(6);
		opcodeRange.setHorizontalAlignment(JTextField.CENTER);
		opcodeRange.setText("31-26");
		opcodeRange.setBorder(null);
		JTextField rsRange = new JTextField(5);
		rsRange.setHorizontalAlignment(JTextField.CENTER);
		rsRange.setText("25-21");
		rsRange.setBorder(null);
		JTextField rtRange = new JTextField(5);
		rtRange.setHorizontalAlignment(JTextField.CENTER);
		rtRange.setText("20-16");
		rtRange.setBorder(null);
		JTextField immRange = new JTextField(6);
		immRange.setHorizontalAlignment(JTextField.CENTER);
		immRange.setText("15-0");
		immRange.setBorder(null);
		
		p.add(opcodeRange); p.add(rsRange); p.add(rtRange); p.add(immRange); 

		
		return p;
		
	}
	
	/**
	 * Creates a new panel for J type instructions 
	 * @param data
	 * @return
	 */
	public JPanel createJTypePanel(InstructionData data){
		
		JPanel p = new JPanel();
		p.setBackground(Color.white);
        p.setBorder(new EmptyBorder(10, 50, 10, 50));
		p.setLayout(new GridLayout(3, 2));
		
		JTextField opcodeBinary = new JTextField(6);
		opcodeBinary.setHorizontalAlignment(JTextField.CENTER);
		opcodeBinary.setText(data.binaryForm.substring(0, 6));
		opcodeBinary.setBorder(null);
		JTextField functBinary = new JTextField(6);
		functBinary.setHorizontalAlignment(JTextField.CENTER);
		functBinary.setText(data.binaryForm.substring(26,32));
		functBinary.setBorder(null);
		
		p.add(opcodeBinary);p.add(functBinary);
		
		JTextField opcode = new JTextField(6);
		opcode.setHorizontalAlignment(JTextField.CENTER);
		opcode.setText(data.mi.instrName.toUpperCase());
		JTextField funct = new JTextField(6);
		funct.setHorizontalAlignment(JTextField.CENTER);
		funct.setText("target");
		
		p.add(opcode); p.add(funct); 
		

		JTextField opcodeRange = new JTextField(6);
		opcodeRange.setHorizontalAlignment(JTextField.CENTER);
		opcodeRange.setText("31-26");
		opcodeRange.setBorder(null);
		JTextField functRange = new JTextField(6);
		functRange.setHorizontalAlignment(JTextField.CENTER);
		functRange.setText("25-0");
		functRange.setBorder(null);
		
		p.add(opcodeRange); p.add(functRange);

		
		return p;
		
	}

}
