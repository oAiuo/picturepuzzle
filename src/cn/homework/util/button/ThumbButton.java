package cn.homework.util.button;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cn.homework.util.image.ImageView;
/**
 * ��ϰģʽ��ѡ��ͼƬ��Thumbnail
 * @author huihui3
 *
 */
public class ThumbButton extends ImageView{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Image image;
	
	public ThumbButton(File file) {
		try {
			image = ImageIO.read(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		setImage(image);
		setScaleType(ImageView.FIT_CENTER);
	}
	

	
}
