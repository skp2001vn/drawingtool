import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class DrawingTool {

    private static final Logger logger = LogManager.getLogger(DrawingTool.class);

    public static void main(String... args) throws Exception {

        draw("data/input.txt", "data/output.txt");
    }

    /**
     *
     * @throws Exception
     */
    public static void draw(String inputFileName, String outputFileName)throws Exception  {

        final List<Command> commandList=new ArrayList<>();
        Files.lines(Paths.get(inputFileName))
                    .forEach(line -> populate(line, commandList));

        if (!validatePicture(commandList)){
            logger.error("there should be ONLY one canvas in the picture");
            throw new Exception("there should be ONLY one canvas in the picture");
        }

        Canvas canvas = getCanvas(commandList);

        //remove invalid inputs
        final List<Command> finalCommandList=commandList.stream()
                                                .filter(command -> command.validate(canvas.getWidth(),canvas.getHeight()))
                                                .collect(toList());

        Character[][] picture =new Character[canvas.getHeight()+2][canvas.getWidth()+2];
        drawPicture(picture, finalCommandList, outputFileName);
    }

    private static boolean validatePicture(List<Command> commandList){

        int numberOfCanvas=0;
        for (Command command : commandList) {
            if (command instanceof Canvas)
                numberOfCanvas++;
        }

        if (numberOfCanvas==1)
            return true;

        return false;
    }

    private static Canvas getCanvas(List<Command> commandList){

        for (Command command : commandList) {
            if (command instanceof Canvas)
                return (Canvas) command;
        }
        return null;
    }

    /**
     *
     * @param input
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
     *
     * @param picture
     */
    private static void drawPicture(Character[][] picture, List<Command> commandList, String outputFileName) throws Exception{

        List<String> output= new ArrayList<>();
        commandList.stream().forEach(command -> {
            command.draw(picture);
            output.addAll(generateResult(picture));
        });

        output.stream().forEach(System.out::println);

        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFileName)
                                                                , StandardCharsets.UTF_8)){
            for (String item : output) {
                writer.write(item);
                writer.newLine();
            }
        }catch(IOException ex){
            logger.error("drawPicture error",ex);
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
            logger.error("canvas errors", ex);
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
            logger.error("bucket errors", ex);
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
            logger.error("line errors", ex);
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
            logger.error("rectangle errors", ex);
        }
        return null;
    }



}
