package webapi;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.List;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.helidon.microprofile.tests.junit5.AddConfig;
import io.helidon.microprofile.tests.junit5.HelidonTest;

@HelidonTest
@AddConfig(key = "server.port", value = "7001")
public class ProductResourceTest {

    private static ProcutEndPointSpec endPoint;

    @BeforeAll
    static void init() throws Exception {
        endPoint = RestClientBuilder.newBuilder()
                .baseUri(new URI("http://localhost:7001"))
                .build(ProcutEndPointSpec.class);
    }

    @Test
    void testNormalMember() {
        List<Integer> expect = List.of(90, 1, 10);
        List<Integer> actual = endPoint.calculatePrice("A0001", 100);
        assertEquals(expect, actual);
    }

    @Test
    void testSilverMember() {
        List<Integer> expect = List.of(80, 2, 20);
        List<Integer> actual = endPoint.calculatePrice("A0002", 100);
        assertEquals(expect, actual);
    }

    @Test
    void testGoldMember() {
        List<Integer> expect = List.of(70, 3, 30);
        List<Integer> actual = endPoint.calculatePrice("A0003", 100);
        assertEquals(expect, actual);
    }

    @Test
    void testOtherMember() {
        List<Integer> expect = List.of(90, 1, 10);
        //List<Integer> expect = List.of(90, 1, 1); // テストレポートを出すため意図的に失敗させる
        List<Integer> actual = endPoint.calculatePrice("99999", 100);
        assertEquals(expect, actual);
    }
}
