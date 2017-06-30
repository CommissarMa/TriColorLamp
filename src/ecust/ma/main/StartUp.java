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

	private JFrame frame;//窗体
	private JLabel player;//视频播放器
	private JLabel labelInfo;
	
	static{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);//加载Opencv库
	}

	/**
	 * 启动程序
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
	 * 构造函数
	 */
	public StartUp() {
		initialize();
	}

	/**
	 * 初始化窗体及其内容
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setTitle("三色灯检测程序");//标题
		frame.setResizable(false);//不可改变窗体大小
		frame.setBounds(100, 100, 800, 600);//设置窗体位置和大小
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();//菜单栏
		menuBar.setBounds(0, 0, 800, 25);//位置和大小
		frame.getContentPane().add(menuBar);
		
		JMenu menuOption = new JMenu("选项");//菜单
		menuBar.add(menuOption);//将菜单加入菜单栏
		
		JMenuItem menuItemOpenVideo = new JMenuItem("打开视频");//菜单项
		menuItemOpenVideo.addActionListener(new ActionListener() {//点击打开视频菜单项
			public void actionPerformed(ActionEvent e) {
				//打开视频
				openVideo();
			}
		});
		menuOption.add(menuItemOpenVideo);//将菜单项加入菜单
		
		JMenuItem menuItemOpenCamera = new JMenuItem("打开监控");//菜单项
		menuItemOpenCamera.addActionListener(new ActionListener() {//点击打开监控菜单项
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		menuOption.add(menuItemOpenCamera);//将菜单项加入菜单
		
		JMenu menuOther = new JMenu("其他");//菜单
		menuBar.add(menuOther);
		
		JMenuItem menuItemAbout = new JMenuItem("关于本程序");//菜单项
		menuItemAbout.addActionListener(new ActionListener() {//点击关于本程序菜单项
			public void actionPerformed(ActionEvent arg0) {
				//弹出关于本程序的相关信息
				JOptionPane.showMessageDialog(frame, "Author: CommissarMa\nAt ECUST", "关于本程序", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		menuOther.add(menuItemAbout);
		
		player = new JLabel("");//视频播放器
		player.setIcon(new ImageIcon(StartUp.class.getResource("/ecust/ma/resource/playerbg.png")));
		player.setBounds(20, 75, 600, 450);
		frame.getContentPane().add(player);
		
		labelInfo = new JLabel("");
		labelInfo.setBounds(10, 35, 54, 15);
		frame.getContentPane().add(labelInfo);
	}
	
	/**
	 * 打开视频
	 */
	public void openVideo(){
		JFileChooser jfc=new JFileChooser();//创建文件选择对话框对象
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);//只允许选取文件
		jfc.setCurrentDirectory(new File("D://"));//设置默认文件夹
		FileNameExtensionFilter fnef=new FileNameExtensionFilter("MP4 & AVI Video","mp4","avi");//扩展名过滤器
		jfc.setFileFilter(fnef);//设置文件过滤器
		jfc.showDialog(frame, "选择视频文件");//显示对话框
		
		if(jfc.getSelectedFile()!=null){//如果选择了视频文件
			String videoPath=Tool.convertPath(jfc.getSelectedFile().getAbsolutePath());
			System.out.println(videoPath);
			//播放视频
			VideoCapture capture=new VideoCapture(videoPath);
			if(!capture.isOpened()){
				System.out.println("打开视频出错");
			}else{
				Mat image=new Mat();
				capture.read(image);
				if(!image.empty()){
					Imgproc.resize(image, image, new Size(600,450));
					BufferedImage bfImage=Tool.matToBufferedImage(image);
					player.setIcon(new ImageIcon(bfImage));
					capture.release();
				}
				//视频播放线程
				VideoThread vt=new VideoThread();
				vt.setVideoPath(videoPath);
				vt.player=player;
				vt.labelInfo=labelInfo;
				vt.start();
			}
		}
		
	}
}
