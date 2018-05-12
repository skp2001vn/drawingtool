import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class DrawingToolTest {

    @Test
    public void correctPicture() throws Exception {

        DrawingTool.draw("input.txt");
        List<String> output= Files.lines(Paths.get(ClassLoader.getSystemResource("output.txt").toURI()))
                .collect(toList());
        List<String> expectedOutput=Files.lines(Paths.get(ClassLoader.getSystemResource("expecteOutput.txt").toURI()))
                .collect(toList());

        assertEquals(output.size(), expectedOutput.size());
        for(int i=0; i<output.size(); i++){

            assertEquals(output.get(i), expectedOutput.get(i));
        }
    }

    @Test(expected = Exception.class)
    public void noCanvas() throws Exception {

        DrawingTool.draw("inputNoCanvas.txt");
    }

    @Test(expected = Exception.class)
    public void moreThanOneCanvas() throws Exception {

        DrawingTool.draw("inputMoreThanOneCanvas.txt");
    }


    @Test
    public void invalidFormatInputs() throws Exception {

        DrawingTool.draw("inputInvalidFormat.txt");
        List<String> output= Files.lines(Paths.get(ClassLoader.getSystemResource("output.txt").toURI()))
                .collect(toList());
        List<String> expectedOutput=Files.lines(Paths.get(ClassLoader.getSystemResource("expecteOutput.txt").toURI()))
                .collect(toList());

        assertEquals(output.size(), expectedOutput.size());
        for(int i=0; i<output.size(); i++){

            assertEquals(output.get(i), expectedOutput.get(i));
        }
    }


}
