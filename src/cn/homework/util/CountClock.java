package cn.homework.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import cn.homework.util.panel.OperationPanel;
import cn.homework.view.CHUANGGUAN_view;


//��ʱ����
public class CountClock {

	public static final boolean LIANXI = true;
	public static final boolean CHUANGGUAN = false;
	private volatile boolean StopCountFlag = false;
	
	JLabel TimeLabel;			//��ʾʱ��ı�ǩ
	TimeFormat time ;			//���յ������ʱ��
	int min;					//��
	int sec;					//��
	boolean CountModel;			//��ʱ����		true:��ϰģʽ	false:����ģʽ
	
	
	//�ĸ���ť �������ÿ�ʼ��Ϸ��������Ϸ����ͣ��Ϸ��������Ϸ������ĸ�����Ӧ������Ҳ��
	JButton Start;
	JButton Reset;
	JButton Stop;
	JButton KeepOn;
	
	JPanel jpanelTime;  //��ʱ������
	JPanel jpanelButton;  //��ʱ������ť�����
	OperationPanel operateArea;
	
	

	public CountClock(TimeFormat time, JPanel jpanelTime, boolean CountModel) {
		super();
		this.time = time;
		this.min = time.getMin();
		this.sec = time.getSec();
		
		this.jpanelTime = jpanelTime;
		this.CountModel = CountModel;
		
	}

	public CountClock(TimeFormat time, JPanel jpanelTime, JPanel jpanelButton, boolean CountModel, OperationPanel operateArea) {
		super();
		this.time = time;
		this.min = time.getMin();
		this.sec = time.getSec();
		
		this.jpanelTime = jpanelTime;
		this.jpanelButton = jpanelButton;
		this.operateArea = operateArea;
		this.CountModel = CountModel;
		
	}
	
	//�߳�
	TimerThread timerThread = new TimerThread();
	Thread th;
	
	public void setStopCountFlag(boolean stopCountFlag) {
		StopCountFlag = stopCountFlag;
		if(stopCountFlag == false)
		{
			th = new Thread(timerThread);
	    	th.start();
		}
	}

	public boolean isStopCountFlag() {
		return StopCountFlag;
	}
	
	public void setOperateArea(OperationPanel operateArea) {
		this.operateArea = operateArea;
	}
	
	//��ʼ������,�������趨�õ�ʱ�䡣
	public void init() {
		
		//��ʾʱ��
		TimeLabel = new JLabel(time.toString());
		TimeLabel.setFont(new Font("����",1,36));
		TimeLabel.setPreferredSize(new Dimension(600, 100));
		TimeLabel.setBackground(Color.white);
		TimeLabel.setHorizontalAlignment(JLabel.CENTER);
		TimeLabel.setVerticalAlignment(JLabel.CENTER);
		
		TimeLabel.setForeground(Color.blue);
		jpanelTime.add(TimeLabel);
		
		//��ϰģʽ
		if(CountModel == LIANXI)
		{
	    	//����һ�����̲߳�ִ��
	    	th = new Thread(timerThread);
	    	th.start();
		}
		
		//����ģʽ
		else {
			//���ÿ�ʼ��ť
			Dimension preferredSize = new Dimension(160, 25); //���ð�ť�ߴ�
			Start = new JButton("��ʼ");
			Start.setPreferredSize(preferredSize);//����ť�޸ĳ����úõĳߴ�
			Start.addActionListener(new StartCountListener());//��Ӽ���
			jpanelButton.setLayout(new GridLayout(1,2, 10, 0));
			jpanelButton.add(Start);//��Ӱ�ť�����*/
					
			//�������ð�ť
			Reset = new JButton("����");
			Reset.addActionListener(new ResetCountListener());
			
			//������ͣ��ť
			Stop = new JButton("��ͣ");
			Stop.addActionListener(new StopActionListener());
			Stop.setEnabled(false);
			jpanelButton.add(Stop);
			
			//���ü�����ť
			KeepOn = new JButton("����");
			KeepOn.addActionListener(new KeepOnActionListener());
		}

	
	}
	
	public void setTime(TimeFormat time) {
		this.time = time;
		
		TimeLabel.setText(time.toString());
		this.min = time.getMin();
		this.sec = time.getSec();
	}


	public int getTime_Min() {
		return min;
	}
	
