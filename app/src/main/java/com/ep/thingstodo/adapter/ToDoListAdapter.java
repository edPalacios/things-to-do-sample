package com.ep.thingstodo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.ep.thingstodo.R;
import com.ep.thingstodo.model.ToDoPOJO;

public class ToDoListAdapter extends BaseAdapter {

	static class ViewHolder {

		TextView titleView;
		CheckBox statusView;
		TextView priorityView;
		TextView dateView;
	}
	private final List<ToDoPOJO> thingToDoList = new ArrayList<ToDoPOJO>();

	private final Context context;
	private LayoutInflater inflater;

	private static final String TAG = "ToDoListAdapter";
	public ToDoListAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		return thingToDoList.size();
	}

	@Override
	public Object getItem(int pos) {
		return thingToDoList.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ToDoPOJO toDoPOJO = (ToDoPOJO) getItem(position);
//		RelativeLayout itemLayout = (RelativeLayout) LayoutInflater.from(context)
//														.inflate(R.layout.todo_item, null);
		ViewHolder h;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.todo_item, parent, false);
			h = new ViewHolder();

			h.titleView = (TextView) convertView.findViewById(R.id.titleView);
			h.titleView.setText(toDoPOJO.getTitle());

			h.statusView = (CheckBox) convertView.findViewById(R.id.statusCheckBox);
			h.statusView.setChecked(ToDoPOJO.Status.DONE.equals(toDoPOJO.getStatus()));
			h.statusView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					toDoPOJO.setStatus(isChecked ? ToDoPOJO.Status.DONE : ToDoPOJO.Status.PENDING);
				}
			});


			h.priorityView = (TextView) convertView.findViewById(R.id.priorityView);
			h.priorityView.setText(toDoPOJO.getPriority().toString());

			h.dateView = (TextView) convertView.findViewById(R.id.dateView);
			h.dateView.setText(ToDoPOJO.FORMAT.format(toDoPOJO.getDate()));

			convertView.setTag(h);
		} else {
			h = (ViewHolder) convertView.getTag();
			h.titleView.setText(toDoPOJO.getTitle());
			h.titleView.setText(toDoPOJO.getTitle());
			h.statusView.setChecked(ToDoPOJO.Status.DONE.equals(toDoPOJO.getStatus()));
			h.priorityView.setText(toDoPOJO.getPriority().toString());
			h.dateView.setText(ToDoPOJO.FORMAT.format(toDoPOJO.getDate()));
		}
		return convertView;
	}
	public void add(ToDoPOJO item) {
		thingToDoList.add(item);
		notifyDataSetChanged();

	}

	public void clear() {
		thingToDoList.clear();
		notifyDataSetChanged();
	}
}
