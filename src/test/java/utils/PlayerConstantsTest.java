package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static utils.Constants.PlayerConstants.IDLE;
import static utils.Constants.PlayerConstants.RUNNING;
import static utils.Constants.PlayerConstants.getSpriteAmount;

public class PlayerConstantsTest {

    // 9. Teszt: Futás animáció sprite-jainak száma
    @Test
    void testGetSpriteAmountRunning() {
        int amount = getSpriteAmount(RUNNING);
        assertEquals(2, amount, "A futás animációnak 2 fázisból kell állnia.");
    }

    // 10. Teszt: Alapállapot (IDLE) animáció sprite-jainak száma
    @Test
    void testGetSpriteAmountIdle() {
        int amount = getSpriteAmount(IDLE);
        assertEquals(4, amount, "Az IDLE animációnak 4 fázisból kell állnia.");
    }

    // 11. Teszt: Ismeretlen állapot
    @Test
    void testGetSpriteAmountDefault() {
        int amount = getSpriteAmount(999); // Nem létező állapot
        assertEquals(1, amount, "Ismeretlen állapotra 1-et kell visszaadnia.");
    }
}
