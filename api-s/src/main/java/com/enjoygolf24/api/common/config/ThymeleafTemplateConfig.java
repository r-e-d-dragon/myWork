package com.enjoygolf24.api.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * <p>
 * クラス名：<br>
 * PDF出力のため環境設定を行う。<br>
 * <br>
 * 機能説明：<br>
 * SpringMVCの環境のPDFソースローダーを設定する。<br>
 * SpringMVCの環境にBeanの定義を行う。<br>
 * <br>
 * </p>
 *
 * @since 2018/10
 */
@Configuration
public class ThymeleafTemplateConfig {

	/**
	 * PDF生成HTMLソースをロードする。
	 *
	 * @return テンプレートロード
	 */
	@Bean
	public ClassLoaderTemplateResolver pdfTemplateResolver() {
		ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
		emailTemplateResolver.setPrefix("templates/");
		emailTemplateResolver.setTemplateMode("HTML5");
		emailTemplateResolver.setSuffix(".html");
		emailTemplateResolver.setTemplateMode("XHTML");
		emailTemplateResolver.setCharacterEncoding("UTF-8");
		emailTemplateResolver.setOrder(1);
		return emailTemplateResolver;
	}
}
