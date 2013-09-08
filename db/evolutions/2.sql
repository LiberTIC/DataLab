# --- !Ups
DELETE FROM organismeactivite WHERE id=4;
DELETE FROM organismeactivite WHERE id=5;
INSERT INTO organismeactivite VALUES (7, 'Formation');
INSERT INTO organismeactivite VALUES (8, 'Services');
INSERT INTO organismeactivite VALUES (9, 'Réutilisation');
INSERT INTO organismeactivite VALUES (10, 'Réseau');
INSERT INTO organismeactivite VALUES (11, 'Autre');

UPDATE organismenbsalarie SET libelle='N/A' WHERE id=1;
UPDATE organismenbsalarie SET libelle='1 à 5' WHERE id=2;
UPDATE organismenbsalarie SET libelle='6 à 20' WHERE id=3;
UPDATE organismenbsalarie SET libelle='21 à 50' WHERE id=4;
UPDATE organismenbsalarie SET libelle='51 à 100' WHERE id=5;
INSERT INTO organismenbsalarie VALUES (6, 'Plus de 100');
# --- !Downs