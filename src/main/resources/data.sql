/*CREATE TABLE Category (
  category_id       INTEGER PRIMARY KEY,
  description       VARCHAR(64) NOT NULL,
  reference_id      INTEGER NOT NULL DEFAULT 0
);*/

INSERT INTO Category (category_id, description, reference_id) VALUES (100, 'A', 0);
INSERT INTO Category (category_id, description, reference_id) VALUES (110, 'A-1', 100);
INSERT INTO Category (category_id, description, reference_id) VALUES (111, 'A-11', 110);
INSERT INTO Category (category_id, description, reference_id) VALUES (112, 'A-12', 110);
INSERT INTO Category (category_id, description, reference_id) VALUES (120, 'A-2', 100);
INSERT INTO Category (category_id, description, reference_id) VALUES (130, 'A-3', 100);
INSERT INTO Category (category_id, description, reference_id) VALUES (200, 'B', 0);
INSERT INTO Category (category_id, description, reference_id) VALUES (210, 'B-1', 200);
INSERT INTO Category (category_id, description, reference_id) VALUES (300, 'C', 0);
INSERT INTO Category (category_id, description, reference_id) VALUES (310, 'C-1', 300);
INSERT INTO Category (category_id, description, reference_id) VALUES (320, 'C-2', 300);
