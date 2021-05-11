import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class normalizationTest {

    @Test
    void theMaiusc() {
        assertEquals("the", Miscellaneous.getNormalizeToken("THE"));
    }

    @Test
    void theDot() {
        assertEquals("the", Miscellaneous.getNormalizeToken("ThE."));
    }

    @Test
    void thePar() {
        assertEquals("the", Miscellaneous.getNormalizeToken("(ThE."));
    }
}
