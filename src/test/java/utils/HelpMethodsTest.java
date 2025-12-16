package utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class HelpMethodsTest {

    // 6. Teszt: Lejtő azonosítása (IsSlope) - Érvényes ID
    @Test
    void testIsSlopeTrue() {
        // A kódod alapján a 21-es ID lejtőnek számít
        assertTrue(HelpMethods.IsSlope(21), "A 21-es blokknak lejtőnek kell lennie.");
    }

    // 7. Teszt: Lejtő azonosítása (IsSlope) - Érvénytelen ID
    @Test
    void testIsSlopeFalse() {
        // A 0-s ID (levegő vagy sima blokk) nem lejtő
        assertFalse(HelpMethods.IsSlope(0), "A 0-s blokk nem lehet lejtő.");
    }

    // 8. Teszt: Pályán kívüli terület szolidnak számít-e (IsSolid)
    // Megjegyzés: Ez a teszt a Game.GAME_WIDTH konstansokat használja
    @Test
    void testIsSolidOutOfBounds() {
        int[][] emptyData = new int[10][10]; // Üres pálya adat

        // Pálya bal széle előtti koordináta (-10 pixel)
        boolean result = HelpMethods.IsSolid(-10, 50, emptyData);

        assertTrue(result, "A pályán kívüli területnek falnak (solid) kell lennie.");
    }
}
