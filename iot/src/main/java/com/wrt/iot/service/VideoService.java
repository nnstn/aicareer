package com.wrt.iot.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.*;

import javax.swing.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;

/**
 * @author : wangjn_bj
 * @date : 2021/6/14 15:48
 */
@Slf4j
public class VideoService {

    public static void prepare(){
        long start = System.currentTimeMillis();
        Loader.load(opencv_objdetect.class);
        long cost = System.currentTimeMillis()-start;
        System.out.println("加载opencv系统准备时间:"+cost);
    }

    public static void getCamera(String inputVideoRMI){
        try {
            FrameGrabber grabber = null;
            if (StringUtils.isBlank(inputVideoRMI)){
                //本机摄像头默认0
                grabber = FrameGrabber.createDefault(0);
                //新建opencv抓取器，一般的电脑和移动端设备中摄像头默认序号是0，不排除其他情况
                //grabber = new OpenCVFrameGrabber(0);
            }else {
                //网络读取视频流地址
                grabber = FFmpegFrameGrabber.createDefault(inputVideoRMI);
            }

            grabber.start();//开启抓取器
          grabberOneFrame(grabber);// 获取一帧图片
            // 展示java客户端
            //cameraShow(grabber);


        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    public static void grabberOneFrame(FrameGrabber grabber){
        try {
            long start = System.currentTimeMillis();
            OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
            Frame grabframe =grabber.grab();
            opencv_core.IplImage grabbedImage =null;
            if(grabframe!=null){
                System.out.println("取到第一帧");
                grabbedImage = converter.convert(grabframe);
                //如果想要保存图片,可以使用
                cvSaveImage("/aicp/applications/rstp/"+System.currentTimeMillis()+".jpg", grabbedImage);
            }else{
                System.out.println("沒有取到第一帧");
            }
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static void cameraShow(FrameGrabber grabber){
        try {
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


    public static void recordPush(String outputFile,int v_rs) throws Exception, org.bytedeco.javacv.FrameRecorder.Exception, InterruptedException{
//
//        FrameRecorder recorder;
//        recorder = FrameRecorder.createDefault(outputFile, 1280, 720);
//        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // avcodec.AV_CODEC_ID_H264
//        recorder.setFormat("flv");
//        recorder.setFrameRate(v_rs);
//        recorder.setGopSize(v_rs);
//        System.out.println("准备开始推流...");
//        try {
//            recorder.start();
//        } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
//            try {
//                System.out.println("录制器启动失败，正在重新启动...");
//                if(recorder!=null)
//                {
//                    System.out.println("尝试关闭录制器");
//                    recorder.stop();
//                    System.out.println("尝试重新启动录制器");
//                    recorder.start();
//                }
//
//            } catch (org.bytedeco.javacv.FrameRecorder.Exception e1) {
//                throw e;
//            }
//        }
//        System.out.println("开始推流");
//        CanvasFrame frame = new CanvasFrame("camera", CanvasFrame.getDefaultGamma() / grabber.getGamma());
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setAlwaysOnTop(true);
//        while (frame.isVisible() && (grabframe=grabber.grab()) != null) {
//            System.out.println("推流...");
//            frame.showImage(grabframe);
//            grabbedImage = converter.convert(grabframe);
//            Frame rotatedFrame = converter.convert(grabbedImage);
//
//            if (startTime == 0) {
//                startTime = System.currentTimeMillis();
//            }
//            recorder.setTimestamp(1000 * (System.currentTimeMillis() - startTime));//時間戳
//            if(rotatedFrame!=null){
//                recorder.record(rotatedFrame);
//            }
//
//            Thread.sleep(40);
//        }
//        frame.dispose();
//        recorder.stop();
//        recorder.release();
//        grabber.stop();
//        System.exit(2);
    }

    public static void main(String[] args) {
        String inputVideoRMI = "rtsp://admin:ALHLII@172.21.147.248:554/h264/ch1/main/av_stream";
//      getCamera(inputVideoRMI);
        getCamera(null);
        //localCamera();
    }

    /**
     * 调用本地摄像头窗口视频
     */
    public static void localCamera(){
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
