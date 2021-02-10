package com.baidu.utils;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;


public class BaiDuAIUtils {
    //设置APPID/AK/SK
    public static final String APP_ID = "23491309";
    public static final String API_KEY = "GC1NOKPFznm2ZDxBBxLaUYG2";
    public static final String SECRET_KEY = "M6SnDdARcWAvoBScLbCwsMzUFzrYBlSC";

    public static AipSpeech getAipSpeech() {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        //mSystem.setProperty("aip.log4j.conf", "log4j.properties");

        return client;
    }

    /**
     * 根据文字生成语音文件
     * @param client
     * @param options
     * @param text
     */
    public static synchronized void generateVoiceFiles(AipSpeech client, HashMap<String, Object> options,String text) {
//        String path =System.getProperty("user.dir")+"\\src\\main\\resources\\data\\";
        TtsResponse res = client.synthesis(text, "zh", 1, options);
        byte[] data = res.getData();
        JSONObject res1 = res.getResult();
        if (data != null) {
            try {
                Util.writeBytesToFileSystem(data, FileUtils.path + text + ".mp3");
                System.out.println(new StringBuilder(FileUtils.path+text+".mp3").append("生成成功！"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (res1 != null) {
            System.out.println(res1.toString(2));
        }
    }
}
