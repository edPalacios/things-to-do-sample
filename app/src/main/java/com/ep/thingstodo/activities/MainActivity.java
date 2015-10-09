package com.ep.thingstodo.activities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ep.thingstodo.R;
import com.ep.thingstodo.adapter.ToDoListAdapter;
import com.ep.thingstodo.model.ToDoPOJO;
import com.ep.thingstodo.model.ToDoPOJO.Priority;
import com.ep.thingstodo.model.ToDoPOJO.Status;

public class MainActivity extends ListActivity {

	private static final int TODO_ITEM_REQUEST_CODE = 0;
	private static final String FILE_NAME = "ToDo.txt";
	private static final String TAG = "MainActivity";
	private static final int MENU_DELETE = Menu.FIRST;
	private static final int MENU_DUMP = Menu.FIRST + 1;
	private ToDoListAdapter toDoListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button footerView = initView();
		footerView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent addToDoIntent = new Intent(MainActivity.this, AddToDoActivity.class);
				startActivityForResult(addToDoIntent, TODO_ITEM_REQUEST_CODE);
			}
		});
		getListView().setAdapter(toDoListAdapter);
	}

	private Button initView() {
		toDoListAdapter = new ToDoListAdapter(getApplicationContext());
		getListView().setFooterDividersEnabled(true);
		Button footerView = (Button) getLayoutInflater().inflate(R.layout.footer_view, null);
		getListView().addFooterView(footerView);
		return footerView;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == TODO_ITEM_REQUEST_CODE) {
			if (resultCode == RESULT_OK) { //or  if (TODO_ITEM_REQUEST_CODE == requestCode && RESULT_OK == resultCode)
				ToDoPOJO toDoPOJO = new ToDoPOJO(data);
				toDoListAdapter.add(toDoPOJO);
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (toDoListAdapter.getCount() == 0)
			loadThingsToDo();
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveThingsToDo();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all");
		menu.add(Menu.NONE, MENU_DUMP, Menu.NONE, "Dump to log");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_DELETE:
			toDoListAdapter.clear();
			return true;
		case MENU_DUMP:
			dump();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void dump() {
		for (int i = 0; i < toDoListAdapter.getCount(); i++) {
			String data = ((ToDoPOJO) toDoListAdapter.getItem(i)).toLog();
			Log.i(TAG,	"Item " + i + ": " + data.replace(ToDoPOJO.ITEM_SEP, ","));
		}
	}

	private void loadThingsToDo() {
		BufferedReader reader = null;
		try {
			FileInputStream fis = openFileInput(FILE_NAME);
			reader = new BufferedReader(new InputStreamReader(fis));

			String title = null;
			String priority = null;
			String status = null;
			Date date = null;

			while (null != (title = reader.readLine())) {
				priority = reader.readLine();
				status = reader.readLine();
				date = ToDoPOJO.FORMAT.parse(reader.readLine());
				toDoListAdapter.add(new ToDoPOJO(title, Priority.valueOf(priority),
						Status.valueOf(status), date));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void saveThingsToDo() {
		PrintWriter writer = null;
		try {
			FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					fos)));

			for (int idx = 0; idx < toDoListAdapter.getCount(); idx++) {

				writer.println(toDoListAdapter.getItem(idx));

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				writer.close();
			}
		}
	}
}