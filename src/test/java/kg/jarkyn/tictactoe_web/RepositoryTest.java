package kg.jarkyn.tictactoe_web;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RepositoryTest {

    private Repository repo;
    private WebUI webUI;

    @Before
    public void setUp() {
        repo = new Repository();
        webUI = new WebUI();
    }

    @Test
    public void isEmptyAtTheStart() {
        assertTrue(repo.isEmpty());
    }

    @Test
    public void notEmptyAfterSaving() {
        repo.save(webUI);

        assertFalse(repo.isEmpty());
    }

    @Test
    public void returnsId() {
        assertEquals(repo.save(webUI), webUI.hashCode());
    }

    @Test
    public void knowsIfGivenValueIsNotStored() {
        assertFalse(repo.contains(webUI));
    }

    @Test
    public void savesUI() {
        repo.save(webUI);

        assertTrue(repo.contains(webUI));
    }

    @Test
    public void findsById() {
        int id = repo.save(webUI);

        assertEquals(webUI, repo.find(id));
    }

    @Test
    public void returnsLast() {
        WebUI first = new WebUI();
        WebUI last = new WebUI();

        repo.save(first);
        repo.save(last);

        assertEquals(last, repo.getLast());
    }

    @Test
    public void returnsLastId() {
        repo.save(webUI);

        assertEquals(webUI.hashCode(), repo.getLastId());
    }
}
