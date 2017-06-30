package ecust.ma.tool;

import java.awt.image.BufferedImage;

import org.opencv.core.Mat;

public class Tool {
	/**
	 * ͼ���ʽת��������
	 * Opencv��Matת����Swing��BufferedImage�ķ���
	 * @param matrix
	 * @return
	 */
	public static BufferedImage matToBufferedImage(Mat matrix) {
		int cols = matrix.cols();
		int rows = matrix.rows();
		int elemSize = (int) matrix.elemSize();
		byte[] data = new byte[cols * rows * elemSize];
		int type;
		matrix.get(0, 0, data);
		switch (matrix.channels()) {
		case 1:
			type = BufferedImage.TYPE_BYTE_GRAY;
			break;
		case 3:
			type = BufferedImage.TYPE_3BYTE_BGR;
			// bgr to rgb
			byte b;
			for (int i = 0; i < data.length; i = i + 3) {
				b = data[i];
				data[i] = data[i + 2];
				data[i + 2] = b;
			}
			break;
		default:
			return null;
		}
		BufferedImage image2 = new BufferedImage(cols, rows, type);
		image2.getRaster().setDataElements(0, 0, cols, rows, data);
		return image2;
	}
	
	/**
	 * �ļ�·��ת��������
	 * ���ļ�·���е�\ת��Ϊ//
	 * @param srcPath
	 * @return
	 */
	public static String convertPath(String srcPath){
		String resultPath="";
		String[] pathArray=srcPath.split("\\\\");//Ҫ��\Ϊ�ָ�������Ҫд4��\
		for(int i=0;i<pathArray.length;i++){
			if(i==0){
				resultPath=pathArray[i];
			}else{
				resultPath=resultPath+"//"+pathArray[i];
			}
		}
		return resultPath;
	}
}
