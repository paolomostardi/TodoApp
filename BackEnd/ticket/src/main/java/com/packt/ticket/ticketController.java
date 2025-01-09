package com.packt.ticket;
import java.sql.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/ticket")
@RestController
@EnableSwagger2
/**
 *  Classe usata per gestire richeste API 
 *  
 *  Ticket ha le seguenti colonne : 
 * 
 *  id SERIAL PRIMARY KEY,
    proprietario character varying(255),
    descrizione character varying(500),
    stato  character varying(255),
    priorita integer,
    data_inizio date,
    data_fine date,
    data_deleted date

 * */



public class ticketController {

    private ticketService service;

    public ticketController(JdbcTemplate jdbcTemplate){
        this.service = new ticketService(jdbcTemplate);
    }

    @GetMapping("/countAllTickets")
    public int countAllTickets() {
        return this.service.getTicketCount();
    }    

    // quando un ticket è creato lo stato è settato su todo
    // la struttura di ticket propetario, id, descrizione, stato, priorita, data inizio, data_fine

    @PostMapping("/insert")
    public int insertTicket(@RequestBody ticket newTicket){
        System.out.println(newTicket);
        // se data iniziale non è stata settata, il ticket è reinstanziato con la data_inizio alla data attuale 
        if (newTicket.data_inizio() == null) {
                newTicket = new ticket(
                newTicket.id(),
                newTicket.proprietario(),
                newTicket.descrizione(),
                newTicket.stato(), 
                newTicket.priorita(),
                new Date(System.currentTimeMillis()), // unica parte che cambia dell'oggetto
                newTicket.data_fine(),
                null // il ticket non puo essere inserito deleted 
                );
            }
        System.out.println(newTicket);
        return this.service.insertTicket(newTicket);
    }

    @GetMapping("/allTickets")
    public List<ticket> allTickets() {
        return this.service.getAllTicket();
    }
    
    @GetMapping("/allNonDeletedTickets")
    public List<ticket> allNonDeletedTickets() {
        return this.service.getAllNonDeleteTicket();
    }    

    @PutMapping("deleteTicket/{id}")
    public int putDeleteTicket(@PathVariable int id) {
        return this.service.deleteTicket(id);
    }    

    @PutMapping("updateTicket/{id}")
    public int putUpdateTicket(@PathVariable int id, @RequestBody ticket updatedTicket) {
        System.out.println(updatedTicket);
        System.out.println(id);
        return this.service.updateTicket(id, updatedTicket);
    }    


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


}


