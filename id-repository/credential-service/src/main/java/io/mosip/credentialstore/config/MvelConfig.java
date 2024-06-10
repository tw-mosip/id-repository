package io.mosip.credentialstore.config;

import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Neha Farheen.
 */

@Configuration
public class MvelConfig {
	
	@Value("${config.server.file.storage.uri}")
	private String configServerFileStorageURL;

	@Value("${credential.service.mvel.file}")
	private String mvelFile;

	@Autowired
	@Qualifier("plainRestTemplate")
	private RestTemplate restTemplate;

	@Bean("varres")
	public VariableResolverFactory getVariableResolverFactory() {
		String mvelExpression = restTemplate.getForObject(configServerFileStorageURL + mvelFile, String.class);
		VariableResolverFactory functionFactory = new MapVariableResolverFactory();
		MVEL.eval(mvelExpression, functionFactory);
		return functionFactory;
	}

}
