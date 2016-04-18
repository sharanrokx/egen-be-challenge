package io.egen.controller;

import java.util.Date;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;
import org.mongodb.morphia.Datastore;

import io.egen.controller.AlertsController.Alerts;

@Rule(name = "Alert rule")
public class AlertRule {

	private final int BASE_WEIGHT=150;
	private int weight;
    final Datastore datastore;
	
    public AlertRule(Datastore datastore, int weight) {
        this.datastore = datastore;
        this.weight=weight;
    }

    @Condition
	public boolean checkInput() {
	    return weight > 1.1*BASE_WEIGHT || weight < .9*BASE_WEIGHT;
	}
	
	@Action
	public void sayHelloToDukeFriend() throws Exception {
    	Alerts alert = new Alerts();
    	alert.date= new Date();
    	alert.value=weight;
    	datastore.save(alert);
	}
	

}
