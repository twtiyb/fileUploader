package cn.com.iscs.upload;

/**
 * Created by xuchun on 16/10/21.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableConfigurationProperties
@RestController
@EnableScheduling
public class Application {
	final Logger logger = LoggerFactory.getLogger(Application.class);

	@Value("${app.name}")
	private String appName;

	@Value("${filesClient.authenticationURL}")
	private String authenticationURL;

	@RequestMapping("/myenv")
	public String env(@RequestParam("prop") String prop){
		return "tiantian";
	}

	@RequestMapping("/")
	public String welcome(){
		return "tiantian";
	}


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
