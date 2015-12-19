package AndroidFilesType.FilesType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import android.content.Context;

import com.example.translateok.MainActivity;

import AndroidFilesType.IFile;

public abstract class AndroidFile implements IFile {

	private InputStream stream;

	public abstract List<String> read() throws IOException;
	
	public abstract void setFileName(String name);
	
	public abstract boolean initInputStream();

	public abstract boolean isExist();
	
	protected AndroidFile() {

	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public InputStream getStream() {
		return this.stream;
	}

	protected String toUtf(String primalLine) {
		String compliteLine = null;
		try {
			byte[] bytes = primalLine.getBytes("ISO-8859-1");
			compliteLine = new String(bytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return compliteLine;
	}

	protected OutputStreamWriter initFileWritter(String fileName)
			throws FileNotFoundException {
		OutputStream outStream = null;
		Context context = MainActivity.context;

		outStream = context.openFileOutput(fileName, MODE_APPEND);

		return new OutputStreamWriter(outStream);
	}

	protected void write(String name, String writeString) throws IOException {
		OutputStreamWriter writer = this.initFileWritter(name);
		writer.write(writeString);
		writer.close();
	}

}
