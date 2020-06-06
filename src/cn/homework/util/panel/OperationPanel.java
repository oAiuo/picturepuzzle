package cn.homework.util.panel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cn.homework.util.ImageCut;
import cn.homework.util.button.PPButton;
import cn.homework.util.image.ImageView;


public class OperationPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PPButton[] button; // ��ť����
	Image[] img; // ͼƬ����
	int[] order; // ��Ƭ���˳��
	int nullButton; //�հװ�ť
	int pattern; //ƴͼ��ģ
	int total;


	public OperationPanel(BufferedImage image, int pattern) {
		this.pattern = pattern;
		this.total = pattern * pattern;
		button = new PPButton[total];
		img = new Image[total];
		order = new int[total];
		nullButton = total - 1;
		sliceRandom(image);
	}
	
	public void sliceRandom(BufferedImage image) {
		ImageCut.cutImage(image, pattern, "slice"); //��������ͼƬ����ָ����ģʽ�зֵ�ָ��·����
		this.removeAll();
		this.updateUI();
		this.setLayout(new GridLayout(pattern, pattern));
		random(order);
		for(int i=0;i<total;i++){   
	    	//��ʼ����ť
	    	button[i]=new PPButton();
	    	button[i].setRow(i/pattern);//��ʼ��ÿһ����ť���ڵ���
	    	button[i].setCol(i%pattern);//��ʼ��ÿһ����ť���ڵ���
		    this.add(button[i]);
	    }
		for(int i=0;i<total-1;i++){
			try {
				img[i] = ImageIO.read(new File("slice" + "\\" + order[i] + ".jpg"));
				button[i].setImage(img[i]);
				button[i].setScaleType(ImageView.FIT_XY);
				button[i].setBorder(BorderFactory.createLineBorder(Color.black));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		button[nullButton].setImage(null);
		
		/*for(int i=0;i<total;i++){   
	    	//��ÿһ����ť��Ӽ����¼�
	    	button[i].addMouseListener(new MouseAdapter(){
				@Override
				public void mousePressed(MouseEvent e) {
					PPButton button=(PPButton)e.getSource();
					remove(button);
				}
	    	});
	    }*/
		
	}
	
	//����������飬����ͼƬλ��
	public void random(int a[]){
	    while(true){
			Random cd=new Random();
			int i=0;
			a[0]=cd.nextInt(total-1);
			for(i=1;i<total-1;i++)
			{
				int temp=cd.nextInt(total-1);
				for(int j=0;j<i;j++)
				{
					if(a[j]!=temp)
					{
						a[i]=temp;
					}
					else
					{
						i--;
						break;
					}
				}
			}
			a[i]=total-1;
			if(isOdd(a))
				return;
	   }
	}
	
	
	 public boolean isOdd(int a[]){
	    	int sum=0;
	    	for(int i=0;i<total;i++)
	    		for(int j=i+1;j<total;j++)
	    		{
	    			if(a[i]>a[j])
	    				sum++;
	    		}
	    	if(sum%2==0)
	    		return true;
	    	else
	    		return false;
	    	
	 }
	 
	 public void remove(PPButton clicked){
			int rowN=button[nullButton].getRow();//�õ��հװ�ť������
			int colN=button[nullButton].getCol();//�õ��հװ�ť������
			int rowC=clicked.getRow();//�õ������ť������
			int colC=clicked.getCol();//�õ������ť������
			if(((rowN-rowC)==1&&(colN-colC)==0)||
			   ((rowN-rowC)==-1&&(colN-colC)==0)||
			   ((rowN-rowC)==0&&(colN-colC)==1)||
			   ((rowN-rowC)==0&&(colN-colC)==-1))
			{
				Image img= clicked.getImage();//�õ������ť��ͼƬ
				button[nullButton].setImage(img);//���ÿհװ�ť��ͼƬ���������հװ�ť������ť��ͼƬ
				clicked.setImage(null);//���õ����ťͼƬΪ�հ�
			    int click = rowC*pattern + colC;//��õ����ť�ǵڼ�����ť
			    order[nullButton] = order[click];//����ͼƬ�����˳��״̬
			    order[click] = total - 1;
			    nullButton=click;//���ÿհװ�ť�ǵڼ���
			    check();
			}
			else
			{
				return;
			}
	}
	 
	//�ж�ƴͼ�Ƿ����
	public boolean check(){
		int i;
		for(i=0;i<total;i++)
			if(order[i]!=i)
				return false;
			
		if(i == total)
			return true;
		return true;
		
	}
	
	public void setListener(boolean flag)
	{
		MouseAdapter ma = new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				PPButton button=(PPButton)e.getSource();
				remove(button);
			}
    	};
		
		for(int i=0;i<total;i++){   
	    	//��ÿһ����ť��Ӽ����¼�
			
			
	    	if(flag == true)
	    		button[i].addMouseListener(ma);
	    	else {
	    		MouseListener[] ml = button[i].getMouseListeners();
	    		for(MouseListener l : ml)
	    			button[i].removeMouseListener(l);
	    	}
	    	
	    }
	}
}
