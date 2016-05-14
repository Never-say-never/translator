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

	/**
	 *
	 */
	protected AndroidFile() {

	}

	/**
	 *
	 * @param stream
	 */
	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	/**
	 *
	 * @return
	 */
	public InputStream getStream() {
		return this.stream;
	}

	/**
	 *
	 * @param primalLine
	 * @return
	 */
	protected String toUtf(String primalLine) {
		String completeLine = null;
		try {
			byte[] bytes = primalLine.getBytes();
			completeLine = new String(bytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return completeLine;
	}

	/**
	 *
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	protected OutputStreamWriter initFileWriter(String fileName)
			throws FileNotFoundException {
		OutputStream outStream = null;
		Context context = MainActivity.context;

		outStream = context.openFileOutput(fileName, MODE_APPEND);

		return new OutputStreamWriter(outStream);
	}

	/**
	 *
	 * @param name
	 * @param writeString
	 * @throws IOException
	 */
	protected void write(String name, String writeString) throws IOException {
		OutputStreamWriter writer = this.initFileWriter(name);
		writer.write(writeString);
		writer.close();
	}

}
