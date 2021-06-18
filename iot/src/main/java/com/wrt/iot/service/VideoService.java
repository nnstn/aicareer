package com.wrt.iot.service;

import com.github.xiaoymin.knife4j.core.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.*;

import javax.swing.*;

/**
 * @author : wangjn_bj
 * @date : 2021/6/14 15:48
 */
@Slf4j
public class VideoService {

    private void prepare(){
        long start = System.currentTimeMillis();
        Loader.load(opencv_objdetect.class);
        long cost = System.currentTimeMillis()-start;
        System.out.println("加载opencv系统准备时间:"+cost);
    }

    public static void getLocalCamera(String inputVideoRMI){
        try {
            FrameGrabber grabber = null;
            if (StringUtils.isBlank(inputVideoRMI)){
                //本机摄像头默认0
                grabber = FrameGrabber.createDefault(0);
            }else {
                //网络读取视频流地址
                grabber = FFmpegFrameGrabber.createDefault(inputVideoRMI);
            }

            grabber.start();//开启抓取器
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
    public static void recordPush(String inputFile,String outputFile,int v_rs) throws Exception, org.bytedeco.javacv.FrameRecorder.Exception, InterruptedException{
        Loader.load(opencv_objdetect.class);
        long startTime=0;
        FrameGrabber grabber = FrameGrabber.createDefault(0);//本机摄像头默认0
//        FrameGrabber grabber = FFmpegFrameGrabber.createDefault(inputFile);
        try {
            grabber.start();//开启抓取器
        } catch (Exception e) {
            try {
                grabber.restart();
            } catch (Exception e1) {
                throw e;
            }
        }

        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        Frame grabframe =grabber.grab();
        opencv_core.IplImage grabbedImage =null;
        if(grabframe!=null){
            System.out.println("取到第一帧");
            grabbedImage = converter.convert(grabframe);
        }else{
            System.out.println("沒有取到第一帧");
        }
//如果想要保存图片,可以使用 opencv_imgcodecs.cvSaveImage("hello.jpg", grabbedImage);來保存图片
        FrameRecorder recorder;
        try {
            recorder = FrameRecorder.createDefault(outputFile, 1280, 720);
        } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
            throw e;
        }
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // avcodec.AV_CODEC_ID_H264
        recorder.setFormat("flv");
        recorder.setFrameRate(v_rs);
        recorder.setGopSize(v_rs);
        System.out.println("准备开始推流...");
        try {
            recorder.start();
        } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
            try {
                System.out.println("录制器启动失败，正在重新启动...");
                if(recorder!=null)
                {
                    System.out.println("尝试关闭录制器");
                    recorder.stop();
                    System.out.println("尝试重新启动录制器");
                    recorder.start();
                }

            } catch (org.bytedeco.javacv.FrameRecorder.Exception e1) {
                throw e;
            }
        }
        System.out.println("开始推流");
        CanvasFrame frame = new CanvasFrame("camera", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        while (frame.isVisible() && (grabframe=grabber.grab()) != null) {
            System.out.println("推流...");
            frame.showImage(grabframe);
            grabbedImage = converter.convert(grabframe);
            Frame rotatedFrame = converter.convert(grabbedImage);

            if (startTime == 0) {
                startTime = System.currentTimeMillis();
            }
            recorder.setTimestamp(1000 * (System.currentTimeMillis() - startTime));//時間戳
            if(rotatedFrame!=null){
                recorder.record(rotatedFrame);
            }

            Thread.sleep(40);
        }
        frame.dispose();
        recorder.stop();
        recorder.release();
        grabber.stop();
        System.exit(2);
    }

    public static void main(String[] args) {
        String inputVideoRMI = "rtsp://admin:ALHLII@172.21.147.248:554/h264/ch1/main/av_stream";
        getLocalCamera(inputVideoRMI);
    }
}
