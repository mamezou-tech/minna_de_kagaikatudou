# 第5回 最近のモダンなCI/CD環境 ～ Jenkinsおじさんいらず ～
- 資料は[こちら](/5th_cicd/doc/modern_cicd_publicversion.pdf)
- アプリケーションの実行は<a href="http://54.199.30.101/static/">こちら</a>	
  - 画面のリンクからREST APIを呼び出します（Simple is best!）。ちなみに下のSwaggerUIからはGUIで実行できます
- アプリケーションのAPI仕様(OpneAPI)は<a href="https://mamezou-tech.github.io/minna_de_kagaikatudou/swagger-ui/index.html">こちら</a>
  - MicroProfile OpenAPIで稼働中のアプリから取得したOpenAPIドキュメントをSwaggerUIで表示させてます
- ユニットテスト＆静的解析結果は<a href="https://mamezou-tech.github.io/minna_de_kagaikatudou/site/project-reports.html">こちら</a>	
  - JenkinsのDashboard的なのモノがないのでモダンな感じの[スキン](https://maven.apache.org/skins/)を使い懐かしのmaven site pluginで生成したページをGitHub Pagesにデプロイしてます
  
  ※ EC2にアプリをデプロイしているけどネットワークの設定が緩いのでIPが変わったらご愛敬でｍｍ

- リンク情報
  - OpenAPIのアノテーションが付けられているコード
    - [ApplicationConfig](/5th_cicd/cicd-sample/src/main/java/webapi/ApplicationConfig.java)
    - [ProductEndPointSpec](/5th_cicd/cicd-sample/src/main/java/webapi/ProcutEndPointSpec.java)
  - workflow(GitHubActions)のyaml
    - [CICD_flow.yml](https://github.com/mamezou-tech/minna_de_kagaikatudou/blob/main/.github/workflows/CICD_flow.yml)
    - [MatrixTests.yml](https://github.com/mamezou-tech/minna_de_kagaikatudou/blob/main/.github/workflows/MatrixTests.yml)
  
- デモアプリのREST APIの仕様(/第4回のサンプルと同じ) 
![インターフェース仕様](/4th_restapi/doc/interface_spec.png)  
 
- デモアプリのCI/CD構成
![CICDの構成](https://user-images.githubusercontent.com/60466339/111430607-f0f0cd80-873d-11eb-8a2d-75089d41fb74.png)
