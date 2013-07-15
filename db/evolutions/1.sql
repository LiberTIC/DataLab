# --- !Ups

--- Table Member
INSERT INTO member VALUES (1,	null,	'http://a0.twimg.com/profile_images/1248011807/bsimard-logo_normal.png', 'Benoît Simard',	'bsimard@yopmail.com',	TRUE,	TRUE,	null,	'XXXXXXXX', 'http://wwww.bsimard.com',	'XXXXXXXX');
--- Table Useraccount (+ member_useraccount)
INSERT INTO useraccount VALUES (1, 'twitter', '197173482');
INSERT INTO member_useraccount VALUES (1, 1);
INSERT INTO useraccount VALUES (2, 'linkedin', 'QNK2UhSM7G');
INSERT INTO member_useraccount VALUES (1, 2);

# --- !Downs
DELETE FROM member_useraccount;
DELETE FROM useraccount;
DELETE FROM member;

