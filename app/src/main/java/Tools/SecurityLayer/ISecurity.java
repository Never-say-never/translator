package Tools.SecurityLayer;

/**
 * Created by java_monkey on 2/22/2016.
 */
public interface ISecurity {

    public abstract void encode(char[] message);

    public abstract Object decode();

}
