package Session;

import java.util.ArrayList;
import java.util.List;

public class ConfigEntity {

	private int countAppStart;

	private int countUserBook;

	private int countUserWords;

	private int countUnknownWords;

	private String version = "TranslateOK 2.0";

	public final String DEFAULT = "Default.dic";

	private List<String> userBookList;

	public ConfigEntity() {
		this.userBookList = new ArrayList<String>();
		this.userBookList.add(DEFAULT);
	}

	public List<String> getUserBookList() {
		return this.userBookList;
	}

	public void addUsserBook(String book) {
		this.userBookList.add(book);
	}

	public int getCountAppStart() {
		return countAppStart;
	}

	public void setCountAppStart(int countAppStart) {
		this.countAppStart = countAppStart;
	}

	public int getCountUserBook() {
		return countUserBook;
	}

	public void setCountUserBook(int countUserBook) {
		this.countUserBook = countUserBook;
	}

	public int getCountUserWords() {
		return countUserWords;
	}

	public void setCountUserWords(int countUserWords) {
		this.countUserWords = countUserWords;
	}

	public int getCountUnknownWords() {
		return countUnknownWords;
	}

	public void setCountUnknownWords(int countUnknownWords) {
		this.countUnknownWords = countUnknownWords;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getJson() {
		StringBuilder jason = new StringBuilder();
		jason.append(this.version).append((char) 0xa);
		jason.append(this.countAppStart).append((char) 0xa);
		jason.append(this.countUserBook).append((char) 0xa);
		jason.append(this.countUserWords).append((char) 0xa);
		jason.append(this.countUnknownWords);

		return jason.toString();
	}
}
