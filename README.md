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
helm upgrade --install wildfly --create-namespace -n wildfly --set wildflyUser=manager,wildflyPassword=password -f wildfly.yaml bitnami/wildfly
```

Package the application:
```
cd jsf-demo
./mvnw package
```

Copy the application in Wildfly deployments dir (replace <pod name>):
```
kubectl -n wildfly cp jsf-demo/target/jsf-demo.war <pod name>:/app/jsf-demo.war
```

Once the app is deployed visit `http://yourhostname/jsf-demo/hello.xhtml`.

Run the following to see the generated otel metrics (replace <pod name>):
```
kubectl -n wildfly exec -it <pod name> -- curl localhost:9464/metrics
```
