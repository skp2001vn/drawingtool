
public class Line implements Command {

    int x1, y1, x2, y2;

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

/*
    @Override
    public boolean validateFormat(String line){

        String[] words = line.split(" ");

        if (words.length!=5)
            return false;

        if (!words[0].equalsIgnoreCase(Constants.LINE_SYMBOL))
            return false;

        try {
            new Long(words[1]).intValue();
            new Long(words[2]).intValue();
            new Long(words[3]).intValue();
            new Long(words[4]).intValue();
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
*/

    @Override
    public boolean validate(int width, int height){

        return x1<=width && x2<=width
                && y1<=height && y2<=height;
    }

    @Override
    public void draw(Character[][] picture) {

        for(int i=y1; i<=y2; i++){
            for(int j=x1; j<=x2; j++){

                picture[i][j]= Constants.DRAWN_CHARACTER;
            }
        }
    }
}
