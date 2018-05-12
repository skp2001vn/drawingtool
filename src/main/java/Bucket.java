
public class Bucket implements Command {

    int x, y;
    Character colour;


    public Character getColour() {
        return colour;
    }

    public void setColour(Character colour) {
        this.colour = colour;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean validate(int width, int height){

        return x<=width && y<=height;

    }

    @Override
    public void draw(Character[][] picture) {

        fillBucket(picture, Constants.BLANK_CHARACTER,colour,x,y);
    }

        /**
         *
         * @param picture
         * @param colourToReplace
         * @param colourToFill
         * @param x
         * @param y
         */
    private static void fillBucket(Character[][] picture, Character colourToReplace, Character colourToFill, int x, int y) {

        Character colour = getColourAt(picture, x, y);
        if (colour == colourToReplace) {
            picture[y][x] = colourToFill;
            fillBucket(picture, colourToReplace, colourToFill, x + 1, y);
            fillBucket(picture, colourToReplace, colourToFill, x - 1, y);
            fillBucket(picture, colourToReplace, colourToFill, x, y + 1);
            fillBucket(picture, colourToReplace, colourToFill, x, y - 1);
        }
    }

    /**
     *
     * @param picture
     * @param x
     * @param y
     * @return
     */
    private static Character getColourAt(Character[][] picture, int x, int y) {

        if (x < 0 || y < 0 || y > picture.length || x > picture[y].length) {
            return null;
        } else {
            return picture[y][x];
        }
    }

}
