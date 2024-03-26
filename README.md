# Project Description

Streaming service is simple streaming platform designed using Spring Boot microservices architecture and inspired by popular streaming platforms like Netflix.

The core concept of this web application is to provide users with a platform for watching movies while tracking their viewing progress. For instance,users who watch certain number of Marvel movies may earn badge as form of recognition for their
engagement. The video-service component is responsible for managing the streaming of movies and shows, as well as tracking user progress as they watch content. On the other hand the badge-service component operates alongside the video-service, handling the assignment of badges based on user engagement
For instance, users who watch a certain number of Marvel movies trigger badge awards as a form of recognition for their dedication and activity on the platform.

All incoming request are routed through an API Gateway, which incorporates a JWTAuthenticationFilter responsible for verifying the validity of JWT tokens.
The API Gateway orchestrates communication with security-service, tasked with user management, authentication, and authorization, including permissions handling.



# Project Diagram
![diagram.png](..%2F..%2FDocuments%2Fdiagram.png)


API documentation:
https://documenter.getpostman.com/view/28489900/2sA35A74yQ