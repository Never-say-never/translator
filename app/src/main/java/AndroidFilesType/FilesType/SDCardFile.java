package AndroidFilesType.FilesType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import Session.SessionCommons.ISessionLoader;

import com.example.translateok.MainActivity;

public class SDCardFile extends AndroidFile {

	private InputStream stream;

	private String fileName;

	public SDCardFile() {
		this.fileName = D_APPCONF + APP_CONF;
	}

	public SDCardFile(String fileName) {
		this.fileName = D_SDCARD + fileName;
	}

	public void setFileName(String name) {
		this.fileName = D_SDCARD + name;
	}

	@Override
	public void read(ISessionLoader sessionLoader) {
		BufferedReader reader = this.openSd();
		String lineFromFile = null;
		try {
			while ((lineFromFile = reader.readLine()) != null) {
				sessionLoader.nextLine(this.toUtf(lineFromFile));
			}
			stream.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		stream = null;
		reader = null;
	}

	@Override
	public void write(String writeString) throws IOException {
		this.write(this.fileName, writeString);
	}

	@Override
	public boolean initInputStream() {
		try {
			stream = MainActivity.context.openFileInput(this.fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private BufferedReader openSd() {
		InputStreamReader streamReader = null;
		try {
			streamReader = new InputStreamReader(stream, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return new BufferedReader(streamReader);
	}

	@Override
	public boolean isExist() {
		this.initInputStream();
		return this.openSd() != null;
	}

	@Override
	public List<String> read() throws IOException {
		List<String> tabsContent = new ArrayList<String>();
		BufferedReader reader = this.openSd();
		String lineFromFile   = null;
		try {
			while ((lineFromFile = reader.readLine()) != null) {
				//tabsContent.add(this.toUtf(lineFromFile));
				tabsContent.add(lineFromFile);
			}
			stream.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		stream = null;
		reader = null;
		
		return tabsContent;
	}

}
