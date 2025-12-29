-- ========================================
-- SCRIPT MIGRAZIONE ADDETTI -> UTENTI
-- ========================================
-- Questo script migra i dati dalla vecchia tabella 'addetto'
-- alla tabella 'utente' con ruolo ADDETTO
--
-- ⚠️ ATTENZIONE: Esegui questo script SOLO se hai già dati
-- nella tabella addetto che vuoi migrare!
-- ========================================

-- Step 1: Verifica i dati esistenti
SELECT * FROM addetto;
SELECT * FROM utente;

-- Step 2: Migrazione degli addetti nella tabella utente
-- Inserisce gli addetti come utenti con ruolo ADDETTO
-- Password impostata a 'addetto123' per tutti (da cambiare dopo il primo login)
INSERT INTO utente (username, password, nome, cognome, credito_residuo, ruolo)
SELECT
    email AS username,
    'addetto123' AS password,  -- ⚠️ CAMBIA QUESTA PASSWORD!
    nome,
    cognome,
    0.0 AS credito_residuo,
    'ADDETTO' AS ruolo
FROM addetto
WHERE email NOT IN (
    SELECT username FROM utente
);

-- Step 3: Verifica il risultato
SELECT * FROM utente WHERE ruolo = 'ADDETTO';

-- Step 4: (OPZIONALE) Backup della vecchia tabella prima di eliminarla
CREATE TABLE addetto_backup AS SELECT * FROM addetto;

-- Step 5: (OPZIONALE) Elimina la vecchia tabella addetto
-- ⚠️ ESEGUI SOLO DOPO AVER VERIFICATO CHE TUTTO FUNZIONI!
-- DROP TABLE addetto;

-- ========================================
-- SCRIPT ALTERNATIVO: Se vuoi ripartire da zero
-- ========================================

-- Elimina tutti i dati esistenti (ATTENZIONE!)
-- DROP TABLE IF EXISTS addetto;
-- DROP TABLE IF EXISTS utente;

-- Riavvia l'applicazione Spring Boot che ricreerà
-- automaticamente le tabelle con la struttura corretta

-- ========================================
-- VERIFICA FINALE
-- ========================================

-- Controlla che tutti gli addetti siano stati migrati
SELECT
    COUNT(*) as totale_addetti,
    ruolo
FROM utente
WHERE ruolo = 'ADDETTO'
GROUP BY ruolo;

-- Lista di tutti gli addetti migrati
SELECT
    id_utente,
    username as email,
    nome,
    cognome,
    ruolo
FROM utente
WHERE ruolo = 'ADDETTO';

