package com.enjoygolf24.api.common.utility.pdf;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.util.StringUtils;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.FSEntityResolver;

import com.lowagie.text.pdf.BaseFont;

@Component
public class PdfGenaratorUtil {

	/** 請求書テンプレート */
	private static final String TEMPLATE_BILL = "/pdf/TEST";

	/** ファイル名用日付フォーマッター */
	private static final SimpleDateFormat FILE_NAME_DATE_PART = new SimpleDateFormat("yyyyMMddHHmm");

	/** ロガー */
	private static final Logger logger = LoggerFactory.getLogger(PdfGenaratorUtil.class);

	/** PDFテンプレートエンジン */
	@Autowired
	private TemplateEngine templateEngine;

	/** リソースローダー */
	@Autowired
	ResourceLoader resourceLoader;

	/** PDFページングアシスタント */
	@Autowired
	PdfPagingAssistent pdfAssistent;

	/**
	 * PDFファイルを生成する。
	 * 
	 * @param templateName テンプレート名
	 * @param response     レスポンス
	 * @param id           注文ID
	 * @return FileOutputStream
	 */
	private HttpServletResponse createPdf(String templateName, HttpServletResponse response, Long id) {
		return createPdf(templateName, response, null, null, id);
	}

	/**
	 * PDFファイルを生成する。
	 * 
	 * @param templateName テンプレート名
	 * @param response     レスポンス
	 * @param estimationNo 見積書管理番号
	 * @param cart         ショッピングカート
	 * @param id           注文ID
	 * @return FileOutputStream
	 */
	private HttpServletResponse createPdf(String templateName, HttpServletResponse response, String estimationNo,
	        Object cart, Long id) {
		logger.debug("Make PDF Start!");
		OutputStream os = null;
		try {

//        	String fomallyEstimationPath = null;
//            if (SAVE_PDF_TEMPLATES.contains(templateName)) {
//                fomallyEstimationPath = environmentValue.getFormallyEstimationSavePath() + "/" + estimationNo + ".pdf";
//                os = new FileOutputStream(fomallyEstimationPath);
//            } else {
//                if (response != null) {
//                    os = response.getOutputStream();
//                }
//            }

			String fileName = buildPdfFileName(templateName, estimationNo);
			if (response != null) {
				response.setContentType("application/octet-stream");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			}

			Assert.notNull(templateName, "The templateName can not be null");

			String processedHtml = new String(
			        templateEngine.process(templateName, pdfAssistent.getContext(1)).getBytes("UTF-8"), "UTF-8");

			final ITextRenderer renderer = new ITextRenderer();
			final ITextFontResolver fontResolver = renderer.getFontResolver();

			Resource resource = resourceLoader.getResource("classpath:");
			File dir = resource.getFile();
			String path = dir.getPath();

			renderer.getSharedContext().setReplacedElementFactory(
			        new MediaReplacedElementFactory(renderer.getSharedContext().getReplacedElementFactory(), path));

			String dirPath = "/font/ipam.ttf";

			fontResolver.addFont(dirPath, BaseFont.IDENTITY_H, true);
			logger.debug("addFont");
			Document document = getDocument(processedHtml);
			renderer.setDocument(document, null);
			logger.debug("setDocument");
			renderer.layout();
			logger.debug("layout");
			renderer.createPDF(os, false);

			if (pdfAssistent.isMultiPages()) {
				for (int i = 2; i <= pdfAssistent.getTotalPage(); i++) {
					String processedHtmlPage = new String(
					        templateEngine.process(templateName, pdfAssistent.getContext(i)).getBytes("UTF-8"),
					        "UTF-8");
					Document documentPage = getDocument(processedHtmlPage);

					renderer.getSharedContext().setReplacedElementFactory(new MediaReplacedElementFactory(
					        renderer.getSharedContext().getReplacedElementFactory(), path));

					renderer.setDocument(documentPage, null);
					renderer.layout();
					renderer.writeNextDocument();
				}
			}

			PdfPagingAssistent pdfAssistentPreview = new PdfPagingAssistent();

			String processedHtmlPreview = new String(
			        templateEngine.process("", pdfAssistentPreview.getContext(1)).getBytes("UTF-8"), "UTF-8");

			Document documentPreview = getDocument(processedHtmlPreview);
			renderer.getSharedContext().setReplacedElementFactory(
			        new MediaReplacedElementFactory(renderer.getSharedContext().getReplacedElementFactory(), path));
			dirPath = "/font/ipaexg.ttf";
			fontResolver.addFont(dirPath, BaseFont.IDENTITY_H, true);
			renderer.setDocument(documentPreview, null);
			renderer.layout();
			renderer.writeNextDocument();

			if (pdfAssistentPreview.isMultiPages()) {
				for (int i = 2; i <= pdfAssistentPreview.getTotalPage(); i++) {
					String processedHtmlPreviewPage = new String(
					        templateEngine.process("", pdfAssistentPreview.getContext(i)).getBytes("UTF-8"), "UTF-8");
					Document documentPage = getDocument(processedHtmlPreviewPage);

					renderer.getSharedContext().setReplacedElementFactory(new MediaReplacedElementFactory(
					        renderer.getSharedContext().getReplacedElementFactory(), path));

					renderer.setDocument(documentPage, null);
					renderer.layout();
					renderer.writeNextDocument();
				}
			}

			logger.debug("createPDF");
			renderer.finishPDF();

//            if (fomallyEstimationPath != null && response != null) {
//                responseDownloadFile(response, new File(fomallyEstimationPath));
//            }

			logger.debug("Make PDF End!");
			return response;

		} catch (Exception ex) {
			logger.error("failed! {}", ex);
			return null;
		} finally {
			if (os != null) {
				try {
					os.flush();
				} catch (IOException e) {
					logger.error("failed! {}", e);
				}
			}
		}
	}

	/**
	 * PDFファイル名生成
	 *
	 * @param templateName テンプレート名
	 * @param estimationNo 見積書管理番号
	 * @return PDFファイル名
	 */
	private String buildPdfFileName(String templateName, String estimationNo) {
		StringBuilder sb = new StringBuilder();
		sb.append(templateName.substring(templateName.lastIndexOf("/") + 1));
		sb.append("_");
		Calendar c = Calendar.getInstance();
		sb.append(FILE_NAME_DATE_PART.format(c.getTime()));
		if (!StringUtils.isEmptyOrWhitespace(estimationNo)) {
			sb.append("_");
			// estimationNo : YYYYMMDDnnnnnn
			sb.append(estimationNo.substring(8, estimationNo.length()));
		}
		sb.append(".pdf");
		return sb.toString();
	}

	/**
	 * DomをParse
	 * 
	 * @param htmlContent HTML本文
	 * @return Document
	 * @throws Exception エラー
	 */
	private Document getDocument(String htmlContent) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);
		factory.setValidating(false);
		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setEntityResolver(FSEntityResolver.instance());
		return builder.parse(new ByteArrayInputStream(htmlContent.getBytes("UTF-8")));
	}

	/**
	 * ファイルダウンロード
	 *
	 * @param httpServletResponse レスポンス
	 * @param file                ファイル
	 */
	private void responseDownloadFile(HttpServletResponse httpServletResponse, File file) {
		try (InputStream is = new FileInputStream(file); OutputStream os = httpServletResponse.getOutputStream();) {

			byte[] fileByteArray = IOUtils.toByteArray(is);

			httpServletResponse.setContentType("application/octet-stream");
			httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
			httpServletResponse.setContentLength(fileByteArray.length);

			os.write(fileByteArray);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}