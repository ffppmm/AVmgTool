package at.fpmedv.avmgtool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.codec.DecoderException;

public class VmgItem {
	String box = "";
	String body = "";
	String tel = "";
	String date = "";
	
	static final String INBOX_NAME = "inbox";
	static final String OUTBOX_NAME = "sent";
	
	public static VmgItem parse(File f) {
	    BufferedReader reader = null;
	    Vector<String> lines = new Vector<String>();
		try {
			reader = new BufferedReader( new FileReader (f.getAbsolutePath()));
		    String line = null;
		    while( ( line = reader.readLine() ) != null ) {
		        lines.add(line);
		    }
		} catch (FileNotFoundException e) {
			// TODO: should never happen
		} catch (IOException e) {
			// TODO: could happen ;)
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO: terrible problems
				}	
			}
		}
		// loop over lines and fill fields
		VmgItem returnItem = new VmgItem();
		Iterator<String> i = lines.iterator();
		while (i.hasNext()) {
			String cLine = i.next();
			if (cLine.startsWith("X-IRMC-BOX")) {
				returnItem.box = cLine.indexOf("INBOX") != -1 ? INBOX_NAME : OUTBOX_NAME;
			} else if (cLine.startsWith("TEXT")) {
				String[] contents = cLine.split(":", 2);
				try {
					returnItem.body = new String(org.apache.commons.codec.net.QuotedPrintableCodec.decodeQuotedPrintable(contents[1].getBytes()), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
				} catch (DecoderException e) {
					// TODO Auto-generated catch block
				}
			} else if (cLine.startsWith("TEL")) {
				String[] contents = cLine.split(":", 2);
				returnItem.tel = contents[1];
			} else if (cLine.startsWith("Date")) {
				String[] contents = cLine.split(":", 2);
				returnItem.date = contents[1];				
			}
		}
	    return returnItem;
	}
	
}
