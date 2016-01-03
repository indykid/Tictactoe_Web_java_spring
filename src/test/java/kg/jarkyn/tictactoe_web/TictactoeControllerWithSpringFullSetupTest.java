package kg.jarkyn.tictactoe_web;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class TictactoeControllerWithSpringFullSetupTest {

    @Value("${local.server.port}")
    private int port;

    private String baseUrl;
    private RestTemplate template;
    private ResponseEntity<String> response;
    private final String hvhOption = "3";
    private final String cvcOption = "4";

    @Before
    public void setUp() throws Exception {
        baseUrl = "http://localhost:" + port;
        template = new TestRestTemplate();
    }

    @Test
    public void getsGameSelection() {
        response = template.getForEntity(baseUrl + "/game/select", String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void getNewGame() throws Exception {
        response = template.getForEntity(baseUrl + "/game?gameOption=1", String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void getsGame() throws MalformedURLException {
        ResponseEntity newGameResponse = template.getForEntity(baseUrl + "/game?gameOption=" + hvhOption, String.class);
        String url = baseUrl + findUrl(newGameResponse);

        response = template.getForEntity(url, String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void getsGameOver() throws MalformedURLException {
        ResponseEntity newGameResponse = template.getForEntity(baseUrl + "/game?gameOption=" + hvhOption, String.class);

        response = playAllMoves(newGameResponse);

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    private ResponseEntity<String> playAllMoves(ResponseEntity lastResponse) {
        String[] positions = new String[]{"0", "4", "3", "6", "2", "1", "7", "5", "8"};
        String url = baseUrl + findUrl(lastResponse) + "?position=";

        for (String position : positions) {
            response = template.getForEntity(url + position, String.class);
        }
        return response;
    }

    private String findUrl(ResponseEntity response) {
        Elements elements = Jsoup.parse(response.toString()).select("a.position");
        String[] urlParts = elements.attr("href").split("\\?");
        return urlParts[0];
    }
}
