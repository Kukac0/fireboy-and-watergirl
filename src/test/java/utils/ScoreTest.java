package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ScoreTest {

    // 1. Teszt: Helyesen formázza-e a toString() az időt (perc:másodperc)
    @Test
    void testToStringFormat() {
        Score score = new Score("TesztPlayer", 125); // 2 perc 5 másodperc
        String expected = "TesztPlayer: 02:05";
        assertEquals(expected, score.toString(), "A toString nem megfelelő formátumot ad vissza.");
    }

    // 2. Teszt: Egy percen belüli idő formázása
    @Test
    void testToStringFormatBelowMinute() {
        Score score = new Score("TesztPlayer", 45); // 0 perc 45 másodperc
        String expected = "TesztPlayer: 00:45";
        assertEquals(expected, score.toString());
    }

    // 3. Teszt: Getter metódus ellenőrzése (Név)
    @Test
    void testGetName() {
        Score score = new Score("Jatekos", 100);
        assertEquals("Jatekos", score.getName());
    }

    // 4. Teszt: Getter metódus ellenőrzése (Idő)
    @Test
    void testGetTime() {
        Score score = new Score("Jatekos", 100);
        assertEquals(100, score.getTime());
    }

    // 5. Teszt: Sorbarendezés (compareTo) - Kisebb idő a jobb
    @Test
    void testCompareTo() {
        Score score1 = new Score("Gyors", 60);
        Score score2 = new Score("Lassu", 120);

        // Ha score1 gyorsabb (kisebb idő), akkor negatív számot kell visszaadnia
        assertTrue(score1.compareTo(score2) < 0, "A kevesebb időnek előrébb kell végeznie a rangsorban.");
    }
}
