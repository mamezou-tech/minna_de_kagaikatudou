package webapi;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/mng")
@ApplicationScoped
public class ManagementResource {

    @GET
    @Path("stop")
    @Produces(MediaType.APPLICATION_JSON)
    public String stopApplication() {
        new Thread( () -> {
            System.out.println("★終了イベント受信：3秒後に終了します");
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("実行：System.exit(0)");
            System.exit(0);
        } ).start();
        return "success";
    }
}
