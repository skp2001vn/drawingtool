
public interface Command {

    public void draw(Character[][] picture);

    public boolean validate(int width, int height);

    //public boolean validateFormat(String line);

}
