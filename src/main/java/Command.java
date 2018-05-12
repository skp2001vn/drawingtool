/**
 * This interface is to provide commands to validate, draw canvas, line, etc for the whole application
 */
public interface Command {

    public void draw(Character[][] picture);

    public boolean validate(int width, int height);

}
