/**
 * This class is to do bucket fill the entire area connected to (x,y) with colour
 */
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

    /**
     * This method is to check whether the input bucket dot is inside the picture area, which is canvas area in this case
     *
     * @param width
     * @param height
     * @return
     */
    @Override
    public boolean validate(int width, int height){

        return x<=width && y<=height;

    }

    @Override
    public void draw(Character[][] picture) {

        fillBucket(picture, Constants.BLANK_CHARACTER,colour,x,y);
    }

        /**
         * This method is to fill out recursively the entire area connected to (x,y) with colourToFill, which is "x" in this case
         * Based on the current input dot, the algorithm will check four surrounded dots((x+1,y), (x-1,y), (x,y+1), (x,y-1)
         *      to see whether these dots need to be filled based on their current colour.
         *      If any of these dots have the colourToReplace, which is " " in this case, colour of these dots will be updated by colourToReplace
         * Recursively, the algorithm will continue to check four dots surround each of these dot to replace/fill in.
         *      The algorithm will stop until there are no dots needed to be replaced.
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
     * Get the current colour of the dot (x,y) in the picture.
     *      If the dot is not in the picture, return null
     *      Otherwise, return the current colour
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
