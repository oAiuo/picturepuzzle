package cn.homework.util.panel;

import javax.swing.JButton;
import javax.swing.SpringLayout;

public class BottomBar extends MyPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JButton confirmButton = new JButton("ȷ��");
	public JButton cancelButton = new JButton("ȡ��");
	
	public BottomBar() {
		this.padding(10).preferredHeight(40);
		SpringLayout springLayout = new SpringLayout();
		this.setLayout(springLayout);
		this.add(confirmButton);
		this.add(cancelButton);
		springLayout.putConstraint(SpringLayout.WEST, confirmButton, 50, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.WEST, cancelButton, 150, SpringLayout.EAST, confirmButton);
	}

}
