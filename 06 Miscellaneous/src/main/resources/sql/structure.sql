CREATE TABLE player (
  id      SERIAL,
  name    VARCHAR(100),
  surname VARCHAR(100),
  PRIMARY KEY (id)
);

CREATE TABLE team (
  id   SERIAL,
  name VARCHAR(100),
  PRIMARY KEY (id)
);

ALTER TABLE player
  ADD COLUMN team_id INTEGER;

ALTER TABLE player
  ADD CONSTRAINT fk_team
FOREIGN KEY (team_id)
REFERENCES team (id);

INSERT INTO team (name)
VALUES
  ('Liverpool F.C.');

UPDATE player
SET team_id = 1;