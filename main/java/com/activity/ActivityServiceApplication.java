package com.  .activity;

import java.util.Collections;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.DataSourceHealthIndicator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpEncodingAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;
import com.google.common.collect.Maps;
import com.  .activity.exception.AdapterExceptionHandler;
import com.  .activity.processor.FormProcessorFactory;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author SSangapalli
 *  
 */
@SpringBootApplication
/*@ImportResource({"classpath*:com/  /qna/qna-service.xml","classpath*:com/  /qna/qna-datasource-jndi.xml","classpath*:com/  /qna/qna-data.xml","classpath*:com/  /qna/qna-data.xml",
	"classpath*:com/  /qna/qna-service.xml","classpath*:com/  /activity/activityService.xml","classpath*:com/  /prof_utilities/cp-httpclient.xml"})*/
//"classpath*:com/  /qna/qna-cache.xml",
@ImportResource({"classpath*:com/  /activity/activityService.xml","classpath*:com/  /prof_utilities/cp-httpclient.xml"})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class,HttpEncodingAutoConfiguration.class})
@EnableSwagger2
@ComponentScan({"com.  .*"})
public class ActivityServiceApplication {
	
	private Logger logger = Logger.getLogger(ActivityServiceApplication.class.getName());
	 @Bean
	 public FactoryBean serviceLocatorFactoryBean() {
	    ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
	    factoryBean.setServiceLocatorInterface(FormProcessorFactory.class);
	    return factoryBean;
	 }
	 
	
	 @Bean
	    public Docket api() { 
	        return new Docket(DocumentationType.SWAGGER_2)  
	          .select()
	          .apis(RequestHandlerSelectors.basePackage("com.  "))
	          .apis(RequestHandlerSelectors.any())
	          .paths(PathSelectors.any())                          
	          .build().globalOperationParameters(Collections.singletonList(
	        		  new ParameterBuilder().name("X-User-Guid")
	        		  .defaultValue("")
	        		  .allowMultiple(false)
	        		  .description("User GUID")
	        		  .parameterType("header")
	        		  .required(true)
	        		  .modelRef(new ModelRef("string"))
	        		  .build()));	          
	                                                   
	    }
	 
	 
		@Bean
		public AnnotationMBeanExporter annotationMBeanExporter() {
		    AnnotationMBeanExporter annotationMBeanExporter = new AnnotationMBeanExporter();
		    annotationMBeanExporter.setRegistrationPolicy(RegistrationPolicy.IGNORE_EXISTING);
		    return annotationMBeanExporter;
		}
		
		@Bean
		public FilterRegistrationBean characterEncodingFilter() {
			FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
			filterRegistrationBean.setFilter(new CharacterEncodingFilter());
			filterRegistrationBean.setName("characterEncodingFilter");
			filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST);
			filterRegistrationBean.setOrder(1);
			filterRegistrationBean.addUrlPatterns("/*");			
			Map <String, String> initParams = Maps.newHashMap();
			initParams.put("encoding", "UTF-8");
			initParams.put("forceEncoding", "true");
			
			filterRegistrationBean.setInitParameters(initParams);
			
			return filterRegistrationBean;
		}
		
	
		
/*		@Bean
		public FilterRegistrationBean userResponseRequestFilterChain(@Qualifier("userResponseRequestFilterChainProxy") FilterChainProxy filterChainProxy) {
			FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
			filterRegistrationBean.setFilter(filterChainProxy);
			filterRegistrationBean.setName("userResponseRequestFilterChainProxy");
			filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST);
			filterRegistrationBean.setOrder(2);
			filterRegistrationBean.addUrlPatterns("/*");
			
			return filterRegistrationBean;
		}*/
		
	
		
		/*@Bean
		public HttpAuthServiceAdapter createAuthServiceAdaptor(){
			try{
				
				HttpAuthServiceAdapter adaptor= new HttpAuthServiceAdapter();
				adaptor.setRestOperations(new RestTemplate());
				return adaptor;
			}catch(Exception ex){
				
				ex.printStackTrace();
			}
			return null;
		}
		*/
		/*@Bean
		public BeanDefinitionRegistryPostProcessor beanDefinitionRegistryPostProcessor() {
			return new BeanDefinitionRegistryPostProcessor() {
				@Override
				public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
					
					
				}
				
				@Override
				public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
					DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
					
					for(String beanName : defaultListableBeanFactory.getBeanNamesForType(BaseFilter.class)) {
						BeanDefinition beanDefinition = BeanDefinitionBuilder
								.genericBeanDefinition(FilterRegistrationBean.class)
								.setScope(BeanDefinition.SCOPE_SINGLETON)
								.addConstructorArgReference(beanName)
								.addConstructorArgValue(new ServletRegistrationBean[]{})
								.addPropertyValue("enabled", false)
								.getBeanDefinition();
						
						StringBuilder newBeanName = new StringBuilder(beanName)
								.append("FilterRegistrationBean");
						
						defaultListableBeanFactory.registerBeanDefinition(newBeanName.toString(), beanDefinition);
					}
					
				}
			};
		}*/
		
		@Bean
		public RestTemplate restTemplate() throws Exception {
			logger.info("Initializing RestTemplate...");
			/*HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});*/

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.setErrorHandler(new AdapterExceptionHandler());
			return restTemplate;
		}

	@Bean
	public DataSourceHealthIndicator medscape_DataSource(@Qualifier("medscapeDataSource") DataSource dataSource) {
		return new DataSourceHealthIndicator(dataSource);
	}

	@Bean
	public DataSourceHealthIndicator questionnaire_DataSource(@Qualifier("activityDataSource") DataSource dataSource) {
		return new DataSourceHealthIndicator(dataSource);
	}
	public static void main(String[] args) {
		SpringApplication.run(ActivityServiceApplication.class, args);
	}
}
