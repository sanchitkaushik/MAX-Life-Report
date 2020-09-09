package com.maxlifeinsurance.mpro.config;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 
 * @author Vinay
 *
 */
public class DbConfigForLocal {

	public MongoCollection<Document> getDbConnection(String dbName) {
		try {
			MongoClient mongo = new MongoClient("localhost", 27017);
			ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
			CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
			CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
			MongoClientSettings clientSettings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .codecRegistry(codecRegistry)
                    .build();
			com.mongodb.client.MongoClient mongoClient = MongoClients.create(clientSettings);
			System.out.println("Connecting to database");
			MongoDatabase database = mongoClient.getDatabase(dbName).withCodecRegistry(codecRegistry);
			System.out.println("Connection created successfully");
			for (String name : database.listCollectionNames()) {
				System.out.println(name);
			}			
			return mongo.getDatabase(dbName).getCollection("proposal").withCodecRegistry(codecRegistry);
		} catch (Exception ex) {
			System.out.println("Exception occured while creating connection with database :: "+ex);
		}
		return null;
	}

	

}
