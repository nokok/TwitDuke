#TwitDuke

**JavaJavaJava〜〜〜ｗｗｗ**

#TwitDukeとは?
TwitDukeはJavaで書かれたTwitterクライアントです。  
SwingなのにSwingっぽくないクライアントを目標に開発しています  
2014/4あたりまでにVersion 1.0をリリースする*予定*です  

TwitDukeはMITライセンスで配布されています。  
開発者は一切の義務や責任を負いません。  


**開発開始したばかりなので今のところまともに動きません。  
正式版のリリースまでお待ちください。Pull Request大歓迎です。**

#使い方
初回起動時、**認証ページを自動で開きます。**  
表示された数字をTwitDukeの認証画面に入力するとつぶやく事が出来ます。  

#環境
OS X + IntelliJ 13 CE + Java **8** の環境にて開発をしています。  
現在、Windowsの環境では動作には問題ありませんが一部私が意図しない描画をする場合があります。  
`master`ブランチはJava8ブランチで開発したコードがマージされます。  
`java8`ブランチはJava8で開発をしていますので、Java7以前のJDKではビルドが通りません。  
`java7`ブランチではJava7でもコンパイル出来るコードとなっています。master(java8)ブランチと同等の機能が実装されるとは限りません。

#使用ライブラリ
Twitter4j(Apache License 2.0) http://twitter4j.org/ja/index.html  
guava-libraries(Apache License 2.0) http://code.google.com/p/guava-libraries/  
Project Lombok(MIT license) http://projectlombok.org/  

####開発者:
Twitter:@noko_k
