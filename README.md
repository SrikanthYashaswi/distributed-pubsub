# Distributed PubSub
This is a project done as a part of Distributed Systems course at UB.

It follows rendezvous-based event routing between nodes.

Docker is used for orchestration.

# How to start the application
```
docker build -t pubsub .

docker run --rm -it --name broker1 -h broker1 -p 8082:8082 --network=backendBridge -e NETWORK_NEIGHBOURS=broker2,broker3  pubsub

docker run --rm -it --name broker2 -h broker2 -p 8083:8082 --network=backendBridge -e NETWORK_NEIGHBOURS=broker1  pubsub

docker run --rm -it --name broker3 -h broker3 -p 8084:8082 --network=backendBridge -e NETWORK_NEIGHBOURS=broker1,broker4  pubsub

docker run --rm -it --name broker4 -h broker4 -p 8085:8082 --network=backendBridge -e NETWORK_NEIGHBOURS=broker3,broker5  pubsub

docker run --rm -it --name broker5 -h broker5 -p 8086:8082 --network=backendBridge -e NETWORK_NEIGHBOURS=broker4  pubsub
```


