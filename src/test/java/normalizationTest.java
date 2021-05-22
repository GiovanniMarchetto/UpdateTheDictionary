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
        String w = "CAT";
        String w2 = Miscellaneous.getNormalizeToken(w);
        assertEquals("cat",w2 );
//        System.out.println(w+" ----  "+w2);
    }

    @Test
    void catParDot() {
        assertEquals("cat", Miscellaneous.getNormalizeToken("(CaT."));
    }

}
