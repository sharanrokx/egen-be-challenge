package io.egen.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoClient;

@RestController
@RequestMapping("/alerts")
class AlertsController {
    final Datastore datastore;

    @Autowired
    AlertsController(MongoClient mongoClient) {
        Morphia morphia = new Morphia();
        datastore = morphia.createDatastore(mongoClient, "myDB");
    }

    @RequestMapping(value="/list", method = GET)
    List<Alerts> index() {
    	return datastore.find(Alerts.class).asList();
    }

    @RequestMapping(value="/between", method = GET)
    List<Alerts> between(@RequestParam Long from, @RequestParam Long to) {
        return datastore.createQuery(Alerts.class).filter("date >", new Date(from)).filter("date <", new Date(to)).asList();
    }


   
    
    @Entity
    static class Alerts {
        @Id
        ObjectId id;
        Date date;
        Integer value;
        public Date getDate() {
			return date;
		}
        public Integer getValue() {
			return value;
		}
    }

}