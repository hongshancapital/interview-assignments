/*
    Inserts a new User record.
*/
INSERT INTO domains(short_domain,real_domain)
VALUES($1,$2)
RETURNING *
