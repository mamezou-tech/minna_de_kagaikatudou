package webapi;

import java.util.Set;
import java.util.logging.LogManager;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * RESTアプリケーションのコンフィグ情報。
 * REST API全体に関することをOpenAPIのアノテーションで定義している。
 */
@OpenAPIDefinition(
        info = @Info(title = "会員価格を計算してくれる公開APIですよん♪", version = "0.0.1-SNAPSHOT",
            contact = @Contact(name = "豆ちゃん", url = "https://www.mamezou.com"))
        )
@ApplicationScoped
public class ApplicationConfig extends Application {

    @Override
        public Set<Class<?>> getClasses() {
            return Set.of(
                        ProductResource.class
                    );
        }

    // for Eclipse EntryPoint
    public static void main(String[] args) {

        // delegate output of java.util.logging to SLF4J
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        io.helidon.microprofile.cdi.Main.main(args);
    }
}
