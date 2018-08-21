# altran-test


La solución esta dividida en 3 capas

- Administracion - (Vista para el monitoreo de la aplicación)
- Presentation - (API Interfaz)
- Business - Logica de negocio
- Access Data - Acceso a Dato
- Common - Comun

Esta division se hizo separando cada una de ellas en Jar's para garantizar lo siguiente:

1. Garantizar la portabilidad y la reutilización de cualquiera de las capas en otras soluciones.
2. Garantizar la escalabilidad y la mantenibilidad sin afectar cualquier funcionalidad existente que no se haya modificado 

La solucion tiene integracion con **SonarQube** para la revision  

###Documentacion del API 

**https://test17285.docs.apiary.io**

Lo hice por este medio ya que Swagger y Spring RestDoc tienen bugs para integrarse con WebFlux 


###Dependencias
- Java 8
- Docker

###Monitoreo

- Implemente el uso de Spring Boot Admin, el puerto es: **9090**
- Implemente el uso Actuator para obtenr las trazas de monitoreo

