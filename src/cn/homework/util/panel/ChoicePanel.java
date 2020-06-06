package cn.homework.util.panel;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import cn.homework.util.border.MyBorder;

public class ChoicePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JRadioButton radio1 = new JRadioButton("��");
	public JRadioButton radio2 = new JRadioButton("�е�");
	public JRadioButton radio3 = new JRadioButton("����");
	public ButtonGroup bg = new ButtonGroup();
	
	public ChoicePanel() {
		this.setLayout(new GridLayout(3, 1));
		this.setBorder(BorderFactory.createTitledBorder("�Ѷ�"));
		this.setPreferredSize(new Dimension(400, 0));
		MyBorder.addMargin(this, 10);
		MyBorder.addPadding(this, 10);
		this.add(radio1);
		this.add(radio2);
		this.add(radio3);
		bg.add(radio1);
		bg.add(radio2);
		bg.add(radio3);
		radio1.setSelected(true);
	}
	
	public String getValue() {
		if(radio1.isSelected()) {
			return radio1.getText();
		} else if(radio2.isSelected()) {
			return radio2.getText();
		} else {
			return radio3.getText();
		}
	}
	
	
}
