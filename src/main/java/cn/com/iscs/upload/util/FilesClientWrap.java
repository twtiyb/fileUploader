package cn.com.iscs.upload.util;

import com.mosso.client.cloudfiles.FilesClient;
import org.apache.http.HttpException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * Created by xuchun on 16/10/24.
 */
@Component
public class FilesClientWrap extends FilesClient {
	public FilesClientWrap() {
		super("testuser:swift", "tthksqNaJdS6RvNaOh9p5aTRu5qkMDCLITi7iYFM", "", "http://192.168.6.93/auth/",28800000);
		try {
			super.login();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		}
	}

	@Scheduled(fixedRate = 3600 * 4 * 1000)
//	@Scheduled(fixedRate = 5000)
	public void reLogin(){
		try {
			super.login();
			System.out.println("relogin" + new Date().toGMTString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		}
	}
}
