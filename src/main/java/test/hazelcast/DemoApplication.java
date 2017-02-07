package test.hazelcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;

@SpringBootApplication
@EnableWebMvc
@EnableCaching
public class DemoApplication {
	
	@Bean
	public SomeDao someDao() {
		return new SomeDaoImpl();
	}
	
	@Bean(destroyMethod="shutdown")
	public HazelcastInstance hcClient() {
		ClientConfig cfg = new ClientConfig();
		cfg.getGroupConfig().setName("dev").setPassword("dev-pass");
		// cfg.getNetworkConfig().addAddress("172.17.0.2", "172.17.0.3", "172.17.0.4");
		cfg.getNetworkConfig().addAddress("localhost:15701", "localhost:15702", "localhost:15703");
		HazelcastInstance inst = HazelcastClient.newHazelcastClient(cfg);
		return inst;
	}
	
	@Bean
	public CacheManager hcCacheManager() {
		return new HazelcastCacheManager(hcClient());
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