	public int getTime_Sec() {
		return sec;
	}
	
	public String getTime() {
		return TimeLabel.getText();
	}
	


	//��ʱ�߳�ִ�еĳ���
	class TimerThread implements Runnable{
		public void run() {
			while(StopCountFlag == false) {
				//˳���ʱģʽ
				if(CountModel == LIANXI) {
					if(sec==60) {
			        	min=min+1;
			        	sec=sec-60;
			        }
					DecimalFormat f1 = new DecimalFormat("00");
					TimeLabel.setText(f1.format(min)+":"+f1.format(sec));
					try {
						Thread.sleep(1000);//�߳�����һ�룬����+1
						sec++;
					}catch(InterruptedException e) {
						break;
				    }
				}
				//����ʱģʽ
				else {
					if(sec<0&&min>0) {
						sec=59;
						min--;
					}
					//�涨��ʾ�ĸ�ʽ
					//��ʱ���ǩ��ʾʱ�䣬ÿ��ˢ��һ��
					DecimalFormat f1 = new DecimalFormat("00");
					TimeLabel.setText(f1.format(min)+":"+f1.format(sec));
					
					//�ж�ʱ���Ƿ����꣬�����ɾ����ͣ��ť
					//TODO	����ģʽ����Ҫ����
					if(sec==0 && min==0) {
						TimeLabel.setText("00:00");
						StopCountFlag = true;
						
						return;
					}
					
					try {
						Thread.sleep(1000);//�߳�����һ�룬����-1
						sec--;
					}catch(InterruptedException e) {
						break;
					}
				}
			}
		}
	}
	
	//TODO ��Ҫ����ʼ��ʱ�¼���ӵ�ͼƬѡ��ȷ�ϵİ�ť��
	//��ʼ��ʱ��ť��ť�����¼�
	 class StartCountListener implements ActionListener {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	
	        	CHUANGGUAN_view jf = (CHUANGGUAN_view) operateArea.getTopLevelAncestor();
	        	jf.setStartGameFlag(true);
	        	
	        	
	        	//����ʼ��ť��Ϊ������
	        	Start.setEnabled(false);
	        	Stop.setEnabled(true);
	        	
	        	operateArea.setListener(true);
	        	
	        	//ˢ�����
	        	jpanelTime.updateUI();
	        	jpanelButton.updateUI();
	        	
	        	//����ģʽ�µ����ʼ��ť�ͼ�ʱ
	        	if(CountModel == CHUANGGUAN)
	        	{
			    	th = new Thread(timerThread);
			    	th.start();
	        	}

	        }
	    }
	 
	 
	 //TODO ���������¼��ڴ���ģʽ��Ӧ��
	 //���ü�ʱ�¼�
	 class ResetCountListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ��ֹ�߳�
				th.interrupt();
				timerThread = new TimerThread();
				// ɾ������е�ʱ����ʾ�����ð�ť����ͣ��ť��������ť
				jpanelTime.remove(TimeLabel);
				jpanelButton.remove(Reset);
				jpanelButton.remove(Stop);
				jpanelButton.remove(KeepOn);
				
				// ��ӿ�ʼ��ť�ͳ�ʼʱ����ʾ
				jpanelButton.add(Start);
				jpanelTime.add(TimeLabel);
				TimeLabel.setText("00:00");
				Start.setEnabled(true);
				
	        	// ˢ�����
	        	jpanelTime. updateUI();
	        	jpanelButton.updateUI();
			}
	    }
	 
	 //��ͣ��ʱ�¼�
	 class StopActionListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ��ֹ�߳�
				th.interrupt();
				// ����ͣ��ť��ɼ�����ť
				jpanelButton.remove(Stop);
				jpanelButton.add(KeepOn);
				operateArea.setListener(false);
				
				jpanelButton.updateUI();
			}
	    	
	    }

	 //������ʱ�¼�
	 class KeepOnActionListener implements ActionListener {
	    	
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		// �����߳�
	    		th = new Thread(timerThread);
	    		th.start();
	    		// ��������ť�����ͣ��ť
				jpanelButton.remove(KeepOn);
				jpanelButton.add(Stop);
				operateArea.setListener(true);
				
				jpanelButton.updateUI();
	    	}
	    	
	    }
	

}
