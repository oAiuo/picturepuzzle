package cn.homework.util.button;


import cn.homework.util.image.ImageView;

public class PPButton extends ImageView{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		int row; //��Ť�ĺ�����
		int col;  // ��Ť��������
		public void setRow(int row){
			this.row=row;
		}
		public int getRow(){
			return row;
		}
		public void setCol(int col){
			this.col=col;
		}
		public int getCol(){
			return col;
		}
}
