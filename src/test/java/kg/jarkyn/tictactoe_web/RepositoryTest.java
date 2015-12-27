package kg.jarkyn.tictactoe_web;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RepositoryTest {

    private Repository repo;
    private WebUI webUI;

    @Before
    public void setUp() {
        repo = new Repository();
        webUI = new WebUI();
    }

    @Test
    public void returnsId() {
        assertEquals(repo.save(webUI), webUI.hashCode());
    }


    @Test
    public void savesUI() {
        repo.save(webUI);

        assertTrue(repo.isPresent(webUI));
    }

    @Test
    public void findsById() {
        int id = repo.save(webUI);

        assertEquals(webUI, repo.find(id));
    }
}
