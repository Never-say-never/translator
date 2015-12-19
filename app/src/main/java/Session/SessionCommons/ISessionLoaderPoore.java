package Session.SessionCommons;

public interface ISessionLoaderPoore {

	public abstract void nextLine(String line);

	public abstract String getContent();

	public abstract void freeContent();
}
