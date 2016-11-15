package cn.com.iscs.upload.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by xuchun on 16/11/14.
 */
@ConfigurationProperties(prefix = "filesClient")
public class FilesClientConfig {
	String username;
	String password;
	String account;
	String authenticationURL;
	String connectionTimeOut;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAuthenticationURL() {
		return authenticationURL;
	}

	public void setAuthenticationURL(String authenticationURL) {
		this.authenticationURL = authenticationURL;
	}

	public String getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public void setConnectionTimeOut(String connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}
}
