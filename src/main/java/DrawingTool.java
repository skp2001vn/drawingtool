import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * This is the main class to draw picture based on data/input.txt, which contain information about canvas, line, etc which needed to be drawn
 *      The result will be canvas, lines, etc which are in the data/output.txt
 * The application use the command design pattern to populate all the input commands into the command list, and draw picture based on this list
 * Also, the application use the recursive algorithm to do the bucket fillin
 *
 */
public class DrawingTool {

    public static void main(String... args) throws Exception {

        if (args.length != 2) {

            System.out.println("Please provide param for input file & param for output file such as data/input.txt, data/output.txt ");
            System.out.println("please provide param for input file & param for output file");
        }else{
            draw(args[0], args[1]);
        }
    }

    /**
     *  This is the main method to draw picture for the whole application
     *  Here are steps to do it:
     *      1. Read command from the input file and populate into the command list.
     *          One example of command is L 1 2 6 2 => the command is to draw line from (1,2) to (6,2)
     *          Note that if the input command is not in valid format,
     *              the application will ignore the command and not populate into the command list
     *      2. Validate to make sure the command list is not empty
     *                          , ONLY one canvas command in the command list
     *                          , and the canvas command should be the 1st command in the command list
     *          If valid, the application will proceed. Otherwise, throw exception and stop
     *      3. Retrieve the canvas object in the command list
     *      4. Remove all invalid commands in the command list.
     *          The rule to check in this case is just simply to check whether line, rectangle, bucket should be in the canvas area
     *      5. Draw canvas, line, etc based on the command list
     *
     * @throws Exception
     */
    public static void draw(String inputFileName, String outputFileName)throws Exception  {

        //1. Read command from the input file and populate into the command list
        final List<Command> commandList=new ArrayList<>();
        Files.lines(Paths.get(inputFileName))
                    .forEach(line -> populate(line, commandList));

        //2. Validate the command list
        if (!validatePicture(commandList)){
            System.out.println("something wrong with the input");
            throw new Exception("something wrong with the input");
        }

        //3. Retrieve the canvas object in the command list
        Canvas canvas = getCanvas(commandList);

        //4. Remove all invalid commands in the command list
        final List<Command> finalCommandList=commandList.stream()
                                                .filter(command -> command.validate(canvas.getWidth(),canvas.getHeight()))
                                                .collect(toList());

        //5. Draw canvas, line, etc based on the command list
        Character[][] picture =new Character[canvas.getHeight()+2][canvas.getWidth()+2];
        drawPicture(picture, finalCommandList, outputFileName);
    }

    /**
     *  Validate to make sure the command list is not empty
     *              , ONLY one canvas command in the command list
     *              , and the canvas command should be the 1st command in the command list
     *
     * @param commandList
     * @return
     */
    private static boolean validatePicture(List<Command> commandList){

        if (commandList.size()<=0){
            System.out.println("command list should not be empty");
            return false;
        }

        int numberOfCanvas=0;
        for (Command command : commandList) {
            if (command instanceof Canvas)
                numberOfCanvas++;
        }

        if (numberOfCanvas!=1){
            System.out.println("there should be ONLY one canvas command in the command list");
            return false;
        }

        if (!(commandList.get(0) instanceof Canvas)){
            System.out.println("the canvas command should be the 1st command in the command list");
            return false;
        }

        return true;
    }

    /**
     *
     * @param commandList
     * @return
     */
    private static Canvas getCanvas(List<Command> commandList){

        for (Command command : commandList) {
            if (command instanceof Canvas)
                return (Canvas) command;
        }
        return null;
    }

    /**
     *  1. Read command from the input file and populate into the command list.
     *      One example of command is L 1 2 6 2 => the command is to draw line from (1,2) to (6,2)
     *      Note that if the input command is not in valid format,
     *          the application will ignore the command and not populate into the command list
     * @param input
     * @param commandList
     */
    private static void populate(String input, List<Command> commandList) {

        String[] words = input.split(" ");
        Command command = null;

        if (words.length<1)
            return;

        if (words[0].equalsIgnoreCase(Constants.CANVAS_SYMBOL)){

            command = populateCanvas(words);
        }else if (words[0].equalsIgnoreCase(Constants.BUCKET_SYMBOL)){

            command = populateBucket(words);
        }else if (words[0].equalsIgnoreCase(Constants.LINE_SYMBOL)){

            command = populateLine(words);
        }else if (words[0].equalsIgnoreCase(Constants.RECTANGLE_SYMBOL)){

            command = populateRectangle(words);
        }

        if (command!=null)
            commandList.add(command);
    }

    /**
     * Draw canvas, line, etc based on the command list, and output the results into the output file
     *
     * @param picture
     * @param commandList
     * @param outputFileName
     * @throws Exception
     */
    private static void drawPicture(Character[][] picture
                                        , List<Command> commandList
                                        , String outputFileName) throws Exception{

        //Draw canvas, line, etc based on the command list
        List<String> output= new ArrayList<>();
        commandList.stream().forEach(command -> {
            command.draw(picture);
            output.addAll(generateResult(picture));
        });

        //Draw canvas, line, etc based on the command list
        output.stream().forEach(System.out::println);

        //Output the results into the output file
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFileName)
                                                                , StandardCharsets.UTF_8)){
            for (String item : output) {
                writer.write(item);
                writer.newLine();
            }
        }catch(IOException ex){
            System.out.println("drawPicture error" + ex);
        }
    }

    /**
     *
     * @param picture
     * @return
     */
    private static List<String> generateResult(Character[][] picture){

        return Arrays.stream(picture)
                    .map(line ->Arrays.stream(line)
                                        .map(c -> c.toString())
                                        .reduce("",(a, b) -> a+b ))
                    .collect(toList());
    }

    /**
     *
     * @param words
     */
    private static Command populateCanvas(String[] words) {
        try {
            Canvas canvas= new Canvas();
            canvas.setWidth(new Long(words[1]).intValue());
            canvas.setHeight(new Long(words[2]).intValue());

            return canvas;
        } catch (NumberFormatException ex) {
            System.out.println("canvas errors"+ ex);
        }
        return null;
    }

    /**
     *
     * @param words
     */
    private static Command populateBucket(String[] words) {
        try {
            Bucket bucket=new Bucket();
            bucket.setX(new Long(words[1]).intValue());
            bucket.setY(new Long(words[2]).intValue());
            bucket.setColour(new Character(words[3].charAt(0)));

            return bucket;
        } catch (NumberFormatException ex) {
            System.out.println("bucket errors" + ex);
        }
        return null;
    }

    /**
     *
     * @param words
     */
    private static Command populateLine(String[] words) {
        try {
            Line line =new Line();
            line.setX1(new Long(words[1]).intValue());
            line.setY1(new Long(words[2]).intValue());
            line.setX2(new Long(words[3]).intValue());
            line.setY2(new Long(words[4]).intValue());

            return line;
        } catch (NumberFormatException ex) {
            System.out.println("line errors"+ ex);
        }
        return null;
    }

    /**
     *
     * @param words
     */
    private static Command populateRectangle(String[] words) {
        try {
            Rectangle rectangle= new Rectangle();
            rectangle.setX1(new Long(words[1]).intValue());
            rectangle.setY1(new Long(words[2]).intValue());
            rectangle.setX2(new Long(words[3]).intValue());
            rectangle.setY2(new Long(words[4]).intValue());

            return rectangle;
        } catch (NumberFormatException ex) {
            System.out.println("rectangle errors"+ ex);
        }
        return null;
    }

}
