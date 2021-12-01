# Build docker image

```
git clone https://github.com/goxr3plus/Simplest-Spring-Boot-Hello-World.git

docker run -it --rm --name my-maven-project -v "$(pwd)/Simplest-Spring-Boot-Hello-World":/usr/src/mymaven -w /usr/src/mymaven maven:3.3-jdk-8 mvn clean install

docker build -t <repo>/<name>:<tag> .

docker push <repo>/<name>:<tag> 
```
