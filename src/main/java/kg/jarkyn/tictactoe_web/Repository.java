package kg.jarkyn.tictactoe_web;

import java.util.HashMap;

public class Repository {
    private HashMap<Integer, WebUI> store;

    public Repository() {
        this.store = new HashMap<>();
    }

    public int save(WebUI webUI) {
        store.put(webUI.hashCode(), webUI);
        return webUI.hashCode();
    }

    public boolean isPresent(WebUI game) {
        return store.get(game.hashCode()) != null;
    }

    public WebUI find(int id) {
        return store.get(id);
    }
}
