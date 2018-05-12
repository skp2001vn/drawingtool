
public class Canvas implements Command {

    int width, height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean validate(int width, int height){

        return true;
    }

    @Override
    public void draw(Character[][] picture){

        for(int i=0; i<height+2; i++){
            for(int j=0; j<width+2; j++) {

                if (i==0||i==(height+1)){

                    picture[i][j]=Constants.HORIZONTAL_CHARACTER;
                }else if (j==0||j==(width+1)){

                    picture[i][j]=Constants.VERTICAL_CHARACTER;
                }else{
                    picture[i][j]=Constants.BLANK_CHARACTER;

                }
            }
        }
    }

}
