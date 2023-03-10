package edu.eci.arep.app.loadBalancers.impl;

import edu.eci.arep.app.loadBalancers.LoadBalancer;

import java.util.ArrayList;
import java.util.List;

public class RoundRobin implements LoadBalancer {

    private static RoundRobin instance;

    private List<String> servers;
    private int actual;

    private RoundRobin(){
        actual = 0;
        servers = new ArrayList<>();
        servers.add("http://ec2-54-166-143-242.compute-1.amazonaws.com:4567");
        servers.add("http://ec2-100-25-204-197.compute-1.amazonaws.com:4567");
        servers.add("http://ec2-34-232-67-166.compute-1.amazonaws.com:4567");
    }

    public static RoundRobin getInstance() {
        if(instance == null){
            instance = new RoundRobin();
        }
        return instance;
    }

    @Override
    public String getServer() {
        synchronized (servers){
            if(actual >= servers.size()){
                actual = 0;
            }
            String server = servers.get(actual);
            actual+=1;
            return server;
        }
    }
}
