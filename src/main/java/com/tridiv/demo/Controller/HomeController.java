package com.tridiv.demo.Controller;

/*
Online Resources Used
https://github.com/a2cart/google-calendar-api
https://developers.google.com/calendar/quickstart/java
*/


import com.google.api.services.calendar.model.Event;
import com.tridiv.demo.Domain.AvailableSlots;
import com.tridiv.demo.Domain.EventInfo;
import com.tridiv.demo.Service.CalenderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller

public class HomeController {

    private final CalenderService calenderService;
    public List<Event> eventItems;

    public HomeController(CalenderService calenderService) {
        this.calenderService = calenderService;
    }

    @RequestMapping("/")
    public String home() {
        return "home.jsp";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public RedirectView googleConnectionLogin(HttpServletRequest request) throws Exception {
        RedirectView redirectView = new RedirectView(calenderService.authorize());
        return redirectView;
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public RedirectView googleConnectionStatus(HttpServletRequest request) throws Exception {
        return new RedirectView(calenderService.authorize());
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET, params = "code")
    public String oauth2Callback(@RequestParam(value = "code") String code, Model model) {
         eventItems = calenderService.getItemList(code);
        List<EventInfo> eventInfoItems = calenderService.getEvents(eventItems);
        List<AvailableSlots> availableSlotItems = calenderService.getAvailableSlots(eventItems);
        model.addAttribute("events", eventInfoItems);
        model.addAttribute("availableSlots", availableSlotItems);
        return "events.jsp";
    }


}
