package kg.jarkyn.tictactoe_web;

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
    ResponseEntity<String> response;

    @Before
    public void setUp() throws Exception {
        baseUrl = "http://localhost:" + port;
        template = new TestRestTemplate();
    }

    @Test
    public void getNewGame() throws Exception {
        response = template.getForEntity(baseUrl + "/game?gameOption=1", String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void getsGame() throws MalformedURLException {
        playMoves(new String[]{"0"});

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void getsGameOver() throws MalformedURLException {
        playMoves(new String[]{"0", "4", "3", "6", "2", "1", "7", "5", "8"});

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    private void playMoves(String[] positions) throws MalformedURLException {
        String urlPartial = baseUrl + "/game?position=";
        for (String position : positions) {
            String url = urlPartial + position;
            response = template.getForEntity(url, String.class);
        }
    }
}
