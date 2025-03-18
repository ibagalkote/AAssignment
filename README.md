# AAssignment

There are 3 parts in the respository for the complete ***Weather Forecast Application***.
1. **Weather-App** backend of the application
2. **WeatherForecast-UI** frontend of the application
3. **Monitoring** configuration to monitor the application

## Weather-App (Backend)
- The application's backend is designed and developed using a **Spring-Boot microservices architecture** and the **Domain-Driven Design (DDD) pattern**. The core of the application is centered around the **Domain model**. **Controllers** are in the **primary adapters to handle incoming requests**, while **secondary adapters handle outgoing external integrations** such as the database repository.

- For data storage, we use **AWS RDS**, which contains three tables: **WeatherLocation, DailyWeather, and ExtendedForecast**. The **WeatherLocation** table stores location information, including zip code, city, and country. The **DailyWeather** table holds daily weather data, such as the current temperature. Meanwhile, the **ExtendedForecast** table provides extended weather information for a particular location over the next 10 days, including low and high temperatures, wind speed, conditions, and precipitation.

- The application features **centralized error handling**, which includes **specific error handlers** and a **global exception handler**. This error management utilizes the Spring-Boot **@ControllerAdvice** annotation to keep error handling **centralized** and **maintainable**.

- Implemented **Caffeine Cache** to store weather data for approximately 30 minutes temporarily. After the expiry, the cache evicts the expired data.

- Integrated the application with **Prometheus Metrics**, which exposes essential metrics, such as the number of incoming requests over a given period. **Prometheus** can be extended by **customizing to expose custom metrics** to meet the application's needs.

- **Swagger** integration informs users about the **exposed weather-app APIs**. Currently, two endpoints are available within a single **WeatherController**. The first endpoint retrieves a weather forecast, including the current temperature and an extended forecast for the next 10 days.

- **Actuator integration** with the Spring-Boot weather app **monitors the health** of the application.

- To **start the Spring Boot application**, first run **`mvn clean install`**. This command generates a JAR file in the weather app's target directory. Next, execute **`java -jar weather-app.jar`** to launch the microservices via Spring Boot, making them accessible to the front-end or any application that needs to read the weather data.

### Links for the Weather-App
1. **Swagger**: http://localhost:8080/swagger-ui/index.html
2. **Microservices API 1**: http://localhost:8080/api/weather/locations/{zipCode}
3. **Microservices API 2**: http://localhost:8080/api/weather/locations
4. **Actuator**: http://localhost:8080/actuator
5. **Prometheus**: http://localhost:8080/actuator/prometheus



## WeatherForecast-UI (Frontend)
- **WeatherForecast-UI** was developed using **React's component-based architecture**. 
- The microservice accepts zip code and displays the weather data for the given zip code.
- If the zip code does not exist or if it exists and there is no weather data related to it, it displays appropriate messages. 
- **To start the frontend**, run the command "**npm start**" from the weatherforecast-ui directory

### Links for the WeatherForecast-UI
1. **Frontend** is accessible via http://localhost:3000/



## Monitoring
- **Monitoring** for the weather app is built using **Prometheus** and **Grafana**. 
- **Prometheus** is for **monitoring and alerting**. It **collects, stores, and analyzes metrics** that the weather app application and services expose. It offers insights into **performance and system health**.
- **Grafana** for **data visualization, monitoring, and troubleshooting**. The visualization is based on and integrated with the data collected from the weather app via Prometheus.
- The **Docker** script **docker-compose.yml** composes and creates the monitoring application that directs **what ports Prometheus should monitor** and how and on **which port Grafana visualized**. It can be brought up using the "**docker-compose up**" cli.

### Links for the Monitoring
1. **Prometheus** is accessible via http://localhost:9090
2. **Grafana** is accessible via http://localhost:3030
