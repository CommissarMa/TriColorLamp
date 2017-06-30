package ecust.ma.tool;

import java.awt.image.BufferedImage;

import org.opencv.core.Mat;

public class Tool {
	/**
	 * 图像格式转换函数：
	 * Opencv中Mat转换成Swing中BufferedImage的方法
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
	 * 文件路径转换函数：
	 * 将文件路径中的\转换为//
	 * @param srcPath
	 * @return
	 */
	public static String convertPath(String srcPath){
		String resultPath="";
		String[] pathArray=srcPath.split("\\\\");//要以\为分隔符，需要写4个\
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
