import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

/**
 * this class is for all unit tests of the application
 */
public class DrawingToolTest {


    /**
     * This unit test is for the correct, standard input situation
     * @throws Exception
     */
    @Test
    public void correctPicture() throws Exception {

        DrawingTool.draw("data/test/input.txt", "data/test/output.txt");
        this.validateOutputVsExpectedOutput();
    }

    /**
     * This unit test is for no canvas input situation
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void noCanvas() throws Exception {

        DrawingTool.draw("data/test/inputNoCanvas.txt","data/test/output.txt");
    }

    /**
     * This unit test is for the empty input situation
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void inputEmpty() throws Exception {

        DrawingTool.draw("data/test/inputEmpty.txt","data/test/output.txt");
    }

    /**
     * This unit test is for no first canvas input situation
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void noFirstCanvas() throws Exception {

        DrawingTool.draw("data/test/inputNoFirstCanvas.txt","data/test/output.txt");
    }


    /**
     * This unit test is for more than one canvas input situation
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void moreThanOneCanvas() throws Exception {

        DrawingTool.draw("data/test/inputMoreThanOneCanvas.txt","data/test/output.txt");
    }

    /**
     * This unit test is for invalid format input situation.
     *  Line 6->10 in the file are considered invalid format, and therefore, will not be in the valid command list
     * @throws Exception
     */
    @Test
    public void invalidFormatInputs() throws Exception {

        DrawingTool.draw("data/test/inputInvalidFormat.txt","data/test/output.txt");
        this.validateOutputVsExpectedOutput();
    }

    /**
     * This unit test is for the invalid bucket input situation
     *  Line 6 in the file are considered invalid as it is out of the canvas area, and therefore, will be removed from the valid command list
     * @throws Exception
     */
    @Test
    public void inputInvalidBucket() throws Exception {

        DrawingTool.draw("data/test/inputInvalidBucket.txt", "data/test/output.txt");
        this.validateOutputVsExpectedOutput();
    }

    /**
     * This unit test is for the invalid line input situation
     *  Line 3 in the file are considered invalid as it is out of the canvas area, and therefore, will be removed from the valid command list
     * @throws Exception
     */
    @Test
    public void inputInvalidLine() throws Exception {

        DrawingTool.draw("data/test/inputInvalidLine.txt", "data/test/output.txt");
        this.validateOutputVsExpectedOutput();
    }

    /**
     * This unit test is for the invalid rectangle input situation
     *  Line 5 in the file are considered invalid as it is out of the canvas area, and therefore, will be removed from the valid command list
     * @throws Exception
     */
    @Test
    public void inputInvalidRectangle() throws Exception {

        DrawingTool.draw("data/test/inputInvalidRectangle.txt", "data/test/output.txt");
        this.validateOutputVsExpectedOutput();
    }

    /**
     * This method is to compare data result in the output.txt with the expect result in the expecteOutput.txt
     * @throws Exception
     */
    private void validateOutputVsExpectedOutput() throws Exception {

        List<String> output= Files.lines(Paths.get("data/test/output.txt"))
                .collect(toList());
        List<String> expectedOutput=Files.lines(Paths.get(ClassLoader.getSystemResource("expecteOutput.txt").toURI()))
                .collect(toList());

        assertEquals(output.size(), expectedOutput.size());
        for(int i=0; i<output.size(); i++){

            assertEquals(output.get(i), expectedOutput.get(i));
        }
    }


}
