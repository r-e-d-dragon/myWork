package com.enjoygolf24.api.common.properties;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:authority.properties")
@ConfigurationProperties
public class AuthorytyProperty {

	@Value("${public.menu.access}")
	private String publicMenuAccess;

	@Value("${member.menu.access}")
	private String customerMenuAccess;

	@Value("${instructor.menu.access}")
	private String instructorMenuAccess;

	@Value("${admin.menu.access}")
	private String adminMenuAccess;

	public List<String> publicMenus() {
		return Arrays.asList(publicMenuAccess.split(","));
	}

	public List<String> loginUserMenus() {
		return Arrays.asList(customerMenuAccess.split(","));
	}

	public List<String> instructorMenus() {
		return Arrays.asList(instructorMenuAccess.split(","));
	}

	public List<String> adminPublicMenus() {
		return Arrays.asList(adminMenuAccess.split(","));
	}
}
