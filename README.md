# Geofence

Considerações:

Sobre buscar advertisings com base na location:

Dado um ponto atual, busco quais são as geoFences perto. Com essas geofences, eu busco quais advertisings tenho nessas
geofences.

#### Create new geofence:

- It need to consume a JSON with lat, lng and radius
- All values are expressed by decimal of precision 7 (max)
- Radius is how meters far from the center

#### Manage (CRUD) advertising in a geofence

- It need to consume a JSON with href field
- Href need to be a valid URL
- Href need to be verified (once invoked it need to return 2XX) otherwise it need to produce proper http status code

#### Check for geofence entrance

- It need to return a list of advertising regarding current lat, lng
- It need to return empty if there's no advertising on current lat, lng
- If there's any advertising on current lat and lng it need to return how far current lat and lng are from center of
  geofences
    - Distance in meters

