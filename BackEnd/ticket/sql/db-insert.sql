\c tickets

INSERT INTO ticket (proprietario, descrizione, stato, priorita, data_inizio, data_fine, data_deleted)
VALUES 
( 'Paolo', 'Sviluppo del sistema di gestione', 'progress', 1, '2024-06-01', '2024-06-30', NULL),
( 'Giovanni', 'Revisione codice e test', 'resolved', 5, '2024-05-15', '2024-05-25', NULL),
('Luca', 'Progettazione database', 'todo', 8, '2024-07-01', '2024-07-15', NULL);
