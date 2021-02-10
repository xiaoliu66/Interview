package com.baidu;

import com.baidu.utils.StringToVoiceUtil;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test3 {

    @Test
    public void test() {
        ActiveXComponent ax = null;
        String str="BigDecimal 中的除法如果不指定保留方式会发生什么？";
        try { ax = new ActiveXComponent("Sapi.SpVoice");

            //运行时输出语音内容
            Dispatch spVoice = ax.getObject();
            // 音量 0-100
            ax.setProperty("Volume", new Variant(100));
            // 语音朗读速度 -10 到 +10
            ax.setProperty("Rate", new Variant(-2));
            // 执行朗读
            //Dispatch.call(spVoice, "Speak", new Variant(str));

            //下面是构建文件流把生成语音文件

            ax = new ActiveXComponent("Sapi.SpFileStream");
            Dispatch spFileStream = ax.getObject();

            ax = new ActiveXComponent("Sapi.SpAudioFormat");
            Dispatch spAudioFormat = ax.getObject();

            //设置音频流格式
            Dispatch.put(spAudioFormat, "Type", new Variant(22));
            //设置文件输出流格式
            Dispatch.putRef(spFileStream, "Format", spAudioFormat);
            //调用输出 文件流打开方法，创建一个.wav文件
            Dispatch.call(spFileStream, "Open", new Variant("D:\\badao.wav"), new Variant(3), new Variant(true));
            //设置声音对象的音频输出流为输出文件对象
            Dispatch.putRef(spVoice, "AudioOutputStream", spFileStream);
            //设置音量 0到100
            Dispatch.put(spVoice, "Volume", new Variant(100));
            //设置朗读速度
            Dispatch.put(spVoice, "Rate", new Variant(-2));
            //开始朗读
            Dispatch.call(spVoice, "Speak", new Variant(str));

            //关闭输出文件
            Dispatch.call(spFileStream, "Close");
            Dispatch.putRef(spVoice, "AudioOutputStream", null);

            spAudioFormat.safeRelease();
            spFileStream.safeRelease();
            spVoice.safeRelease();
            ax.safeRelease();
            System.out.println("语音合成成功");

        } catch (Exception e) { e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        StringToVoiceUtil.String2Voice("string");
        //System.out.println(System.getProperty("user.dir") + "\\src\\main\\resources\\data\\");
    }
}
