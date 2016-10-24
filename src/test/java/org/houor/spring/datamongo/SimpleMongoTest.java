package org.houor.spring.datamongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SimpleMongoTest extends TestCase {

	Logger logger = LoggerFactory.getLogger(SimpleMongoTest.class);

	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;

	public SimpleMongoTest(String testName) {
		super(testName);
		// 连接到 mongodb 服务
		mongoClient = new MongoClient("localhost", 27017);
		// 连接到数据库
		mongoDatabase = mongoClient.getDatabase("test");
		logger.debug("Connect to database successfully.");
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		if(mongoDatabase.getCollection("products") != null) {
			mongoDatabase.getCollection("products").drop();
		}
		mongoClient.close();
		logger.debug("Close Mongodb successfully.");
	}

	public static Test suite() {
		return new TestSuite(SimpleMongoTest.class);
	}

	public void testCreateCollection() {

		mongoDatabase.createCollection("products");
	}

	public void testInsert() {
		MongoCollection<Document> collections = mongoDatabase.getCollection("products");

		// 插入文档

		// 1. 创建文档 org.bson.Document 参数为key-value的格式
		// 2. 创建文档集合List<Document>
		// 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>)
		// 插入单个文档可以用mongoCollection.insertOne(Document)

		Document document = new Document("title", "MongoDB");
		document.append("author", "Kristina").append("description", "database");

		List<Document> documents = new ArrayList<Document>();
		documents.add(document);

		collections.insertMany(documents);
		logger.debug("insert documents successfully");

	}

	public void testSelect() {
		MongoCollection<Document> collections = mongoDatabase.getCollection("products");

		// 检索所有文档
		// 1. 获取迭代器FindIterable<Document>
		// 2. 获取游标MongoCursor<Document>
		// 3. 通过游标遍历检索出的文档集合
		FindIterable<Document> findIterable = collections.find();
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while (mongoCursor.hasNext()) {
			System.out.println(mongoCursor.next());
		}

	}

	public void testUpdate() {
		MongoCollection<Document> collections = mongoDatabase.getCollection("products");

		collections.updateMany(Filters.eq("author", "Kristina"), new Document("$set", new Document("author", "Kristina Chodorow")));

		FindIterable<Document> findIterable = collections.find();
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while (mongoCursor.hasNext()) {
			System.out.println(mongoCursor.next());
		}

	}

	public void testDelete() {
		MongoCollection<Document> collections = mongoDatabase.getCollection("products");
		collections.deleteOne(Filters.eq("author", "Kristina Chodorow"));

		FindIterable<Document> findIterable = collections.find();
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while (mongoCursor.hasNext()) {
			System.out.println(mongoCursor.next());
		}
	}

}
