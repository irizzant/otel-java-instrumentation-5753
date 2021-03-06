# otel-java-instrumentation-5753
Simple reproducer for issue https://github.com/open-telemetry/opentelemetry-java-instrumentation/issues/5753

# Prerequisites
* k8s cluster with Nginx Ingress Controller
* Helm
* Make sure you change the `hostname` variables in `values.yaml` according to your needs

# Run
Add Bitnami repo:
```
helm repo add bitnami https://charts.bitnami.com/bitnami
```

Install the chart:
```
helm upgrade --install wildfly --create-namespace -n wildfly -f wildfly.yaml bitnami/wildfly
```

Package the application:
```
cd jsf-demo
./mvnw package
``` 
Copy the application in Wildfly deployments dir:
```
kubectl -n wildfly cp jsf-demo/target/jsf-demo.war $(kubectl -n wildfly get po -o jsonpath="{.items[*].metadata.name}"):/app/jsf-demo.war
```

Once the app is deployed visit `http://yourhostname/jsf-demo/hello.xhtml`.

Run the following to see the generated otel metrics:
```
kubectl -n wildfly exec -it $(kubectl -n wildfly get po -o jsonpath="{.items[*].metadata.name}") -- curl localhost:9464/metrics
```
