# Solo Flix (Resource Server)
#### Mandresy Andriamasinoro

---
This project is a website that emulates Netflix, featuring a selection of movies along with their titles and descriptions. It allows users to browse and stream video trailers.

This repository contains the code for the resource server, responsible for storing and serving the movie data required by the client application. 
This client app needs to present an access token from the authorization server along with its request to the GraphQL API.

The movie data is originally obtained from TMDB API, and cached inside the database for faster retrieval.

## Resources

---
### Movies:
* All movies
* Popular Movies
* Trending Movies
* Top-rated Movies
* Romance Movies

### GraphQL fields:
* id
* reference
* title
* description
* date
* image
* video
* rating


## Dependencies

---
* Spring Boot
* Spring MVC
* GraphQl
* Oauth2
* JPA
* MySQL Connector
* JSR303

## Executing program

---
* The service can be accessed at this url: 


## Credits

---

* "This product uses the TMDB API but is not endorsed or certified by TMDB."