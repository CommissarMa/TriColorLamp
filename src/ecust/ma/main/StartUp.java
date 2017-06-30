package ecust.ma.main;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import ecust.ma.tool.Tool;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class StartUp {

	private JFrame frame;//����
	private JLabel player;//��Ƶ������
	private JLabel labelInfo;
	
	static{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);//����Opencv��
	}

	/**
	 * ��������
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartUp window = new StartUp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * ���캯��
	 */
	public StartUp() {
		initialize();
	}

	/**
	 * ��ʼ�����弰������
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setTitle("��ɫ�Ƽ�����");//����
		frame.setResizable(false);//���ɸı䴰���С
		frame.setBounds(100, 100, 800, 600);//���ô���λ�úʹ�С
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();//�˵���
		menuBar.setBounds(0, 0, 800, 25);//λ�úʹ�С
		frame.getContentPane().add(menuBar);
		
		JMenu menuOption = new JMenu("ѡ��");//�˵�
		menuBar.add(menuOption);//���˵�����˵���
		
		JMenuItem menuItemOpenVideo = new JMenuItem("����Ƶ");//�˵���
		menuItemOpenVideo.addActionListener(new ActionListener() {//�������Ƶ�˵���
			public void actionPerformed(ActionEvent e) {
				//����Ƶ
				openVideo();
			}
		});
		menuOption.add(menuItemOpenVideo);//���˵������˵�
		
		JMenuItem menuItemOpenCamera = new JMenuItem("�򿪼��");//�˵���
		menuItemOpenCamera.addActionListener(new ActionListener() {//����򿪼�ز˵���
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		menuOption.add(menuItemOpenCamera);//���˵������˵�
		
		JMenu menuOther = new JMenu("����");//�˵�
		menuBar.add(menuOther);
		
		JMenuItem menuItemAbout = new JMenuItem("���ڱ�����");//�˵���
		menuItemAbout.addActionListener(new ActionListener() {//������ڱ�����˵���
			public void actionPerformed(ActionEvent arg0) {
				//�������ڱ�����������Ϣ
				JOptionPane.showMessageDialog(frame, "Author: CommissarMa\nAt ECUST", "���ڱ�����", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		menuOther.add(menuItemAbout);
		
		player = new JLabel("");//��Ƶ������
		player.setIcon(new ImageIcon(StartUp.class.getResource("/ecust/ma/resource/playerbg.png")));
		player.setBounds(20, 75, 600, 450);
		frame.getContentPane().add(player);
		
		labelInfo = new JLabel("");
		labelInfo.setBounds(10, 35, 54, 15);
		frame.getContentPane().add(labelInfo);
	}
	
	/**
	 * ����Ƶ
	 */
	public void openVideo(){
		JFileChooser jfc=new JFileChooser();//�����ļ�ѡ��Ի������
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);//ֻ����ѡȡ�ļ�
		jfc.setCurrentDirectory(new File("D://"));//����Ĭ���ļ���
		FileNameExtensionFilter fnef=new FileNameExtensionFilter("MP4 & AVI Video","mp4","avi");//��չ��������
		jfc.setFileFilter(fnef);//�����ļ�������
		jfc.showDialog(frame, "ѡ����Ƶ�ļ�");//��ʾ�Ի���
		
		if(jfc.getSelectedFile()!=null){//���ѡ������Ƶ�ļ�
			String videoPath=Tool.convertPath(jfc.getSelectedFile().getAbsolutePath());
			System.out.println(videoPath);
			//������Ƶ
			VideoCapture capture=new VideoCapture(videoPath);
			if(!capture.isOpened()){
				System.out.println("����Ƶ����");
			}else{
				Mat image=new Mat();
				capture.read(image);
				if(!image.empty()){
					Imgproc.resize(image, image, new Size(600,450));
					BufferedImage bfImage=Tool.matToBufferedImage(image);
					player.setIcon(new ImageIcon(bfImage));
					capture.release();
				}
				//��Ƶ�����߳�
				VideoThread vt=new VideoThread();
				vt.setVideoPath(videoPath);
				vt.player=player;
				vt.labelInfo=labelInfo;
				vt.start();
			}
		}
		
	}
}
