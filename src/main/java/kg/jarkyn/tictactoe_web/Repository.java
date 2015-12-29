package kg.jarkyn.tictactoe_web;

import java.util.LinkedHashMap;

public class Repository {
    private LinkedHashMap<Integer, WebUI> store;
    private WebUI last;

    public Repository() {
        this.store = new LinkedHashMap<>();
    }

    public int save(WebUI webUI) {
        store.put(webUI.hashCode(), webUI);
        last = webUI;
        return webUI.hashCode();
    }

    public boolean contains(WebUI webUI) {
        return store.containsValue(webUI);
    }

    public WebUI find(int id) {
        return store.get(id);
    }

    public WebUI getLast() {
        return last;
    }

    public boolean isEmpty() {
        return store.isEmpty();
    }

    public int getLastId() {
        return last.hashCode();
    }
}
