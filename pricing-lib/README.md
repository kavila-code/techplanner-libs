# pricing-lib

Librería Java desarrollada para el ecosistema TechPlanner.

## ¿Qué hace?

La librería permite:

- Calcular precios finales
- Aplicar descuentos
- Aplicar impuestos
- Analizar estadísticas de precios

## Tecnologías

- Java 21
- Maven
- JUnit 5

---

## Instalación

```xml
<dependency>
  <groupId>com.techplanner</groupId>
  <artifactId>pricing-lib</artifactId>
  <version>1.0.0</version>
</dependency>
```

---

## Ejemplo de uso

```java
BigDecimal finalPrice =
        PriceCalculator.calculateFinalPrice(
                new BigDecimal("100"),
                new BigDecimal("10"),
                new BigDecimal("19")
        );

System.out.println(finalPrice);
```

---

## Resultado

```text
107.1000
```

---

## Tests

Ejecutar:

```bash
./mvnw clean test
```

---

## Autor
Jhon Velez - 
TechPlanner 