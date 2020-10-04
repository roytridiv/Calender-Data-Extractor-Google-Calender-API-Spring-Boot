package com.tridiv.demo.Service;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.tridiv.demo.Domain.AvailableSlots;
import com.tridiv.demo.Domain.EventInfo;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CalenderService {


    private static final String APPLICATION_NAME = "Calendar Event Extractor";
    private static HttpTransport httpTransport;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    GoogleClientSecrets clientSecrets;
    GoogleAuthorizationCodeFlow flow;
    Credential credential;

    private final String clientId = "{client-id}" ;
    private final String clientSecret = "{client-secret}";
    private final String redirectURI = "http://localhost:8080/events";


    private List<EventInfo> myEventInfos ;
    private List<AvailableSlots> myAvailableSlots ;
    private List<Event> items = null;

    public List<Event> getItemList(String code) {
        System.out.println(code);
        try {
            TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectURI).execute();
            System.out.println(response);
            credential = flow.createAndStoreCredential(response, "userID");

            Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            DateTime now = new DateTime(System.currentTimeMillis());
            com.google.api.services.calendar.model.Events calEvents = service.events().list("primary")
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();

            items = calEvents.getItems();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return items;
    }


    public List<EventInfo> getEvents(List<Event> items) {
        myEventInfos = new ArrayList<>();
        try {
            if (!items.isEmpty() ) {
                System.out.println(items.size());
                for (Event event : items) {
                    String start = timeExtract(event.getStart().getDateTime().toString());
                    String end = timeExtract(event.getEnd().getDateTime().toString());
                    Long duration = duration(start, end);
                    EventInfo eventInfo = new EventInfo(convertTimeTo12Hour(start), convertTimeTo12Hour(end), event.getDescription(), event.getSummary(), duration);

                    myEventInfos.add(eventInfo);

                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return myEventInfos;
    }


    public List<AvailableSlots> getAvailableSlots(List<Event> items) {

        myAvailableSlots = new ArrayList<>();
        try {
            String endTime = timeExtract(items.get(0).getEnd().getDateTime().toString());
            String nextStartTime = "";

            if (!items.isEmpty()) {
                for (int i = 1; i < items.size(); i++) {
                    nextStartTime = timeExtract(items.get(i).getStart().getDateTime().toString());
                    if (duration(endTime, nextStartTime) > 0) {

                        AvailableSlots availableSlots = new AvailableSlots(convertTimeTo12Hour(endTime), convertTimeTo12Hour(nextStartTime));

                        myAvailableSlots.add(availableSlots);

                    }
                    endTime = timeExtract(items.get(i).getEnd().getDateTime().toString());
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return myAvailableSlots;
    }


    public String authorize() throws Exception {
        AuthorizationCodeRequestUrl authorizationUrl;
        GoogleClientSecrets.Details web = new GoogleClientSecrets.Details();
        web.setClientId(clientId);
        web.setClientSecret(clientSecret);
        clientSecrets = new GoogleClientSecrets().setWeb(web);
        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets,
                Collections.singleton(CalendarScopes.CALENDAR))
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .build();

        authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectURI);
        System.out.println(authorizationUrl);
        return authorizationUrl.build();
    }

    public Long duration(String start, String end) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date1 = format.parse(start);
        Date date2 = format.parse(end);
        return date2.getTime() - date1.getTime();

    }

    public String timeExtract(String s) {
        return s.substring(s.indexOf("T") + 1, s.indexOf("T") + 9);
    }

    public String convertTimeTo12Hour(String t) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
        LocalTime time = LocalTime.parse(t);
        String time12Hour = time.format(timeFormatter);
        return time12Hour;
    }


}
