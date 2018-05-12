/**
 * This class is to create a new rectangle from (x1,y1) to (x2,y2)
 */
public class Rectangle implements Command {

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

    /**
     * This method is to check whether the input rectangle is inside the picture area, which is canvas area in this case
     *
     * @param width
     * @param height
     * @return
     */
    @Override
    public boolean validate(int width, int height){

        return x1<=width && x2<=width
                && y1<=height && y2<=height;
    }

    /**
     * This method is to draw rectangle from (x1,y1) to (x2,y2)
     * @param picture
     */
    @Override
    public void draw(Character[][] picture) {

        for(int i=y1; i<=y2; i++){
            for(int j=x1; j<=x2; j++){

                if (i==y1||i==y2){

                    picture[i][j]= Constants.DRAWN_CHARACTER;
                }else if (j==x1||j==x2){

                    picture[i][j]= Constants.DRAWN_CHARACTER;
                }
            }
        }


    }

}
