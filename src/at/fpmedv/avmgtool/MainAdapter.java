package at.fpmedv.avmgtool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.DecoderException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainAdapter extends BaseAdapter {
	
	private List<VmgItem> items= new ArrayList<VmgItem>();
	private static final String LOGTAG = MainAdapter.class.getSimpleName();
	private final LayoutInflater inflator;
	private File vmgFileDirecory;
	private File[] vmgFiles = null;

	public MainAdapter(Context context) {
		super();
		inflator = LayoutInflater.from(context);
		// read SD Dir else Toast
		vmgFileDirecory = new File (Environment.getExternalStorageDirectory().getAbsolutePath() + "/avmgtool");
		// read Files
		if (isSDPresent()) {
			vmgFiles = vmgFileDirecory.listFiles();
		}
		Toast.makeText(context, "Nr of Files: " + vmgFiles.length, Toast.LENGTH_LONG).show();
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
			convertView = inflator.inflate(R.layout.vmg_item_text_text, parent, false);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.titletext);
			holder.summary = (TextView) convertView.findViewById(R.id.summarytext);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Log.d(LOGTAG, "vmgFiles: " + vmgFiles);

		if (vmgFiles != null) {
			File item = vmgFiles[position];
			Log.d(LOGTAG, "item: " + item);
			if (item != null) {
				VmgItem vmgItem = VmgItem.parse(item);
				holder.title.setText(vmgItem.tel + " (" + vmgItem.box + ")");
				holder.summary.setText(vmgItem.body);
				/**				String vmgFileContents = "";
				if (isSDPresent()) {
					try {
						vmgFileContents = readFile(item.getAbsolutePath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				holder.title.setText(decodedString);
					}
					if (lines[i].startsWith("TEXT;")) {
						String[] contents = lines[i].split(":", 2);
						try {
							decodedString = new String(org.apache.commons.codec.net.QuotedPrintableCodec.decodeQuotedPrintable(contents[1].getBytes()), "UTF-8");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (DecoderException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						holder.summary.setText(decodedString);
					}
				}
	//			holder.icon.setImageResource(item.getIcon());
				**/
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
	}
}
