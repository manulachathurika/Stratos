#!/bin/bash

export host_ip="localhost"

set -e

# Undeploying application
echo "Undeploying application..."
curl -X POST -H "Content-Type: application/json" -k -v -u admin:admin https://${host_ip}:9443/api/applications/myapp1265/undeploy

sleep 5

# Deleting application
echo "Deleting application..."
curl -X DELETE -H "Content-Type: application/json" -k -v -u admin:admin https://${host_ip}:9443/api/applications/myapp1265

# Removing groups
echo "Removing groups..."
curl -X DELETE -H "Content-Type: application/json" -k -v -u admin:admin https://${host_ip}:9443/api/cartridgeGroups/group6

# Removing cartridges
echo "Removing cartridges..."
curl -X DELETE -H "Content-Type: application/json" -k -v -u admin:admin https://${host_ip}:9443/api/cartridges/tomcat
curl -X DELETE -H "Content-Type: application/json" -k -v -u admin:admin https://${host_ip}:9443/api/cartridges/tomcat1
curl -X DELETE -H "Content-Type: application/json" -k -v -u admin:admin https://${host_ip}:9443/api/cartridges/tomcat2

# Removing kubernetes
echo "Removing kubernetes cluster..."
curl -X DELETE -H "Content-Type: application/json" -k -v -u admin:admin https://${host_ip}:9443/api/kubernetesClusters/kubernetes-cluster-1

# Removing autoscale policies
echo "Removing autoscale policies..."
curl -X DELETE -H "Content-Type: application/json" -k -v -u admin:admin https://${host_ip}:9443/api/autoscalingPolicies/autoscale_policy_1