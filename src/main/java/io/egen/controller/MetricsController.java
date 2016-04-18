package io.egen.controller;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.easyrules.api.RulesEngine;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoClient;

@RestController
@RequestMapping("/metrics")
class MetricsController {
    final Datastore datastore;

    @Autowired
    MetricsController(MongoClient mongoClient) {
        Morphia morphia = new Morphia();
        datastore = morphia.createDatastore(mongoClient, "myDB");
    }

    @RequestMapping(value="/list", method = GET)
    List<Metrics> index() {
    	return datastore.find(Metrics.class).asList();
    }

    @RequestMapping(value="/between", method = GET)
    List<Metrics> between(@RequestParam Long from, @RequestParam Long to) {
        return datastore.createQuery(Metrics.class).filter("date >", new Date(from)).filter("date <", new Date(to)).asList();
    }

    @RequestMapping(value="/create", method = POST, consumes = "application/json")
    void create(@RequestBody PostObject obj) {
    	Metrics metrics = new Metrics();
    	metrics.date= new Date(Long.valueOf(obj.getTimeStamp()));
    	metrics.value=Integer.valueOf(obj.getValue());
    	datastore.save(metrics);
        AlertRule alertRule = new AlertRule(datastore, metrics.value);
        RulesEngine rulesEngine = aNewRulesEngine().build();
        rulesEngine.registerRule(alertRule);
        rulesEngine.fireRules();

    }

   
    
    @Entity
    static class Metrics {
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