# --- !Ups

--- User data
INSERT INTO member VALUES (1,	null,	'http://a0.twimg.com/profile_images/1248011807/bsimard-logo_normal.png', 'Benoît Simard',	'bsimard@yopmail.com',	TRUE,	TRUE,	null,	'XXXXXXXX', 'http://wwww.bsimard.com',	'XXXXXXXX');
INSERT INTO useraccount VALUES (1, 'twitter', '197173482');
INSERT INTO member_useraccount VALUES (1, 1);
INSERT INTO useraccount VALUES (2, 'linkedin', 'QNK2UhSM7G');
INSERT INTO member_useraccount VALUES (1, 2);

--- Activity theme
INSERT INTO organismeactivite VALUES (1, 'Activity 1');
INSERT INTO organismeactivite VALUES (2, 'Activity 2');
INSERT INTO organismeactivite VALUES (3, 'Activity 3');
INSERT INTO organismeactivite VALUES (4, 'Activity 4');
INSERT INTO organismeactivite VALUES (5, 'Activity 5');

--- Activity theme
INSERT INTO organismetype VALUES (1, 'Type 1');
INSERT INTO organismetype VALUES (2, 'Type 2');
INSERT INTO organismetype VALUES (3, 'Type 3');
INSERT INTO organismetype VALUES (4, 'Type 4');
INSERT INTO organismetype VALUES (5, 'Type 5');

-- Organismes data
INSERT INTO organismemaster VALUES (1);
INSERT INTO organisme VALUES (1, '21 rue d''escalibur', '44000', NOW(), NULL, 'bsimard@yopmail.com', NULL, '1', 'LogiSima', 'IT', NULL, '+33000000000', 'Nantes', NULL, NULL, 1, NULL, 1);
INSERT INTO organisme VALUES (2, '21 rue d''escalibur', '44000', NOW(), NULL, 'bsimard@yopmail.com', NULL, '1', 'LogiSima', 'Site Web', NULL, '+33000000000', 'Nantes', NULL, NULL, 1, 1, 1);
INSERT INTO organisme VALUES (3, '21 rue d''escalibur', '44000', NOW(), NULL, 'bsimard@yopmail.com', NULL, '1', 'LogiSima', 'Site Web', NULL, '+33000000000', 'Nantes', 0.0, 1.1, 1, 2, 1);

# --- !Downs
DELETE FROM member_useraccount;
DELETE FROM useraccount;
DELETE FROM member;
DELETE FROM organisme;
DELETE FROM organismemaster;
DELETE FROM themeactivite;

