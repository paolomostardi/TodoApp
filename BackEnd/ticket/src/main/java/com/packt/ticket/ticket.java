    package com.packt.ticket;

    import java.sql.Date;

    // record di ticket 
    // usato come tabella SQL 

    public record ticket(
        Long id, 
        String proprietario, 
        String descrizione, 
        String stato, 
        int priorita, 
        Date data_inizio,
        Date data_fine, 
        Date data_deleted
        

    ) {}
