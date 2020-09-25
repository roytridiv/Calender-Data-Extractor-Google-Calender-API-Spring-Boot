package com.tridiv.demo;

import com.google.api.services.calendar.model.Event;
import com.tridiv.demo.Domain.AvailableSlots;
import com.tridiv.demo.Domain.EventInfo;
import com.tridiv.demo.Service.CalenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CalenderTaskExtractorApplicationTests {



    @Autowired
    private CalenderService calenderService;
    private String code = "{CODE_GENERATED_FROM_RESPONSE}";

    @Test
    void contextLoads() {
    }


    @Test
    void checkEvents() {
        List<Event> events = calenderService.getItemList(code);
        List<EventInfo> eventList = calenderService.getEvents(events);
        for (EventInfo event : eventList) {
            System.out.println(event.getStartTime()+","
                    +event.getEndTime()+","
                    +event.getDescription()+","
                    +event.getDuration()
            );
        }
    }

    @Test
    void checkAvailableSlots() {
        List<Event> events = calenderService.getItemList(code);
        List<AvailableSlots> slotList = calenderService.getAvailableSlots(events);
        for (AvailableSlots event : slotList) {
            System.out.println(
                    event.getStartTime()+","
                    +event.getEndTime()+","
            );
        }
    }

}
