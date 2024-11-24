# City Suggestion API

This is a Spring Boot application that provides city name suggestions based on a query string.
The API supports searching for cities by name and returns results with scores based on the city name match, geographical distance (optional), and population (configurable).

## Features

- **City Suggestions**: Get city suggestions based on a query string.
- **Location-based Scoring**: Cities are ranked based on proximity to a given latitude and longitude.
- **Name-based Scoring**: Cities are ranked based on a match to the query string using the Levenshtein distance algorithm.
- **Population-based Scoring**: Cities with higher populations are given a higher score. (Configurable)
- **Caching**: The list of city suggestions is cached for faster responses.

## API Documentation

### Get City Suggestions

#### Endpoint
`GET /suggestions`

#### Parameters

| Name       | Type    | Description                                                                 |
|------------|---------|-----------------------------------------------------------------------------|
| `q`        | `String`| The query string to search for city names (required).                       |
| `latitude` | `Double`| Latitude of the location (optional).                                       |
| `longitude`| `Double`| Longitude of the location (optional).                                      |

#### Responses

- **200 OK**: Successfully returns a list of city suggestions.
- **400 Bad Request**: Invalid input parameters.
- **500 Internal Server Error**: An unexpected error occurred on the server.

#### Example Request

```http
GET /suggestions?q=New&latitude=40.7128&longitude=-74.0060
```

#### Example Response

```json
{
  "suggestions": [
    {
      "name": "New York City",
      "latitude": 40.71427,
      "longitude": -74.00597,
      "score": 0.5382326284115252
    },
    {
      "name": "Newark",
      "latitude": 40.73566,
      "longitude": -74.17237,
      "score": 0.4346739753070565
    },
    {
      "name": "Newton",
      "latitude": 41.05815,
      "longitude": -74.75267,
      "score": 0.41902232685226914
    }
  ]
}
```

## Scoring System

The scoring of city suggestions is based on three factors:
1. **Name Match Score:** Cities that start with the same letters as the query string receive a higher score. The score is calculated using the Levenshtein distance algorithm to measure the similarity between the query string and city names.
2. **Location Score:** Cities that are geographically closer to the provided latitude and longitude receive a higher score. The distance is calculated using the Haversine formula.
3. **Population Score:** Cities with higher populations receive a higher score. The population score can be turned off and is scaled according to the weight defined in the configuration.

### Weights Configuration

You can adjust the weights of each scoring factor (name match, location, population) by modifying the values in the application.properties file.

```properties
# Scoring Weights
suggestion.scoring.location-weight=0.3
suggestion.scoring.name-weight=0.5
suggestion.scoring.use-population=true
suggestion.scoring.population-weight=0.2
```

- suggestion.scoring.location-weight: Controls the contribution of the city location match (proximity to latitude/longitude) to the overall score.
- suggestion.scoring.name-weight: Controls the contribution of the city name match to the overall score.
- suggestion.scoring.use-population: Set to true to enable population-based scoring, or false to disable it.
- suggestion.scoring.population-weight: Controls the contribution of the population size to the overall score (only used if use-population is true).

These values can be adjusted according to your preference for how much weight should be given to each scoring factor.

## Setup and Configuration

#### Requirements
- Java 21 or higher
- Gradle
- Postgres Database

#### Running the application
1. Clone the repository
```bash
git clone https://github.com/BadAccuracyID/city-suggestion-api.git
cd city-suggestion-api
```

2. Build and run the application
```bash
./gradlew bootRun
```

3. The application will be hosted on http://localhost:8080 by default

## Environment Variables
The application uses **environment variables** for database connection configuration. These variables are defined in the .env file inside the /src/resources directory.

#### Configuration in .env
```bash
# Database connection settings
DATASOURCE_URL=jdbc:postgresql:localhost:5432/postgres
DATASOURCE_USERNAME=root
DATASOURCE_PASSWORD=root
```

You can adjust these values to connect to your own PostgreSQL database. Ensure the .env file is correctly set up before running the application.

You can also find a .env.example file in the same directory, which provides a template for the environment variables.


## Testing

Tests are written using **JUnit 5** and **Mockito** for mocking dependencies.

To run the tests:
```bash
./gradlew test
```
