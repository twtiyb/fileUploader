package cn.com.iscs.upload.util;

import com.mosso.client.cloudfiles.FilesClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Scheduled(fixedRate = 3600 * 4 * 1000)
//	@Scheduled(fixedRate = 5000)
	public void reLogin(){
		try {
			super.login();
			System.out.println("relogin" + new Date().toGMTString());
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
}
