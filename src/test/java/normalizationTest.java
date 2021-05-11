import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class normalizationTest {

    @Test
    void theMaiusc() {
        assertEquals("the",Dictionary.getNormalizeToken("THE"));
    }

    @Test
    void theDot() {
        assertEquals("the",Dictionary.getNormalizeToken("ThE."));
    }

    @Test
    void thePar() {
        assertEquals("the",Dictionary.getNormalizeToken("(ThE."));
    }
}
