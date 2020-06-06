package cn.homework.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cn.homework.util.SwingConsole;
import cn.homework.util.button.ThumbButton;
import cn.homework.util.image.ImageView;
import cn.homework.util.layout.RowLayout;
import cn.homework.util.panel.BottomBar;
import cn.homework.util.panel.ChoicePanel;
import cn.homework.util.panel.MyPanel;


public class SelectPractice extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//��ʾ��ͼ����
	ImageView canvas = new ImageView();
	//Ԥ��ͼ
	MyPanel thumbBar = new MyPanel();
	// ͼ�갴ť��
	List<ThumbButton> buttons = new ArrayList<ThumbButton>(); //������е���ϰ�ز�
	// �Ѷ�ѡ��ҳ
	ChoicePanel choice = new ChoicePanel();
	// �ײ�ȷ��ȡ����
	BottomBar bottomBar = new BottomBar();
	
	private int curPage = 0; //��ǰҳ
	
	public SelectPractice(String title) {
		super(title);
		
		JPanel root = new JPanel();
		this.setContentPane(root);
		root.setLayout(new BorderLayout());
		
		MyPanel topBar = new MyPanel();
		topBar.setLayout(new BorderLayout());
		topBar.padding(10).preferredHeight(80);
		JButton prePage = new JButton("<");
		JButton nextPage = new JButton(">");
		prePage.addActionListener(new ActionListener(){  // Ϊ��һҳ��Ť����¼�
			@Override
			public void actionPerformed(ActionEvent e){
				if(curPage > 0) {
					curPage--;
					updateThumbBar();
				}
			}
		});
		nextPage.addActionListener(new ActionListener(){ // Ϊ��һҳ��Ť����¼�
			@Override
			public void actionPerformed(ActionEvent e){
				if(curPage < buttons.size() / 7) {
					curPage++;
					updateThumbBar();
				}
			}
		});
		
		thumbBar.setLayout(new RowLayout(10));
		thumbBar.padding(5).preferredHeight(80);
		topBar.add(prePage, BorderLayout.WEST);
		topBar.add(nextPage, BorderLayout.EAST);
		topBar.add(thumbBar, BorderLayout.CENTER);
		root.add(topBar, BorderLayout.PAGE_START);
		
		
		MyPanel main = new MyPanel();
		main.setLayout(new GridLayout(1, 2));
		main.padding(10);
		root.add(main, BorderLayout.CENTER);
		
		
		canvas.setBackground(Color.white);
        canvas.setScaleType(ImageView.FIT_CENTER_INSIDE);
        canvas.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        canvas.setPreferredSize(new Dimension(300, 200));
		
        
		main.add(canvas);
		main.add(choice);
		
		
		root.add(bottomBar, BorderLayout.PAGE_END);
		
		ThumbClickListener clickListener = new ThumbClickListener();
        // ���� practise �ļ����µ�ͼƬ ( �����ļ���ʽ )
        //File imageDir = new File("practise");
        //File[] imageFiles = imageDir.listFiles();
		
		File[] imageFiles = getAllIamgeFiles("practise");
        for(File f: imageFiles)
        {
            ThumbButton button = new ThumbButton(f);
            buttons.add(button);

            button.setPreferredSize(new Dimension(80,80));
            button.setBgColor(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(Color.white,3));
            button.addMouseListener( clickListener );
        }
        
        modifyBottomBar();
        updateThumbBar();
	}
	private class ThumbClickListener extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            // ������İ�ť
            ThumbButton source = (ThumbButton)e.getSource();

            // ����İ�ť����ʾ��ɫ�����߿�
            // ������ť����ɫ��ͨ�߿�
            for(ThumbButton button: buttons)
            {
                if(button == source)
                {
                    button.setBorder(BorderFactory.createLineBorder(new Color(0x1E9FFF),3));
                }
                else
                {
                    button.setBorder(BorderFactory.createLineBorder(Color.white,3));
                }
            }

            // �м����� ��ʾ��ͼ
            canvas.setImage( source.image );
        }

    }
	
	private void updateThumbBar() {
		thumbBar.removeAll();
		if(curPage == buttons.size() / 7) {
			for(int i = curPage*7; i < buttons.size(); i++) {
				thumbBar.add(buttons.get(i), "auto");
			}
		} else {
			for(int i = curPage*7; i < curPage*7 + 7; i++) {
				thumbBar.add(buttons.get(i), "auto");
			}
		}
		thumbBar.updateUI();
		thumbBar.repaint();
	}
	
	private void modifyBottomBar() {
		bottomBar.cancelButton.addActionListener(new ActionListener() {  //Ϊȡ����Ť���˻���������¼�

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
				PINTU window = new PINTU();
				
				SwingConsole.run(window.PINTU);
				
			}
			
		});
		
		bottomBar.confirmButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String difficult = choice.getValue();
				if(canvas.getImage() != null && difficult != null) {
					int pattern;
					if("��".equals(difficult))
						pattern = 3;
					else if("�е�".equals(difficult))
						pattern = 4;
					else
						pattern = 5;
					LIANXI_view lx = new LIANXI_view((BufferedImage)canvas.getImage(), pattern);
					SwingConsole.run(lx);
					dispose();
				}
			}
			
		});
	}
	
	public static File[] getAllIamgeFiles(String path) {
		File imageDir = new File(path);
        File[] imageFiles = imageDir.listFiles();
        return imageFiles;
	}
}
