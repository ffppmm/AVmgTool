package at.fpmedv.avmgtool;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainAdapter extends BaseAdapter {
	
	private static final String TAG = MainAdapter.class.getSimpleName();
	private final LayoutInflater inflator;
	private File vmgFileDirecory;
	private File[] vmgFiles = null;

	public MainAdapter(Context context) {
		super();
		inflator = LayoutInflater.from(context);
		// read SD Dir
		vmgFileDirecory = new File (Environment.getExternalStorageDirectory().getAbsolutePath() + "/avmgtool");
		// read Files
		if (isSDPresent()) {
			vmgFiles = vmgFileDirecory.listFiles();
		}
		Toast.makeText(context, "Nr. of Files found: " + vmgFiles.length, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public int getCount() {
		return vmgFiles.length;
		// return items.size();
	}

	@Override
	public Object getItem(int position) {
		return vmgFiles[position];
		// return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {
			convertView = inflator.inflate(R.layout.vmg_item_icon_text_text, parent, false);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.titletext);
			holder.summary = (TextView) convertView.findViewById(R.id.summarytext);
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (vmgFiles != null) {
			File item = vmgFiles[position];
			if (item != null) {
				VmgItem vmgItem = VmgItem.parse(item);
				holder.title.setText(vmgItem.tel + " (" + vmgItem.box + ")");
				holder.summary.setText(vmgItem.body);
				int IconId = vmgItem.box.equals(VmgItem.INBOX_NAME) ? R.drawable.mail_inbox : R.drawable.mail_sent;
				holder.icon.setImageResource(IconId);
			} else {
				return null;
			}
		} else {
			return null;
		}
		return convertView;
	}
	
	private boolean isSDPresent() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	static class ViewHolder {
		TextView title, summary;
		ImageView icon;
	}
}
