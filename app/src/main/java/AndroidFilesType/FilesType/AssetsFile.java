package AndroidFilesType.FilesType;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.res.AssetManager;

import com.example.translateok.MainActivity;

import Session.SessionCommons.ISessionLoader;

public class AssetsFile extends AndroidFile {

	private InputStream stream;

	private String fileName;

	public AssetsFile() {
		this.fileName = D_ASSETS;
	}

	public AssetsFile(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void read(ISessionLoader sessionLoader) throws IOException {
		StringBuilder line = new StringBuilder();

		int check;
		while ((check = stream.read()) > -1) {
			if (check == 10) {
				sessionLoader.nextLine(this.toUtf(line.toString()));
				line.delete(0, line.length());
				continue;
			}

			line.append((char) check);
		}

		stream.close();
	}

	@Override
	public void write(String writeString) throws IOException {
		String name = D_SDCARD + "texst";
		this.write(name, writeString);
	}

	@Override
	public boolean initInputStream() {
		AssetManager manager = MainActivity.assetManager;
		try {
			this.stream = manager.open(this.fileName);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean isExist() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFileName(String name) {
		this.fileName = name;
	}

	@Override
	public List<String> read() throws IOException {
		return null;
	}

}
