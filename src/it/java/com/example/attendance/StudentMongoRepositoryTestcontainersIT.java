package com.example.attendance;

import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

// Integration test for MongoDB using Testcontainers
public class StudentMongoRepositoryTestcontainersIT {

    @ClassRule
    public static final MongoDBContainer mongo = 
        new MongoDBContainer("mongo:4.4.3");
    
    
    //  checking  connect to MongoDB container
    @Test
    public void testCanConnectToMongoDB() {
        MongoClient client = new MongoClient(
            new ServerAddress(
                mongo.getContainerIpAddress(),
                mongo.getMappedPort(27017)
            )
        );
        
        client.getDatabase("test").getName();
        
        client.close();
    }
}