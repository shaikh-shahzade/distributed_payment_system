package com.dps.eurekaserver.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/eureka")
public class EurekaInfoController {

    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();
        
        EurekaServerContext serverContext = EurekaServerContextHolder.getInstance().getServerContext();
        PeerAwareInstanceRegistry registry = serverContext.getRegistry();

        status.put("status", "UP");
        status.put("timestamp", Instant.now().toString());
        status.put("totalRegisteredInstances", countAllInstances(registry));
        status.put("totalApplications", registry.getApplications().size());
        status.put("environment", getEnvironment());
        
        return status;
    }

    @GetMapping("/applications")
    public Map<String, Object> getApplications() {
        Map<String, Object> response = new HashMap<>();
        
        EurekaServerContext serverContext = EurekaServerContextHolder.getInstance().getServerContext();
        PeerAwareInstanceRegistry registry = serverContext.getRegistry();

        List<Map<String, Object>> applications = registry.getApplications().getRegisteredApplications()
                .stream()
                .map(this::mapApplication)
                .collect(Collectors.toList());

        response.put("applications", applications);
        response.put("totalCount", applications.size());
        response.put("timestamp", Instant.now().toString());
        
        return response;
    }

    @GetMapping("/instances")
    public Map<String, Object> getAllInstances() {
        Map<String, Object> response = new HashMap<>();
        
        EurekaServerContext serverContext = EurekaServerContextHolder.getInstance().getServerContext();
        PeerAwareInstanceRegistry registry = serverContext.getRegistry();

        List<Map<String, Object>> allInstances = new ArrayList<>();

        for (Application app : registry.getApplications().getRegisteredApplications()) {
            for (InstanceInfo instance : app.getInstances()) {
                allInstances.add(mapInstance(instance, app.getName()));
            }
        }

        response.put("instances", allInstances);
        response.put("totalCount", allInstances.size());
        response.put("timestamp", Instant.now().toString());
        
        return response;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> health = new HashMap<>();
        
        EurekaServerContext serverContext = EurekaServerContextHolder.getInstance().getServerContext();
        PeerAwareInstanceRegistry registry = serverContext.getRegistry();

        health.put("status", "UP");
        health.put("registeredInstances", countAllInstances(registry));
        health.put("upInstances", countUpInstances(registry));
        health.put("timestamp", Instant.now().toString());
        
        return health;
    }

    private Map<String, Object> mapApplication(Application application) {
        Map<String, Object> appMap = new HashMap<>();
        appMap.put("name", application.getName());
        appMap.put("instanceCount", application.getInstances().size());
        appMap.put("instances", application.getInstances().stream()
                .map(instance -> mapInstance(instance, application.getName()))
                .collect(Collectors.toList()));
        return appMap;
    }

    private Map<String, Object> mapInstance(InstanceInfo instance, String appName) {
        Map<String, Object> instanceMap = new HashMap<>();
        instanceMap.put("instanceId", instance.getInstanceId());
        instanceMap.put("application", appName);
        instanceMap.put("hostName", instance.getHostName());
        instanceMap.put("ipAddr", instance.getIPAddr());
        instanceMap.put("port", instance.getPort());
        instanceMap.put("securePort", instance.getSecurePort());
        instanceMap.put("status", instance.getStatus().name());
        instanceMap.put("healthCheckUrl", instance.getHealthCheckUrl());
        instanceMap.put("statusPageUrl", instance.getStatusPageUrl());
        instanceMap.put("homePageUrl", instance.getHomePageUrl());
        instanceMap.put("lastUpdatedTimestamp", instance.getLastUpdatedTimestamp());
        instanceMap.put("lastDirtyTimestamp", instance.getLastDirtyTimestamp());
        
        Map<String, String> metadata = new HashMap<>();
        if (instance.getMetadata() != null) {
            metadata.putAll(instance.getMetadata());
        }
        instanceMap.put("metadata", metadata);
        
        return instanceMap;
    }

    private int countAllInstances(PeerAwareInstanceRegistry registry) {
        return registry.getApplications().getRegisteredApplications().stream()
                .mapToInt(app -> app.getInstances().size())
                .sum();
    }

    private long countUpInstances(PeerAwareInstanceRegistry registry) {
        return registry.getApplications().getRegisteredApplications().stream()
                .flatMap(app -> app.getInstances().stream())
                .filter(instance -> instance.getStatus() == InstanceInfo.InstanceStatus.UP)
                .count();
    }

    private String getEnvironment() {
        String profile = System.getProperty("spring.profiles.active");
        return (profile != null && !profile.isEmpty()) ? profile : "default";
    }
}
