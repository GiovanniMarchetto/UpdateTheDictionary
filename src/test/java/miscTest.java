import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class miscTest {

    @Test
    void misc_token() {
        Miscellaneous.testTitleFormatting("TEST-TOKEN");

        String token = "good";
        String normToken = Miscellaneous.getNormalizeToken(token);
        assertEquals("good",normToken);


        Miscellaneous.testTitleFormatting("END TEST-TOKEN");
    }
}
