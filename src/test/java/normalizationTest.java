import miscellaneous.Miscellaneous;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class normalizationTest {

    @Test
    void cat() {
        assertEquals("cat", Miscellaneous.getNormalizeToken("cat"));
    }

    @Test
    void catM() {
        assertEquals("cat", Miscellaneous.getNormalizeToken("CAT"));
    }

    @Test
    void catParDot() {
        assertEquals("cat", Miscellaneous.getNormalizeToken("(CaT."));
    }

}
