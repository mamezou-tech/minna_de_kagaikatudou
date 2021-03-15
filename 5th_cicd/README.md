# 第5回 最近のモダンなCI/CD環境 ～ Jenkinsおじさんいらず ～
- アプリケーションの実行は<a href="http://54.199.30.101/static/">こちら</a>	
  - 画面のリンクからREST APIを呼び出します（Simple is best!）。ちなみに下のSwaggerUIからはGUIで実行できます
- アプリケーションのAPI仕様(OpneAPI)は<a href="https://mamezou-tech.github.io/minna_de_kagaikatudou/swagger-ui/index.html">こちら</a>
  - MicroProfile OpenAPIで稼働中のアプリから取得したOpenAPIドキュメントをSwaggerUIで表示させてます
- ユニットテスト＆静的解析結果は<a href="https://mamezou-tech.github.io/minna_de_kagaikatudou/site/project-reports.html">こちら</a>	
  - JenkinsのDashboard的なのモノがないのでモダンな感じの[スキン](https://maven.apache.org/skins/)を使い懐かしのmaven site pluginで生成したページをGitHub Pagesにデプロイしてます
  
  ※ EC2にアプリをデプロイしているけどネットワークの設定が緩いのでIPが変わったらご愛敬でｍｍ