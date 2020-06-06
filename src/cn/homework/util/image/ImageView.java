package cn.homework.util.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JPanel;

/**
 * ͼƬʵ�ֿռ�
 * @author huihui3
 *
 */
public class ImageView extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int FIT_XY = 0;
    public static final int FIT_CENTER = 1;
    public static final int FIT_CENTER_INSIDE = 2;

    private Image image;
    private int scaleType = FIT_CENTER;
    private Color bgColor = Color.WHITE;

    public ImageView()
    {
        this.setOpaque(false);
    }

    public Image getImage()
    {
        return image;
    }

    public void setImage(Image image)
    {
        this.image = image;
        repaint();
    }

    public int getScaleType()
    {
        return scaleType;
    }

    public void setScaleType(int scaleType)
    {
        this.scaleType = scaleType;
        repaint();
    }

    public Color getBgColor()
    {
        return bgColor;
    }

    public void setBgColor(Color bgColor)
    {
        this.bgColor = bgColor;
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g)
    {
        // �ȵ��ø���� paintComponent(), ���Ʊ�Ҫ�ı߿�ͱ���
        super.paintComponent(g);

        // ���ؼ��Ŀ�Ⱥ͸߶�
        int width = this.getWidth();
        int height = this.getHeight();

        // �����ʾ
        //g.clearRect(0, 0, width, height);

        // ����ɫ
        g.setColor(bgColor);
        g.fillRect(0, 0, width,height);

        /////////////////////////
        if(image != null)
        {
            int imgW = image.getWidth(null);
            int imgH = image.getHeight(null);

            // ʹ��  AfImageScaler ������
            ImageScaler scaler = new ImageScaler(imgW, imgH, width,height);

            // �����������ͣ�������Ŀ������
            Rectangle fit = scaler.fitXY();
            if(scaleType == FIT_CENTER)
                fit = scaler.fitCenter();
            else if(scaleType == FIT_CENTER_INSIDE)
                fit = scaler.fitCenterInside();

            // ����
            g.drawImage(image, fit.x, fit.y, fit.width, fit.height,	null);
        }
    }

}
