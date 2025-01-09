package com.packt.ticket;
import java.sql.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;


// classe usata per processare richieste al database
// fa da ponte al API end point e il database SQL 

@Service
public class ticketService {
    private final JdbcTemplate jdbcTemplate;

    public ticketService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // lancia queri che conta tutti i ticket totali
    public int getTicketCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ticket", Integer.class);
    }

    // metodo che lancia una SELECT su la tabella TICKET prende TUTTI i ticket 
    public List<ticket> getAllTicket() {
        String sql = "SELECT id, proprietario, descrizione, stato, priorita, data_inizio, data_fine, data_deleted FROM ticket";

        RowMapper<ticket> rowMapper = (rs, rowNum) -> new ticket(
            rs.getLong("id"),
            rs.getString("proprietario"),
            rs.getString("descrizione"),
            rs.getString("stato"),
            rs.getInt("priorita"),
            rs.getDate("data_inizio"),
            rs.getDate("data_fine"),
            rs.getDate("data_deleted")
        );
        return jdbcTemplate.query(sql, rowMapper);
    }

    // metodo che lancia una SELECT su la tabella TICKET prende SOLO i ticket che non sono eliminati
    public List<ticket> getAllNonDeleteTicket() {
        String sql = "SELECT id, proprietario, descrizione, stato, priorita, data_inizio, data_fine, data_deleted FROM ticket WHERE data_deleted is NULL";

        RowMapper<ticket> rowMapper = (rs, rowNum) -> new ticket(
            rs.getLong("id"),
            rs.getString("proprietario"),
            rs.getString("descrizione"),
            rs.getString("stato"),
            rs.getInt("priorita"),
            rs.getDate("data_inizio"),
            rs.getDate("data_fine"),
            rs.getDate("data_deleted")
        );

        return jdbcTemplate.query(sql, rowMapper);
    }


    // Inserisce ticket nel DB, lo stato è settato a todo automaticamente 
    public int insertTicket(ticket newTicket) {
        String sql = "INSERT INTO ticket (proprietario, descrizione, stato, priorita, data_inizio, data_fine, data_deleted) " +
                     "VALUES (?, ?, ?, ?, ?, ?,?)";

        System.out.println(newTicket);

        return jdbcTemplate.update(sql,
                newTicket.proprietario(),
                newTicket.descrizione(),
                newTicket.stato(),
                newTicket.priorita(),
                newTicket.data_inizio(),
                newTicket.data_fine(),
                null
        );
    }

    public int updateTicket( int id, ticket updatedTicket) {
        String query = "UPDATE ticket SET proprietario = ?, descrizione = ?, stato = ?, priorita = ?, data_fine = ? WHERE id = ?";
        return jdbcTemplate.update(
            query,
            updatedTicket.proprietario(),
            updatedTicket.descrizione(),
            updatedTicket.stato(),
            updatedTicket.priorita(),
            updatedTicket.data_fine(),
            id
        );
    }

    // TODO check for deleted ticket already
    public int deleteTicket(int id){
        String query = "UPDATE ticket SET data_deleted = ? WHERE id = ?";
        return jdbcTemplate.update(
            query,
            new Date(System.currentTimeMillis()),
            id
        );
         
    }

}
