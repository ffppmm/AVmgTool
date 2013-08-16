package at.fpmedv.avmgtool;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ListActivity implements OnItemClickListener {

	private MainAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new MainAdapter(this);
		setListAdapter(adapter);
		getListView().setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO
		VmgItem item = (VmgItem) adapter.getItem(position);
		/**
		 	Intent viewIntent = new Intent(getApplicationContext(), VmgItemActivity.class);
			viewIntent.putExtra("vmgitem", item);
			startActivity(viewIntent);

		 * 
		 */
		
	}

}
