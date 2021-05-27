# Geofence

#### Considerations:

- Distances are in meters
- Given a current point, I look for the nearby Geofences. With these Geofences, I search for what advertising within.

### Running it locally

First, you need to start local-environment:

```bash
$ cd local-environment
$ docker-compose up -d
```

Then, you can start application by using:

```bash
$ ./gradlew bootJar
$ java -jar build/libs/geofence-0.0.1-SNAPSHOT.jar
```

### Building image

```bash
$ docker build -t=geofence:latest .
```

When running in docker, creates geofence data manually (a problem that I haven't solved it yet )
