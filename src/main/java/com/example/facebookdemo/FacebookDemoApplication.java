package com.example.facebookdemo;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttachment;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FacebookDemoApplication {

	private static final String APPLICATION_NAME = "Facebook Nadq";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	private static final List<String> SCOPES =
			Collections.singletonList(CalendarScopes.CALENDAR_EVENTS);
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

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

	public static void main(String[] args) throws IOException, GeneralSecurityException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Calendar service =
				new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
						.setApplicationName(APPLICATION_NAME)
						.build();

		// List the next 10 events from the primary calendar.
		DateTime now = new DateTime(System.currentTimeMillis());

// insert event
		Event event1 = new Event();

		String title = "Hello";
		String videoCallUrl = "http://ne]o.com";
		DateTime startDateTime = new DateTime("2022-10-12T12:30:00.000+03:00");
		EventDateTime start = new EventDateTime()
				.setDateTime(startDateTime)
				.setTimeZone("Europe/Sofia");
		event1.setStart(start);

		DateTime endDateTime = new DateTime("2022-10-12T15:00:00.000+03:00");
		EventDateTime end = new EventDateTime()
				.setDateTime(endDateTime)
				.setTimeZone("Europe/Sofia");
		event1.setEnd(end);

		String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=1"};
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

		List<EventAttachment> attachments = new ArrayList<>();
		attachments.add(new EventAttachment()
				.setTitle(title)
				.setFileUrl(videoCallUrl));
		event1.setAttachments(attachments);
		event1.setSummary(title);


		String calendarId = "primary";
		event1 = service.events().insert(calendarId, event1).execute();
		System.out.printf("Event created: %s\n", event1.getHtmlLink());


//		SpringApplication.run(FacebookDemoApplication.class, args);
	}
}
