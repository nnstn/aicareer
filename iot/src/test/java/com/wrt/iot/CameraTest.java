package com.wrt.iot;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import javax.swing.*;

/**
 * @author : wangjn_bj
 * @date : 2021/6/18 14:34
 */
public class CameraTest {


    public static void main(String [] args){
        try {
            //新建opencv抓取器，一般的电脑和移动端设备中摄像头默认序号是0，不排除其他情况
            OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
            grabber.start();//开始获取摄像头数据
            //新建一个预览窗口
            CanvasFrame canvas = new CanvasFrame("摄像头预览");
            canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //窗口是否关闭
            while(canvas.isDisplayable()){
                /*获取摄像头图像并在窗口中显示,这里Frame frame=grabber.grab()得到是解码后的视频图像*/
                canvas.showImage(grabber.grab());
            }
            grabber.close();//停止抓取
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
