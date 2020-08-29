
package com.enjoygolf24.api.common.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceResolver;
import org.springframework.mobile.device.LiteDeviceResolver;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.enjoygolf24.api.common.error.CustomBindResult;
import com.enjoygolf24.api.common.utility.FormFieldUtility;
import com.enjoygolf24.api.common.utility.URICheckUtility;

@Component
public class ControllerInterceptor extends HandlerInterceptorAdapter {

	/** ロガー */
	private static final Logger logger = LoggerFactory.getLogger(ControllerInterceptor.class);

	/** メッセージソース */
	@Autowired
	MessageSource messageSource;

	/** DeviceResolver */
	private final DeviceResolver deviceResolver = new LiteDeviceResolver();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		// logger.info("================ Before Method preHandle");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) {

		if (URICheckUtility.isNotCheckedActionUrl(request) || modelAndView == null) {
			return;
		}

		CustomBindResult newBindResult = new CustomBindResult();

		Map<String, Object> modelMap = modelAndView.getModel();
		String formName = null;
		BindingResult result = null;
		for (String modelKey : modelMap.keySet()) {
			Object object = modelMap.get(modelKey);
			if (object instanceof BindingResult) {
				BindingResult tempResult = (BindingResult) object;
				if (tempResult.hasErrors()) {
					result = tempResult;
					formName = modelKey.substring(modelKey.lastIndexOf('.') + 1);
					break;
				}
			}
		}

		// 例外が発生したフォームがない場合は処理を中断する。
		if (formName == null) {
			modelAndView.addObject("customError", newBindResult);
			return;
		}

		logger.info("Form Name :" + formName);
		logger.info(BindingResult.MODEL_KEY_PREFIX + formName);
		Object formObject = modelMap.get(formName);

		Field[] fields = formObject.getClass().getDeclaredFields();
		Method[] methods = formObject.getClass().getDeclaredMethods();

		if (!FormFieldUtility.exist(formName)) {
			FormFieldUtility.init(formName, fields, methods);
		}

		for (ObjectError error : result.getAllErrors()) {
			int orderNo = 0;
			String[] codes = null;
			String fieldName = null;
			String message = null;
			Object[] arguments = null;

			if (error instanceof FieldError) {
				FieldError fieldError = (FieldError) error;
				fieldName = fieldError.getField();
				orderNo = FormFieldUtility.getOrder(formName, fieldName);
			} else {
				fieldName = error.getObjectName();
				orderNo = -1;
			}

			String fieldNameJpn = "";
			try {
				fieldNameJpn = messageSource.getMessage(fieldName, null, Locale.JAPAN);
			} catch (Exception e) { // nothing
				logger.info("message.propertiesにフィールド名「" + fieldName + "」に対する日本語名称が設定されていません。");
			}

			codes = error.getCodes();
			arguments = error.getArguments();
			message = error.getDefaultMessage();
			newBindResult.addError(orderNo, fieldName, fieldNameJpn, message, arguments, codes);
		}

		modelAndView.addObject("customError", newBindResult);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		// logger.info("================ Method Completed afterCompletion");
	}

	/**
	 * ログを書かないかをチェックする
	 * 
	 * @param uri URI
	 * @return boolean true:ログを書く false:ログを書かない
	 */
	private boolean isNotChecked(String uri) {
		return uri.matches(".*\\.(css|js|jpg|jpeg|bmp|png|gif|jquery.min.map)+$|.*/img/.*|.*/webfonts/.*");
	}

	/**
	 * 環境をチェックする。
	 * 
	 * @param httpRequest request
	 * @param device      device
	 *
	 * @return true/false
	 */
	private boolean isMobile(HttpServletRequest httpRequest, Device device) {

		if (device.isMobile()) {
			return true;
		} else if (device.isTablet()) {
			return true;
		} else {
			return false;
		}
	}
}
