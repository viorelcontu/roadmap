Build application
`mvn clean package`

- Adjust Dockerfile
- Build docker image with the latest
`docker build -t roadmap:latest .`

Create container
`docker create --name roadmap -p 8080:8080 roadmap`

Run 
`docker start roadmap`