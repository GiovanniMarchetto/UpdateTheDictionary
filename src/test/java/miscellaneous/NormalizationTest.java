package miscellaneous;

import operations.Normalization;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NormalizationTest {

    @Test
    void cat() {
        assertEquals("cat", Normalization.getNormalizeToken("cat"));
    }

    @Test
    void catM() {
        String w = "CAT";
        String w2 = Normalization.getNormalizeToken(w);
        assertEquals("cat",w2 );
//        System.out.println(w+" ----  "+w2);
    }

    @Test
    void catParDot() {
        assertEquals("cat", Normalization.getNormalizeToken("(CaT."));
    }

}
