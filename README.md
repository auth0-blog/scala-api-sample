# Build and Secure a Scala Play Framework API

This is the companion code project for the Build and Secure a Scala Play Framework API blog post @ [auth0.com/blog](https://auth0.com/blog/).

It is a REST API built using Scala and [Play Framework](https://playframework.com). It statically serves blog posts and comments from two endpoints. The endpoints are secured using access tokens issued by [auth0.com](https://auth0.com). In order to successfully call the endpoints and retrieve data, the request must supply a valid JWT bearer token in the `Authorization` header. Otherwise, a `401 Unauthorized` response is returned.

Example request:

```
curl \
-H 'Authorization: Bearer <your access token>' \
localhost:9000/api/post/1
```

## Running the project

To start the API, use:

```
$ AUTH0_DOMAIN=<your Auth0 domain> AUTH0_AUDIENCE=<your API audience> sbt run 9000
```

Where `AUTH0_DOMAIN` is your Auth0 tenant domain, and `AUTH0_AUDIENCE` is your API identifier.

## Endpoints

There are two endpoints available:

### /api/post/:postId

Returns a single blog post with the specified ID. The static data provides two blog posts with IDs 1 and 2 respectively.

Example response:

```json
{
  "id": 1,
  "content": "This is a blog post"
}
```

### /api/post/:postId/comments

Returns comments for the specified post. The static data provides comments for posts 1 and 2.

Example response:

```json
[
  {
    "id":1,
    "postId":1,
    "text":"This is an awesome blog post",
    "authorName":"Fantastic Mr Fox"
  },
  {
    "id":2,
    "postId":1,
    "text":"Thanks for the insights",
    "authorName":"Jane Doe"
  }
]
```
