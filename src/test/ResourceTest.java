package test;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResourceTest {
    @Test
    void Test() {
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(ResourceTest.class.getClassLoader().getResourceAsStream("images/tankL.gif")));
            assertNotNull(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
