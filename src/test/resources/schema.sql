
CREATE TABLE speciality (
  id BIGINT NOT NULL,
   name VARCHAR(255),
   CONSTRAINT pk_speciality PRIMARY KEY (id)
);

CREATE TABLE vet (
  id BIGINT NOT NULL,
   name VARCHAR(255),
   CONSTRAINT pk_vet PRIMARY KEY (id)
);

CREATE TABLE vet_speciality (
  speciality_id BIGINT NOT NULL,
   vet_id BIGINT NOT NULL,
   CONSTRAINT pk_vet_speciality PRIMARY KEY (speciality_id, vet_id)
);

ALTER TABLE vet_speciality ADD CONSTRAINT fk_vetspe_on_speciality FOREIGN KEY (speciality_id) REFERENCES speciality (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE vet_speciality ADD CONSTRAINT fk_vetspe_on_vet FOREIGN KEY (vet_id) REFERENCES vet (id) ON UPDATE NO ACTION ON DELETE NO ACTION;