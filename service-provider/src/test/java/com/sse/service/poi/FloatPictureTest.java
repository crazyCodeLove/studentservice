package com.sse.service.poi;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.PictureRenderData;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 * author pczhao<br/>
 * date  2019-12-10 15:58
 */

public class FloatPictureTest {


    @Test
    public void simpleTest() throws IOException {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                // 本地图片
                put("localPicture", new PictureRenderData(120, 120, "src/test/resources/pic/sayi.png"));
                // 图片流文件
                put("localBytePicture", new PictureRenderData(100, 120, ".png",
                        new FileInputStream("src/test/resources/pic/logo.png")));

                put("floatPicture", new PictureRenderData(120, 120, "src/test/resources/pic/float.png"));

                PictureRenderData pictureRenderData = new PictureRenderData(120, 120,
                        "src/test/resources/pic/sayi12.png");
                pictureRenderData.setAltMeta("图片不存在");
                put("image", pictureRenderData);
            }
        };

        Configure configure = Configure.newBuilder().buildGramer("${", "}").addPlugin('¯', new FloatPictureRenderPolicy()).build();

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/docx/picture.docx", configure)
                .render(datas);

        FileOutputStream out = new FileOutputStream("src/test/resources/docx/out_picture.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }
}
