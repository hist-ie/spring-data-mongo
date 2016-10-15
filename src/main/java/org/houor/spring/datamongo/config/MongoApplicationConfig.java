package org.houor.spring.datamongo.config;

import com.mongodb.Mongo;

public class MongoApplicationConfig {

	protected String getDatabaseName() {
		return "e-store";
	}

	public Mongo mongo() throws Exception {
		return null;
	}

	protected String getMappingBasePackage() {
		return "org.houor.spring.datamongo.domain";
	}

	
	
}
