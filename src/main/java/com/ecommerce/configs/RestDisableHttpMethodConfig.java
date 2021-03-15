package com.ecommerce.configs;

import com.ecommerce.entities.Product;
import com.ecommerce.entities.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class RestDisableHttpMethodConfig implements RepositoryRestConfigurer {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

        HttpMethod[] unsuportedMehtodCall= {HttpMethod.DELETE, HttpMethod.POST, HttpMethod.PUT};

        // disabling httpMehtods DELETE, POST, PUT for Product.class
          config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsuportedMehtodCall) )
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unsuportedMehtodCall));


        // disabling httpMehtods DELETE, POST, PUT for ProductCategory.class
          config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsuportedMehtodCall) )
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(unsuportedMehtodCall));


          exposedDomainTypeId(config);

    }

    private void exposedDomainTypeId(RepositoryRestConfiguration config) {

        Set<EntityType<?>> entityTypes= entityManager.getMetamodel().getEntities();

        List<Class> classes= new ArrayList<>();

        for (EntityType entityType: entityTypes){

            classes.add(entityType.getJavaType());
        }

        Class[] domainTypes= classes.toArray( new Class[0]);

        config.exposeIdsFor(domainTypes);
    }
}
