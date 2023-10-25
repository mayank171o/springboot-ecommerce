package com.fullstack.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import com.fullstack.ecommerce.dao.CountryRepository;
import com.fullstack.ecommerce.dao.OrderRepository;
import com.fullstack.ecommerce.dao.StateRepository;
import com.fullstack.ecommerce.entity.Product;
import com.fullstack.ecommerce.entity.ProductCategory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {


	
	private EntityManager entityManager;
	
	@Autowired
	public MyDataRestConfig(EntityManager theEntityManager)
	{
		this.entityManager = theEntityManager;
	}
	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		HttpMethod[] theUnsupportedActions = { HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH };

		// disable HTTP methods for PUT, POST, DELETE and PATCH
		disableHttpMethods(Product.class,config, theUnsupportedActions);
		disableHttpMethods(ProductCategory.class,config, theUnsupportedActions);
		disableHttpMethods(CountryRepository.class,config, theUnsupportedActions);
		disableHttpMethods(StateRepository.class,config, theUnsupportedActions);
		disableHttpMethods(OrderRepository.class,config, theUnsupportedActions);
		
		exposeIds(config);
		// configure cors mappiing
		//cors.addMapping(config.getBasePath() + "/**").allowedOrigins("http://localhost:4200");
	}

	@SuppressWarnings("rawtypes")
	private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
		config.getExposureConfiguration().forDomainType(ProductCategory.class)
				.withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
				.withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
	}

	@SuppressWarnings("rawtypes")
	private void exposeIds(RepositoryRestConfiguration config) {
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
		List<Class> entityClasses = new ArrayList<>();
		
		for(EntityType tempEntityType :entities)
		{
			entityClasses.add(tempEntityType.getJavaType());
		}
		
		Class[] domainTypes = entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainTypes);
		
	}
		
	

}
