package perform;

import java.util.Map;

import org.springframework.web.reactive.function.client.WebClient;

public class PerformRestExample {

    private final String SERVER_URI = "http://localhost:8080/rest";

    private WebClient webClient = WebClient.create();

    public PerformRestExample() {
        TestRestExample();
    }

    private void TestRestExample() {

        System.out.println("\n--------GET WEDSTRIJD SUCCES--------");
        getEvent(1L);
        System.out.println("--------GET WEDSTRIJD FAIL--------");
        try {
            getEvent(59L);
        } catch(Exception e) {
            System.out.println(e.getMessage()); 
        }

    }

    private void getEvent(Long id) {
        webClient.get().uri(SERVER_URI + "/events/" + id).retrieve()
            .bodyToMono(Map.class)
            .doOnSuccess(getal -> System.out.println(getal))
            .block();
    }
}