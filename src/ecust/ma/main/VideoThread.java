package ecust.ma.main;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import ecust.ma.tool.Tool;

public class VideoThread extends Thread{
	private String videoPath;//��Ƶ�ļ�·��
	public JLabel player;
	public JLabel labelInfo;
	
	public void setVideoPath(String videoPath){
		this.videoPath=videoPath;
	}
	
	public String getVideoPath(){
		return this.videoPath;
	}
	
	public void run(){
		VideoCapture capture=new VideoCapture(videoPath);
		if(!capture.isOpened()){
			System.out.println("����Ƶ����");
		}else{
			Mat image=new Mat();
			capture.read(image);
			while(true){
				capture.read(image);
				if(!image.empty()){
					Imgproc.resize(image, image, new Size(600,450));
					BufferedImage bfImage=Tool.matToBufferedImage(image);
					player.setIcon(new ImageIcon(bfImage));
					labelInfo.setText(""+capture.get(1));
					try {
						Thread.sleep(20);//ֹͣ40ms��֡�ʵĵ���
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					System.out.println("��Ƶ��ȡ����");
					capture.release();
					break;
				}
			}
		}
	}
}
