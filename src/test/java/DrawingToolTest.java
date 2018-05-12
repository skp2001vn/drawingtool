import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class DrawingToolTest {


    /**
     *
     * @throws Exception
     */
    @Test
    public void correctPicture() throws Exception {

        DrawingTool.draw("data/test/input.txt", "data/test/output.txt");
        this.validateOutputVsExpectedOutput();
    }

    /**
     *
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void noCanvas() throws Exception {

        DrawingTool.draw("data/test/inputNoCanvas.txt","data/test/output.txt");
    }

    /**
     *
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void moreThanOneCanvas() throws Exception {

        DrawingTool.draw("data/test/inputMoreThanOneCanvas.txt","data/test/output.txt");
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void invalidFormatInputs() throws Exception {

        DrawingTool.draw("data/test/inputInvalidFormat.txt","data/test/output.txt");
        this.validateOutputVsExpectedOutput();
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void inputInvalidBucket() throws Exception {

        DrawingTool.draw("data/test/inputInvalidBucket.txt", "data/test/output.txt");
        this.validateOutputVsExpectedOutput();
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void inputInvalidLine() throws Exception {

        DrawingTool.draw("data/test/inputInvalidLine.txt", "data/test/output.txt");
        this.validateOutputVsExpectedOutput();
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void inputInvalidRectangle() throws Exception {

        DrawingTool.draw("data/test/inputInvalidRectangle.txt", "data/test/output.txt");
        this.validateOutputVsExpectedOutput();
    }

    /**
     *
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
