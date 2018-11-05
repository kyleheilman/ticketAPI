package tickets.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EventInfo {
    private List<String> tickets;

    public EventInfo(List<String> tickets) {
        this.tickets = tickets;
    }

    @JsonProperty("tickets")
    List<String> getTickets() {return tickets;}


}
