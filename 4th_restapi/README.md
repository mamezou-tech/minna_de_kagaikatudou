# 第4回 最近のモダンなREST API開発 ～ APIファースト/OpenAPI/etc ～（番外編）
勉強会ではREST APIの仕様をOpenAPIドキュメントで記述し作成したOpenAPIドキュメントからコードを生成するトップダウンなアプローチを主に説明させていただきました  
ココでは当日ご紹介できなかったコードからOpenAPIドキュメントを生成するボトムアップのアプローチをサンプルで説明します

なお、当日の資料はこちら⇒[最近のモダンなREST API開発](/4th_restapi/doc/modern_restapi_development_publicversion.pdf)

## サンプルの概要
トップダウンアプローチの説明で使った以下の仕様を実装したソースコードから[MicroPfofile OpenAPI](https://github.com/eclipse/microprofile-open-api/blob/master/spec/src/main/asciidoc/microprofile-openapi-spec.adoc)の機能を使い動いているアプリケーションからOpenAPIドキュメントを生成するサンプルとなります
![インターフェース仕様](/4th_restapi/doc/interface_spec.png)  
※:説明の都合上パスパラメータは使わないようにしています

## サンプルを動かすのに必要なローカル環境
- JavaSE 11([AdoptOpenJDK11](https://adoptopenjdk.net/)) 以上
- [Maven 3.6.1](https://maven.apache.org/download.cgi#files) 以上
- `java`コマンドも`mvn`コマンドも環境変数のPATHに通っている前提で説明します

## ビルドと動かし方
### プロジェクトファイルの取得
gitがインストールされている場合はこのリポジトリのcloneで、gitがインストールされていない場合はリポジトリの内容を丸々以下のメニューから取得できます
![zipファイルのダウンロード](/4th_restapi/doc/screenshot_zipdownload.png)

### ビルドと実行
リポジトリの内容を取得したらprojectファイルのホームは`4th_restapi/openapi-sample'となるためコンソールまたはDOSプロンプトを開いてそこに移動します  
```shell
$ cd /your_repository_directory/4th_restapi/openapi-sample
```
※ your_repository_directoryはロールでリポジトリを展開したディレクトリ


インタフェース仕様が実装されているサンプルアプリをコンパイルして起動
```shell
$ mvn clean package
$ java -jar target/openApiSample.jar
```
今回のサンプルアプリは[MicroProfile Server](https://helidon.io/docs/latest/#/se/webserver/01_introduction)を使っているため、HTTPサーバのNettyとJAX-RSのJerseyがExcutable Jarの実行で起動します（なので超便利）  
なお、アプリの内容が気になる方は[サンプルアプリの補足](#サンプルアプリの補足)を参照ください  

curl（もしはブラウザ）からREST APIの動作確認を確認します
```shell
 $ curl -i -X GET "http://localhost:7001/products/pricecalc?memberNo=A0001&price=500" -H  "acication/json"
```
※ブラウザから確認する場合はURLをアドレスバーにコピーでresponseが確認できます

:exclamation: ビルドでJandexが必要  
Helidonで使っているYAML生成の実装は[Jandex](https://github.com/wildfly/jandex)の情報を利用します。このため、Helidonを使う場合はビルドプロセスにJandexの生成を含める必要があり、このプロセスが含まれないEclipse上からの起動では`paths`の情報が空になるので注意（サンプルには組み込んであります）

### OpenAPIドキュメントの取得
以下のURLから稼働しているアプリケーションのOpenAPIドキュメントを取得します
```shell
$ curl -X GET http://localhost:7001/openapi
```
[MicroPfofile OpenAPI](https://github.com/eclipse/microprofile-open-api/blob/master/spec/src/main/asciidoc/microprofile-openapi-spec.adoc)は実行時にJAX-RSのインタフェース実装とMicroPfofile OpenAPIの[アノテーション](https://github.com/eclipse/microprofile-open-api/blob/master/spec/src/main/asciidoc/microprofile-openapi-spec.adoc#annotations)を解析しREST APIの仕様をOpenAPIドキュメントで返してくれます。これはブラウザからのアクセスでも同じことができます  
なお、[サンプルアプリの補足](#サンプルアプリの補足)にあるとおり、素のJAX-RS実装からは最小限のOAS情報しか取得できないため、サンプルではMicroProfile OpenAPIのアノテーションで情報を色々付け加えています  

なお、OpenAPIドキュメントをファイルとして取得したい場合は取得するテストコードを入れているので以下のコマンド実行で取得できます
```shell
$ mvn -PgenerateOas clean test
```
テストが成功すれば`openapi-sample/openapi/openapi/doc`に`openapi.yml`ができています

### OpenAPIドキュメントからのコード生成
コード生成は以下の4種類を用意しています。

| profile | 生成コード |
| ------- | ---------- |
| genJavaServer | JAX-RS準拠のRESTリソースコード |
| genJavaClient | HelidonベースのRestClientコード |
| genJsClient | JavaScriptを使ったクライアントコード |
| genC#Client | C#を使ったクライアントコード |

コードを生成する際は以下のコマンドでprofileを指定して実行します
``` shell
$ mvn -P<生成プロファイル> clean openapi-generator:generate

# RESTリソースのコードを生成する場合の例
$ mvn -PgenJavaServer clean openapi-generator:generate
```
コマンド実行でProfile(-P)で指定したコードが`openapi-sample\target\generated-sources\openapi`以下に生成されます  
なお、入力のOpenAPIドキュメントは[OpenAPIドキュメントの取得](#openapiドキュメントの取得)のコマンド実行で生成される`openapi-sample/openapi/openapi/doc/openapi.yml`としているためOpenAPIドキュメントの元ネタとある実装を修正した場合は再生してください  

また、コード生成には[openapi-generator](https://github.com/OpenAPITools/openapi-generator)を使っています。それぞれのProfileで細かいパラメータを設定していますので、チョッと違う感じで生成してみたい場合はGeneratorの[マニュアル](https://openapi-generator.tech/docs/generators)をもとにpom.xmlのprofile内の設定を変えてみてください。

## サンプルアプリの補足
### アノテーションでOAS情報を追加
JAX-RSの素の実装で足りない情報はMicroProfile OpenAPIの[アノテーション](https://github.com/eclipse/microprofile-open-api/blob/master/spec/src/main/asciidoc/microprofile-openapi-spec.adoc#annotations)で補完します  

RESTアプリケーション全体の情報は`javax.ws.rs.core.Application`のサブクラスに、REST APIごとの情報はResouceクラスに設定します  
[Applicationクラス](/4th_restapi/openapi-sample/src/main/java/webapi/ApplicationConfig.java)への設定）
```Java
/**
 * RESTアプリケーションのコンフィグ情報。
 * REST API全体に関することをOpenAPIのアノテーションで定義している。
 */
@OpenAPIDefinition(
        info = @Info(title = "会員価格を計算してくれる公開APIですよん♪", version = "0.0.1-SNAPSHOT",
            contact = @Contact(name = "豆ちゃん", url = "https://www.mamezou.com"))
        )
@ApplicationScoped
public class ApplicationConfig extends Application { ・・・
```
[Resouceクラス](/4th_restapi/openapi-sample/src/main/java/webapi/ProcutEndPointSpec.java)への設定
```Java
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
```

### OpenAPIファイルを取得するテストクラス
HelidonのTest機能を使うとHelidonServerを起動してクラスパス上にある本物のRESTリソースを使ってテストができます。また、下記のようにサンプルの[テストクラス](/4th_restapi/openapi-sample/src/test/java/webapi/GenerateOasFile.java)では[MicroProfile RestClient](https://download.eclipse.org/microprofile/microprofile-rest-client-1.4.0/microprofile-rest-client-1.4.0.html)を使っているため、たったコレだけの実装でJAX-RSのクライアント機能を使った本物のRESTリソース呼び出しができます（すごい便利）
```Java
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
```
