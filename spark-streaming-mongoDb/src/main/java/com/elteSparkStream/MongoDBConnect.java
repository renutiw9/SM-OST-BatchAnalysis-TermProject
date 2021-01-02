package com.elteSparkStream;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient; 
import com.mongodb.MongoCredential;  
public class MongoDBConnect { 

	static String dbHost = "localhost";
	static int port = 27017;
	private static MongoClient mongoDbConn = null;
	public static MongoClient getConn(){
		try {
			//Creating MongoDb Client
			mongoDbConn = new MongoClient( dbHost, port ); 

			// Accessing the database 
			MongoDatabase database = mongoDbConn.getDatabase("db_schoolweb"); 
			System.out.println("Connected to the MongoDB database successfully"); 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return mongoDbConn;
	}
	//	      // Accessing the database 
	//	      MongoDatabase database = mongo.getDatabase("db_schoolweb"); 
	//	      
	//	      //Create
	//	      //database.createCollection("sampleCollection"); 
	//	      
	//	      // Retrieving a collection
	//	      MongoCollection<Document> collection = database.getCollection("part5"); 
	//	      System.out.println("Collection myCollection selected successfully"); 
	//	      
	//	      //Insert new
	//	      Document document = new Document("title", "MongoDB")
	//	    			.append("description", "database")
	//	    			.append("likes", 100)
	//	    			.append("url", "http://www.tutorialspoint.com/mongodb/")
	//	    			.append("by", "tutorials point");
	//	    			
	//	    			//Inserting document into the collection
	//	    			collection.insertOne(document);
	//	    System.out.println("Document inserted successfully");
	//	    Document document1 = new Document("title", "MongoDB")
	//	    		.append("description", "database")
	//	    		.append("likes", 100)
	//	    		.append("url", "http://www.tutorialspoint.com/mongodb/")
	//	    		.append("by", "tutorials point");
	//	    		Document document2 = new Document("title", "RethinkDB")
	//	    		.append("description", "database")
	//	    		.append("likes", 200)
	//	    		.append("url", "http://www.tutorialspoint.com/rethinkdb/")
	//	    		.append("by", "tutorials point");
	//	    //Insert all values
	//	    List<Document> list = new ArrayList<Document>();
	//		list.add(document1);
	//		list.add(document2);
	//		collection.insertMany(list);
	//		
	//		//Retrieve document values
	//		// Getting the iterable object
	//		FindIterable<Document> iterDoc = collection.find();
	//		int i = 1;
	//		// Getting the iterator
	//		Iterator it = iterDoc.iterator();
	//		while (it.hasNext()) {
	//			System.out.println(it.next());
	//			i++;
	//		}

}