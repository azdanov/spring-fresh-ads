# Spring Fresh Ads

A classified ads site made in Spring Boot hosted on Heroku.

A project where I explored Thymeleaf and minimal JavaScript approach to building classical applications with Spring.

This app is powered by [Java 16](https://www.oracle.com/java/technologies/). The backend is made in [Spring](https://spring.io/projects/spring-framework/), [Spring Boot](https://spring.io/projects/spring-boot), [Spring Data JPA](https://spring.io/projects/spring-data-jpa/) and secured by [Spring Security](https://spring.io/projects/spring-security/). The frontend is created with [Thymeleaf](https://www.thymeleaf.org/) and [Bootstrap 5](https://getbootstrap.com/) with minimal [JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript), apart from [Stripe](https://stripe.com) payment integration.

## Screenshots

| ![image](https://user-images.githubusercontent.com/6123841/124307274-45df1500-db70-11eb-9e8e-3c6129bb5201.png) | ![image](https://user-images.githubusercontent.com/6123841/124308490-06192d00-db72-11eb-888f-b22acdaad288.png) | ![image](https://user-images.githubusercontent.com/6123841/124309189-05cd6180-db73-11eb-8d15-edfc3da1fde6.png) |
| -------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------- |
| ![image](https://user-images.githubusercontent.com/6123841/124308194-94d97a00-db71-11eb-8944-73c9417769a1.png) | ![image](https://user-images.githubusercontent.com/6123841/124308561-1d581a80-db72-11eb-9999-b9f8841cd752.png) | ![image](https://user-images.githubusercontent.com/6123841/124309347-4036fe80-db73-11eb-996f-755bd7e1d6af.png) |

## Dev

To run make sure you have Java 16 and Docker installed.
Then start included docker-compose file and run application.

### Stripe

For Stripe payments you will have to create a personal test account
and replace env variables for `stripe.secret-key=${STRIPE_SECRET_KEY}`, `stripe.public-key=${STRIPE_PUBLIC_KEY}`, `stripe.webhook-secret=${STRIPE_WEBHOOK_SECRET}`

Useful links:
- https://stripe.com/docs/checkout/integration-builder
- https://stripe.com/docs/webhooks/integration-builder

### Email

For dev `mailhog` is used to catch outbound emails.
