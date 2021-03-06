package webapi;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

/**
 * 公開APIのRESTインタフェース。
 * MicroProfileのOpenAPIのアノテーションを使ってAPIの詳細情報を付加している。<br/>
 * 全体に関するAPI情報は{@link ApplicationConfig}に定義している。
 */
@Path("/products")
public interface ProcutEndPointSpec {

    @GET
    @Path("/pricecalc")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        operationId = "calculatePrice",
        summary = "会員価格を計算する",
        description = "会員の会員種別に応じた割引率を適用した価格を計算する")
    @Parameters({
        @Parameter(name = "memberNo", description = "会員番号。数値＋大文字アルファベットのみ", required = true,
            schema = @Schema(implementation = String.class, minLength = 5, maxLength = 5)),
        @Parameter(name = "price",  description = "価格", required = true,
            schema = @Schema(implementation = Integer.class, minimum = "1", maximum = "99999999"))
    })
    @APIResponse(responseCode = "200",
        description = "計算結果。1個目は割引後の価格、2個目は会員種別、3個目は割引率",
        content = @Content(mediaType = "application/json", schema =
            @Schema(type = SchemaType.ARRAY, implementation = Integer.class, minItems = 3, maxItems = 3)))
    List<Integer> calculatePrice(@QueryParam("memberNo") String memberNo, @QueryParam("price") int price);
}
