package com.  ;

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.client.RestTemplate;
import com.  .activity.ActivityServiceApplication;
import com.  .activity.exception.AdapterExceptionHandler;
import com.  .activity.processor.FormProcessorFactory;
import com.  .auth.service.adapter.AuthServiceAdapter;



@RunWith(SpringJUnit4ClassRunner.class)
/*@SpringBootApplication
@ImportResource({"classpath*:com/  /qna/qna-datasource-jndi.xml","classpath*:com/  /qna/qna-service.xml","classpath*:com/  /qna/qna-data.xml",
	"classpath*:com/  /qna/qna-service.xml"})
//"classpath*:com/  /qna/qna-cache.xml",
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class,JpaRepositoriesAutoConfiguration.class})*/
//"classpath*:activityServiceTest-servlet.xml",,"classpath*:com/  /prof_utilities/cp-httpclient.xml"
@ContextConfiguration(locations={
		"classpath*:activityServiceTest-servlet.xml","classpath*:activity-datasource-test.xml"
})
@ComponentScan({"com.  .activity.*"})
@WebAppConfiguration
public class BaseActivityServiceApplicationTests {

	private Logger logger = Logger.getLogger(BaseActivityServiceApplicationTests.class.getName());
	
	@Autowired
	protected WebApplicationContext webApplicationContext;
	/*@Autowired
	private FilterChainProxy userResponseFilterChainProxy;*/	
	
	protected MockMvc mockMvc;
	
	protected HttpHeaders headers;	

	
	 @Bean
	 public FactoryBean serviceLocatorFactoryBean() {
	    ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
	    factoryBean.setServiceLocatorInterface(FormProcessorFactory.class);
	    return factoryBean;
	 }
	
	@Before
	public void baseSetUp() throws Exception {
		
		headers = new HttpHeaders();
		headers.add("host", "localhost");
		headers.add("user-agent", "Mozilla/5.0 windows firefox");		
		headers.add("X-User-Guid", "15483046");
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
	}
	
	protected String getCookieHeader(Cookie... cookies) {
		StringBuilder cookieHeader = new StringBuilder();
		
		for(Cookie cookie : cookies) {
			cookieHeader.append(cookie.getName())
				.append("=")
				.append(cookie.getValue())
				.append("; ");
		}
		
		return cookieHeader.toString();
	}
	
	

	@Test
	public void contextLoads() {
	}
	

	
	
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

	/*public HttpClient createHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		HttpClientBuilder b = HttpClientBuilder.create();

		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				return true;
			}
		}).build();
		b.setSSLContext(sslContext);

		HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

		SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory)
				.build();

		PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		b.setConnectionManager(connMgr);

		final Credentials credentials = new UsernamePasswordCredentials(username, password);
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(AuthScope.ANY, credentials);
		b.setDefaultCredentialsProvider(credsProvider);
		b.setDefaultHeaders(new ArrayList<Header>(){{add(BasicScheme.authenticate(credentials,"US-ASCII",false));}});

		HttpClient client = b.build();
		return client;
	}
*/
}
