This is the simplest default configuration of Spring Security - no configuration at all.
Security is active by only including spring security dependency.

##warning! Adding any toggle to disable spring security is a bad practice!
Maven profiles are used for demonstration purposes. That way it can be built with `unsecure` profile
`mvn package -P unsecure`
And then just run the jar
`java -jar app.jar`

Another way is to toggle Spring Security is excluding SecurityAutoConfiguration bean in configuration property.
This made with enabling spring profiles by including `spring.profiles.active=@spring.profiles.active@` variable in configuration 
file and creating application-unsecure.properties configuration.
There are multiple ways to set profiles for your springboot application.

You can add this in application.properties file:
`spring.profiles.active=unsecure`

Programmatic way:
`SpringApplication.setAdditionalProfiles("unsecure");`

Tests make it very easy to specify what profiles are active
`@ActiveProfiles("unsecure")`

In a Unix environment
`export spring_profiles_active=unsecure`

JVM System Parameter
`-Dspring.profiles.active=unsecure`
Example: Running a springboot jar file with profile.
`java -jar -Dspring.profiles.active=unsecure app.jar`
