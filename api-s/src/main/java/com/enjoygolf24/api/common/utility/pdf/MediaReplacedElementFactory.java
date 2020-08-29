package com.enjoygolf24.api.common.utility.pdf;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import com.lowagie.text.Image;

public class MediaReplacedElementFactory implements ReplacedElementFactory {

    /** イメージパス*/
    private String classPath;

    /** 置換Factory*/
    private final ReplacedElementFactory superFactory;

    /**
     * Constructor
     * @param superFactory 親クラス
     * @param classPath サーバクラスパス
     */
    public MediaReplacedElementFactory(ReplacedElementFactory superFactory, String classPath) {
        this.superFactory = superFactory;
        this.classPath = classPath + "/static/img/";
    }

    @Override
    public ReplacedElement createReplacedElement(LayoutContext layoutContext, BlockBox blockBox, UserAgentCallback userAgentCallback, int cssWidth,
            int cssHeight) {
        Element element = blockBox.getElement();
        if (element == null) {
            return null;
        }
        String nodeName = element.getNodeName();
        if ("img".equals(nodeName)) {
            if (!element.hasAttribute("src")) {
                throw new RuntimeException("An element is missing a `src` attribute indicating the media file.");
            }

            String imagePath = element.getAttribute("src");
            if (imagePath != null && !(imagePath.startsWith("http:") || imagePath.startsWith("https:") || imagePath.startsWith("localhost:"))) {
                InputStream input = null;
                try {
                    input = new FileInputStream(classPath + imagePath);
                    final byte[] bytes = IOUtils.toByteArray(input);
                    final Image image = Image.getInstance(bytes);
                    final FSImage fsImage = new ITextFSImage(image);
                    if (fsImage != null) {
                        if ((cssWidth != -1) || (cssHeight != -1)) {
                            fsImage.scale(cssWidth, cssHeight);
                        }
                        return new ITextImageElement(fsImage);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("There was a problem trying to read a template embedded graphic.", e);
                } finally {
                    try {
                        if (input != null) {
                            input.close();
                        }
                    } catch (IOException e) {

                    }
                }
            }
        }
        return this.superFactory.createReplacedElement(layoutContext, blockBox, userAgentCallback, cssWidth, cssHeight);
    }

    @Override
    public void reset() {
        this.superFactory.reset();
    }

    @Override
    public void remove(Element e) {
        this.superFactory.remove(e);
    }

    @Override
    public void setFormSubmissionListener(FormSubmissionListener listener) {
        this.superFactory.setFormSubmissionListener(listener);
    }

}
