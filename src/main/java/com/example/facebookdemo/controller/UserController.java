package com.example.facebookdemo.controller;

import com.example.facebookdemo.FacebookDemoApplication;
import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.exception.InvalidEmailException;
import com.example.facebookdemo.exception.InvalidPasswordException;
import com.example.facebookdemo.service.contrack.UserService;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import java.io.FileNotFoundException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.security.auth.login.LoginException;

@Controller
public class UserController extends BaseController implements WebMvcConfigurer {

    private final UserService userService;
    private static final String APPLICATION_NAME = "Facebook Nadq";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES =
            Collections.singletonList(CalendarScopes.CALENDAR_EVENTS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/login")
    public ModelAndView login() {

        return send("login.html");
    }

    @PreAuthorize("!isAuthenticated()")
    @PostMapping("/login")
    public ModelAndView login(@Validated @ModelAttribute("user") UserDTO userDTO
            , BindingResult result) throws LoginException {
        if (result.hasErrors()) {
            throw new LoginException();
        }
        return redirect("profile");
    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/login/oauth2")
    public void loginWithGoogle() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Calendar service =
				new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
						.setApplicationName(APPLICATION_NAME)
						.build();

		// List the next 10 events from the primary calendar.
		DateTime now = new DateTime(System.currentTimeMillis());

// insert event
		Event event1 = new Event();

		DateTime startDateTime = new DateTime("2022-10-04T17:30:00.000+03:00");
		EventDateTime start = new EventDateTime()
				.setDateTime(startDateTime)
				.setTimeZone("America/Los_Angeles");
		event1.setStart(start);

		DateTime endDateTime = new DateTime("2022-10-04T18:00:00.000+03:00");
		EventDateTime end = new EventDateTime()
				.setDateTime(endDateTime)
				.setTimeZone("America/Los_Angeles");
		event1.setEnd(end);

		String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
		event1.setRecurrence(Arrays.asList(recurrence));

		EventAttendee[] attendees = new EventAttendee[] {
				new EventAttendee().setEmail("hopelinesss@gmail.com")
		};
		event1.setAttendees(Arrays.asList(attendees));

		EventReminder[] reminderOverrides = new EventReminder[] {
				new EventReminder().setMethod("email").setMinutes(24 * 60),
				new EventReminder().setMethod("popup").setMinutes(10),
		};
		Event.Reminders reminders = new Event.Reminders()
				.setUseDefault(false)
				.setOverrides(Arrays.asList(reminderOverrides));
		event1.setReminders(reminders);

		String calendarId = "primary";
		event1 = service.events().insert(calendarId, event1).execute();
		System.out.printf("Event created: %s\n", event1.getHtmlLink());


		//find events
		Events events = service.events().list("primary")
				.setMaxResults(10)
				.setTimeMin(now)
				.setOrderBy("startTime")
				.setSingleEvents(true)
				.execute();
		List<Event> items = events.getItems();
		if (items.isEmpty()) {
			System.out.println("No upcoming events found.");
		} else {
			System.out.println("Upcoming events");
			for (Event event : items) {
				DateTime startEvent = event.getStart().getDateTime();
				if (start == null) {
					startEvent = event.getStart().getDate();
				}
				System.out.printf("%s (%s)\n", event.getSummary(), startEvent);
			}
		}
    }

    @PreAuthorize("!isAuthenticated()")
    @PostMapping("/register")
    public ModelAndView register(@Validated @ModelAttribute("user") UserDTO userDTO
            , BindingResult result
            , Model model) throws InvalidPasswordException, InvalidEmailException {
        if (result.hasErrors()) {
            model.addAttribute("message", "Please try again." );
            return send("message");
        }
        userService.register(userDTO);
        return redirect("login");
    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/register")
    public ModelAndView register() {
        return send("register");
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = FacebookDemoApplication.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8080).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
    }
}