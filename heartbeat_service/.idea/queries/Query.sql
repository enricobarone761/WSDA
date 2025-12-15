CREATE TABLE utenti (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        nome VARCHAR(50) NOT NULL,
                        cognome VARCHAR(50) NOT NULL,
                        email VARCHAR(100) UNIQUE NOT NULL,
                        data_nascita DATE,
                        attivo BOOLEAN DEFAULT TRUE,
                        data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
