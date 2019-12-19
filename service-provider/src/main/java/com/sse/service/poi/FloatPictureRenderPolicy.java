package com.sse.service.poi;

import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.policy.PictureRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObject;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;

/**
 * <p></p>
 * author pczhao<br/>
 * date  2019-12-10 16:32
 */

public class FloatPictureRenderPolicy extends AbstractRenderPolicy<PictureRenderData> {

    public static final int EMU = 9525;
    public static final int SEAL_LEFT_OFFSET = 326 * EMU;
    public static final int SEAL_WIDTH = 163 * EMU;
    public static final int SEAL_HEIGHT = 161 * EMU;

    @Override
    protected boolean validate(PictureRenderData data) {
        return (null != data && (null != data.getData() || null != data.getPath()));
    }

    @Override
    public void doRender(RenderContext<PictureRenderData> context) throws Exception {
        PictureRenderPolicy.Helper.renderPicture(context.getRun(), context.getData());
        System.out.println(context.getRun().getCTR().getDrawingList().size());
        changePictureToFloat(context.getRun(), PictureRenderPolicy.Helper.suggestFileType(".png"), SEAL_WIDTH, SEAL_HEIGHT, false);
    }

    @Override
    protected void afterRender(RenderContext<PictureRenderData> context) {
        clearPlaceholder(context, false);
    }

    @Override
    protected void reThrowException(RenderContext<PictureRenderData> context, Exception e) {
        logger.info("Render float picture " + context.getEleTemplate() + " error: {}", e.getMessage());
        context.getRun().setText(context.getData().getAltMeta(), 0);
    }

    /**
     * run 里面只有一个图片，将该图片属性从 inline 转换成 anchor
     */
    private static void changePictureToFloat(XWPFRun run, int id, int width, int height, boolean behind) {
        if (run.getCTR().sizeOfDrawingArray() < 1) {
            return;
        }
        CTDrawing drawing = run.getCTR().getDrawingArray(0);
        if (drawing.sizeOfInlineArray() < 1) {
            return;
        }
        CTGraphicalObject graphicalobject = drawing.getInlineArray(0).getGraphic();

        //拿到新插入的图片替换添加CTAnchor 设置浮动属性 删除inline属性
        CTAnchor anchor = getAnchorWithGraphic(graphicalobject, "测试图片描述",
                width, height,//图片大小
                SEAL_LEFT_OFFSET, 0, behind, id);//相对当前段落位置 需要计算段落已有内容的左偏移
        drawing.setAnchorArray(new CTAnchor[]{anchor});//添加浮动属性
        drawing.removeInline(0);//删除行内属性
    }

    /**
     * @param ctGraphicalObject 图片数据
     * @param picDesc           图片描述
     * @param width             宽
     * @param height            高
     * @param leftOffset        水平偏移 left
     * @param topOffset         垂直偏移 top
     * @param behind            文字上方，文字下方
     */
    public static CTAnchor getAnchorWithGraphic(CTGraphicalObject ctGraphicalObject,
                                                String picDesc, int width, int height,
                                                int leftOffset, int topOffset, boolean behind, int id) {
        String anchorXML =
                "<wp:anchor xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" "
                        + "simplePos=\"0\" relativeHeight=\"0\" behindDoc=\"" + ((behind) ? 1 : 0) + "\" locked=\"0\" layoutInCell=\"1\" allowOverlap=\"1\">"
                        + "<wp:simplePos x=\"0\" y=\"0\"/>"
                        + "<wp:positionH relativeFrom=\"column\">"
                        + "<wp:posOffset>" + leftOffset + "</wp:posOffset>"
                        + "</wp:positionH>"
                        + "<wp:positionV relativeFrom=\"paragraph\">"
                        + "<wp:posOffset>" + topOffset + "</wp:posOffset>" +
                        "</wp:positionV>"
                        + "<wp:extent cx=\"" + width + "\" cy=\"" + height + "\"/>"
                        + "<wp:effectExtent l=\"0\" t=\"0\" r=\"0\" b=\"0\"/>"
                        + "<wp:wrapNone/>"
                        + "<wp:docPr id=\""
                        + id
                        + "\" name=\"Drawing 0\" descr=\"" + picDesc + "\"/><wp:cNvGraphicFramePr/>"
                        + "</wp:anchor>";

        CTDrawing drawing = null;
        try {
            drawing = CTDrawing.Factory.parse(anchorXML);
        } catch (XmlException e) {
            e.printStackTrace();
        }
        if (drawing != null) {
            CTAnchor anchor = drawing.getAnchorArray(0);
            anchor.setGraphic(ctGraphicalObject);
            return anchor;
        }

        return null;
    }

}
