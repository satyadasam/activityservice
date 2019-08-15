package com.  ;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.  .activity.processor.FormProcessorFactory;

/**
 * @author SSangapalli
 *
 */
@SpringBootApplication
@ImportResource({"classpath*:activityServiceTest-servlet.xml", "classpath*:com/  /qna/qna-service.xml","classpath*:activity-datasource-test.xml","classpath*:com/  /qna/qna-data.xml","classpath*:com/  /prof_utilities/cp-httpclient.xml"})
//"classpath*:com/  /qna/qna-cache.xml",
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class,JpaRepositoriesAutoConfiguration.class})
/*@ContextConfiguration(locations={
		"file:qnaServiceTest-servlet.xml", "classpath*:com/  /qna/qna-service.xml","file:qna-datasource-test.xml","classpath*:com/  /qna/qna-data.xml"
})*/
@WebAppConfiguration
public class ActivityServiceApplicationTest {
	
	
	/* @Bean
	 public FactoryBean serviceLocatorFactoryBean() {
	    ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
	    factoryBean.setServiceLocatorInterface(FormProcessorFactory.class);
	    return factoryBean;
	 }*/


	public static void main(String[] args) {
		SpringApplication.run(ActivityServiceApplicationTest.class, args);
	}
}
