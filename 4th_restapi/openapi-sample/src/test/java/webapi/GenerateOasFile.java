package webapi;

import static java.nio.file.StandardOpenOption.*;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.helidon.microprofile.tests.junit5.AddConfig;
import io.helidon.microprofile.tests.junit5.HelidonTest;

@HelidonTest
@AddConfig(key = "server.port", value = "7001")
public class GenerateOasFile {

    private static final Logger LOG = LoggerFactory.getLogger(GenerateOasFile.class);

    public interface OpenApiEndPoint {
        @GET
        @Path("/openapi")
        @Produces(MediaType.TEXT_PLAIN)
        String getOasFile();
    }

    @Test
    void generateOasFile() throws Exception {
        OpenApiEndPoint endPoint = RestClientBuilder.newBuilder()
                .baseUri(new URI("http://localhost:7001"))
                .build(OpenApiEndPoint.class);

        java.nio.file.Path filePath = Files.createDirectories(Paths.get("openapi")).resolve("openapi.yml");
        String body = endPoint.getOasFile();
        Files.writeString(filePath, body, CREATE, WRITE, TRUNCATE_EXISTING); // 上書きモード
        LOG.info("OASファイルを生成しました [Path:{}]", filePath.toAbsolutePath());
    }
}
