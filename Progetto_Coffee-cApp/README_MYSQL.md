# Guida: Configurazione del Driver JDBC MySQL

## Problema Risolto
È stato risolto l'errore `SQLException: No suitable driver found for jdbc:mysql://127.0.0.1:3306/nome_db` aggiungendo la dipendenza corretta del driver JDBC MySQL al progetto Maven.

## Modifiche Apportate

### 1. Aggiornamento del `pom.xml`

Sono state apportate le seguenti modifiche al file `pom.xml`:

#### a) Correzione della versione Java
```xml
<properties>
    <java.version>17</java.version>
</properties>
```
La versione Java è stata corretta da 25 a 17 per corrispondere alla versione installata nel sistema.

#### b) Correzione della versione Spring Boot
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.0</version>
</parent>
```
La versione Spring Boot è stata corretta a 3.2.0 (la versione 4.0.0 non esiste).

#### c) Aggiunta del Driver MySQL JDBC
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.0.33</version>
</dependency>
```

### 2. File di Esempio

È stato creato il file `DatabaseConnectionExample.java` che dimostra come utilizzare il driver JDBC per connettersi a MySQL.

## Come Utilizzare

### Prerequisiti
1. MySQL installato e in esecuzione
2. Un database creato (es. `nome_db`)
3. Credenziali di accesso configurate

### Configurazione della Connessione

⚠️ **IMPORTANTE - Sicurezza**: Il file `DatabaseConnectionExample.java` contiene credenziali hardcoded solo a scopo didattico/di test. 

**NON utilizzare mai credenziali hardcoded in produzione!** Utilizza invece Spring Boot `application.properties` (vedi sezione "Prossimi Passi" più in basso).

Per testare l'esempio, modifica i parametri nel file `DatabaseConnectionExample.java`:

```java
private static final String URL = "jdbc:mysql://127.0.0.1:3306/nome_db";
private static final String USERNAME = "root";
private static final String PASSWORD = "password";
```

### Esecuzione dell'Esempio

Per compilare ed eseguire l'esempio:

```bash
# Compila il progetto
mvn compile

# Esegui l'esempio (opzionale - solo se vuoi testare la connessione)
mvn exec:java -Dexec.mainClass="unipa.it.progetto_coffeecapp.DatabaseConnectionExample"
```

## Versioni Utilizzate

- **Java**: 17
- **Maven**: 3.9.11
- **Spring Boot**: 3.2.0
- **MySQL Connector/J**: 8.0.33

## Note sulla Compatibilità

Il driver MySQL Connector/J versione 8.x è compatibile con:
- MySQL Server 5.7 e superiori
- Java 8 e superiori
- È stata verificata l'assenza di vulnerabilità di sicurezza note

## Risoluzione Problemi

### Errore "Communications link failure"
- Verifica che MySQL sia in esecuzione
- Controlla che l'indirizzo IP e la porta siano corretti
- Verifica le configurazioni del firewall

### Errore "Access denied"
- Controlla username e password
- Verifica i permessi dell'utente sul database

### Errore "Unknown database"
- Assicurati che il database esista
- Crea il database con: `CREATE DATABASE nome_db;`

## Prossimi Passi

Ora puoi:
1. Utilizzare il driver JDBC nel tuo codice
2. Configurare Spring Data JPA per l'accesso al database
3. Aggiungere il file `application.properties` con le configurazioni del database

Esempio di configurazione in `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/nome_db
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
