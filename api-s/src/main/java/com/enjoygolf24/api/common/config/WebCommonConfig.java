package com.enjoygolf24.api.common.config;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.enjoygolf24.api.common.interceptor.ControllerInterceptor;

@Configuration
public class WebCommonConfig implements WebMvcConfigurer {

	@Autowired
	ControllerInterceptor interceptor;

	/** インターセプター */
	// @Autowired
	// LoginBeforeInterceptor interceptor;

	// @Autowired
	// CookeyInterceptor cookeyInterceptor;

	/**
	 * 休日サービス
	 */
	// @Autowired
	// CalendarService pCalendarService;

	/**
	 * 休日ユーティリティをBeanとして生成
	 * 
	 * @return 休日Bean
	 */
//    @Bean
//    public HolidayUtility holidayUtility() {
//        HolidayUtility holidayUtility = HolidayUtility.getinstance(pCalendarService);
//        return holidayUtility;
//    }

	/**
	 * インターセプターを設定する。
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor).addPathPatterns("/**")
				.excludePathPatterns(".**\\\\.(css|js|jpg|jpeg|bmp|png|gif|jquery.min.map)+$|.**/img/.**"); // "/login*//**",
		// .registry.addInterceptor(cookeyInterceptor).addPathPatterns("/**");
	}

	/**
	 * ValidationメッセージをUTF-8で設定できるようにする
	 */
	@Override
	public Validator getValidator() {
		return validator();
	}

	/**
	 * ValidateがDIされるように設定
	 * 
	 * @return ValidateFactoryBean
	 */
	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource());
		return validator;
	}

	/**
	 * Validateメッセージ関連環境設定
	 * 
	 * @return Validation Message Source
	 */
	private MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		// プロパティ名前やディレクトリも変更可能
		messageSource.setBasename("classpath:/ValidationMessages");
		// UTF-8に設定
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	/**
	 * BCryptPasswordEncoderをDIされるように設定する。
	 * 
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	/** データソース */
	@Autowired
	private DataSource dataSource;

	/**
	 * SQLセッションテンプレート
	 * 
	 * @throws Exception
	 * @return SqlSessionTemplate
	 */
	@Autowired
	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);

		ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());

		// MyBatis のコンフィグレーションファイル
		bean.setConfigLocation(resolver.getResource("classpath:config/mybatis.xml"));
		// MyBatis で使用する SQL ファイル群
		bean.setMapperLocations(resolver.getResources("classpath:sql/*.xml"));

		return new SqlSessionTemplate(bean.getObject());
	}

	/**
	 * バリデータ
	 * 
	 * @return LocalValidatorFactoryBean
	 */
	@Bean
	public javax.validation.Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

}
