package com.ep.thingstodo.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;

public class ToDoPOJO {

	public static final String ITEM_SEP = System.getProperty("line.separator");

	public enum Priority {
		LOW, MEDIUM, HIGH
	};

	public enum Status {
		PENDING, DONE
	};

	public final static String TITLE = "title";
	public final static String PRIORITY = "priority";
	public final static String STATUS = "status";
	public final static String DATE = "date";
	public final static String FILENAME = "filename";

	public final static SimpleDateFormat FORMAT
			= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

	private String title = "";
	private Priority priority = Priority.LOW;
	private Status status = Status.PENDING;
	private Date date = new Date();

	public ToDoPOJO(String title, Priority priority, Status status, Date date) {
		this.title = title;
		this.priority = priority;
		this.status = status;
		this.date = date;
	}

	public ToDoPOJO(Intent intent) {
		title = intent.getStringExtra(ToDoPOJO.TITLE);
		priority = Priority.valueOf(intent.getStringExtra(ToDoPOJO.PRIORITY));
		status = Status.valueOf(intent.getStringExtra(ToDoPOJO.STATUS));
		try {
			date = ToDoPOJO.FORMAT.parse(intent.getStringExtra(ToDoPOJO.DATE));
		} catch (ParseException e) {
			date = new Date();
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static void packageIntent(
			Intent intent,
			String title,
			Priority priority,
			Status status,
			String date) {

		intent.putExtra(ToDoPOJO.TITLE, title);
		intent.putExtra(ToDoPOJO.PRIORITY, priority.toString());
		intent.putExtra(ToDoPOJO.STATUS, status.toString());
		intent.putExtra(ToDoPOJO.DATE, date);
	
	}

	public String toString() {
		return title + ITEM_SEP + priority + ITEM_SEP + status + ITEM_SEP
				+ FORMAT.format(date);
	}

	public String toLog() {
		return "Title:" + title + ITEM_SEP + "Priority:" + priority
				+ ITEM_SEP + "Status:" + status + ITEM_SEP + "Date:"
				+ FORMAT.format(date);
	}

}
