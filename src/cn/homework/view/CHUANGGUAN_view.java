package cn.homework.view;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.Dimension;


import java.awt.GridLayout;
import java.awt.Image;


import cn.homework.util.CountClock;
import cn.homework.util.RankingList;
import cn.homework.util.SwingConsole;
import cn.homework.util.TimeFormat;
import cn.homework.util.User;
import cn.homework.util.image.ImageView;
import cn.homework.util.panel.MyPanel;
import cn.homework.util.panel.OperationPanel;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CHUANGGUAN_view extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	CountClock cc;
	
	MyPanel jpanelTime = new MyPanel();
	MyPanel jpanelButton = new MyPanel();
	
	private JPanel root;
	private TimeFormat time;
	
	private Image[] imageArr;
	private int imageArrIndex;
	
	private int levelPoint;
	private int userScore;
	private String userName;
	
	private int pattern;
	private static volatile boolean StartGameFlag;

	public void setStartGameFlag(boolean startGameFlag) {
		StartGameFlag = startGameFlag;
	}

	private int Level;
	
	private boolean QuitGameFlag;
	
	
	ImageView previewArea = new ImageView();
	OperationPanel operateArea;
	
	//ÿ�ص�����ʱ��
	public final int INCREASE_TIME = 45;
	
	//ͼƬ���飬��ʼ�Ѷȣ���ʼʱ��
	public CHUANGGUAN_view(Image[] imageArr, int pattern, TimeFormat time) {
		
		root = (JPanel)this.getContentPane();
		
		this.imageArr = imageArr;
		this.imageArrIndex = 0;
		
		this.pattern = pattern;
		
		this.time = time;
		this.levelPoint = 10;
		this.userScore = 0;
		CHUANGGUAN_view.StartGameFlag = false;
		this.QuitGameFlag = false;
		this.Level = 1;
		this.setTitle("Level-" + Level);
		
		init();
		
		//����У�����
		new Thread( new Runnable() {
			@Override
			public void run() {
				while(QuitGameFlag == false)
				{
					if(StartGameFlag == true)
					{
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if( operateArea.check() == true ) {
							cc.setStopCountFlag(true);
							
							Object[] options = { "����", "����" };
							
							int flag = JOptionPane.showOptionDialog(null, "ƴͼ���", "ѡ��",
									JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION,
									null, options, options[0]);
							if(Level == 7)
							{
								JOptionPane.showMessageDialog(null, "��ϲ���ɹ�ͨ�� ", "ͨ�� ", JOptionPane.DEFAULT_OPTION);
								userScore += levelPoint + cc.getTime_Min()*10;
								updateRKL();
								exit();
								break;
							}
							//ѡ�񷵻�ѡ��
							if(flag == 0)
								updateFrame();
							else {
								exit();
								break;
							}
							
						} else {
							
							//��ʱֹͣ��־Ϊtrue
							if(cc.getTime() == "00:00")
							{
								JOptionPane.showMessageDialog(null, "��ʱ���� ", "ʧ�� ", JOptionPane.ERROR_MESSAGE);
								updateRKL();
								exit();
								break;
								
							}
						}
					}
				}
				
			}
		}).start();
	}
	
	
	
	JLabel poiontArea = new JLabel();
	JPanel top = new MyPanel();
	JPanel buttom = new MyPanel();
	
	public void init() {
		
		previewArea.setImage(imageArr[imageArrIndex]);
		
		operateArea = new OperationPanel((BufferedImage)imageArr[imageArrIndex], pattern);
		
		//��ʼ����ʱ��
		cc = new CountClock(time, jpanelTime, jpanelButton, CountClock.CHUANGGUAN, operateArea);
		
		cc.init();
		
		JButton back = new JButton("����");
		back.setPreferredSize(new Dimension(100, 100));
		back.addActionListener(new Tomenu());
		
		poiontArea.setText("�˹ط��� :" + levelPoint + "    " + "��ҷ���:" + userScore);
		top.setLayout(new GridLayout(1, 4, 10, 0));
		
		top.add(jpanelTime);
		top.add(jpanelButton);
		top.add(poiontArea);
		top.add(back);
		
		root.add(top, BorderLayout.NORTH);
		
		buttom.setLayout(new GridLayout(1, 2));
		buttom.add(previewArea);
		buttom.add(operateArea);
		
		root.add(buttom);
		
	}

	public void updateFrame() {
		Level++;
		this.setTitle("Level-" + Level);
		imageArrIndex++;
		
		time.addSec(INCREASE_TIME);
		userScore += levelPoint + cc.getTime_Min()*10;
		levelPoint += 10;
		poiontArea.setText("�˹ط��� :" + levelPoint + "    " + "��ҷ���:" + userScore);
		pattern++;
		
		previewArea.setImage(imageArr[imageArrIndex]);
		buttom.remove(operateArea);
		operateArea = new OperationPanel((BufferedImage)imageArr[imageArrIndex], pattern);
		cc.setOperateArea(operateArea);
		operateArea.setListener(true);
		buttom.add(operateArea);
		
		root.updateUI();
		root.repaint();
		cc.setTime(time);
		cc.setStopCountFlag(false);
	}
	
	public void updateRKL(){
		
		userName = JOptionPane.showInputDialog( "�������������:");
		
		User currentUser = new User(userName, userScore);
		RankingList rkl = new RankingList();
		ArrayList<User> list = null;
		//TODO �ⲿ��չʾ�߼�
		try {
			list = rkl.readUserList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list.add(currentUser);
		Collections.sort(list); //����
		try {
			rkl.writeUserList(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		int i;
		for(i = 0; i < list.size(); i++) {
			sb.append(i+1 + ". " + list.get(i).getUsername() + " " + list.get(i).getScore() + "\n");
		}
		JOptionPane.showMessageDialog(this, sb.toString());
	}
	
	
	public void exit()	{
		cc.setStopCountFlag(true);
		
		StartGameFlag = false;
		dispose();
		
		PINTU window = new PINTU();
		
		SwingConsole.run(window.PINTU);
	}
	
	class Tomenu implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			QuitGameFlag = true;
			cc.setStopCountFlag(true);
			
			dispose();
			
			PINTU window = new PINTU();
			
			SwingConsole.run(window.PINTU);			
		}
	}
	
	
	
}