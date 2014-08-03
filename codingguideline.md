#PullRequestコーディングガイドライン
`Version 20140803`   
PullRequestを送る際に参考にして欲しいガイドラインです。  
多くのJavaで書かれたプロジェクトで適用されている  
一般的な規約だと考えているので特に難しいことは書いていないつもりです。  
**重要:  
このガイドラインに従わなかった部分があるという理由のみでPullRequestを  
リジェクトすることはないと思います。**  
このガイドラインを修正するPullRequestを送って頂いても全然OKです。  

##インデント
4スペースでインデントしてください。  

````
//NG
if (cond) {
  return "Something";
} else { 
  return "Other";
}

//OK 
if (cond) {
    return "Something";
} else {
    return "Other";
}
````

##{}
* ブレース`{}`は省略禁止です。  
単純なif文でも省略しないでください。  

````
//NG
if (cond)
    return "Something";
else
    return "Other";

//OK
if (cond) {
    return "Something";
} else {
    return "Other";
}
````

##命名
* クラスは`PascalCase`、変数名及びメソッド名は`camelCase`としてください。  
アンダースコア`_`は使いません。  

````
//NG
public class camelCaseClassName {

}

//NG
public class snake_case_class_name {

}

//OK
public class PascalCaseClassName {
    public void camelCaseMethodName(){...}
}
````

* 定数はすべて大文字で`SNAKE_CASE`で記述してください。

````
public static final String NAME = "noko";
public static final String USER_ID = "@nokok";
````

* `tmp`や`i`などの変数名は制限はしませんが出来るだけ小さなスコープでのみ使用してください。  
* インターフェース名の先頭に`I`は付けないでください（これはC#のスタイルです）  

````
//NG
public interface IHoge {
    ..
}

//OK
public interface Hoge {
    ..
}
````

* `Hoge`インターフェースの実装クラスは`HogeImpl`クラスと命名してください。  
単一のインターフェースに対して複数の実装がある(もしくは今は無くとも複数の実装が考えられる)場合は、  
どのような実装なのか分かるようなクラス名にしてください。  

````
public interface Writer<T> {
    public void write(T obj);
}

//OK
public class WriterImpl implements Writer<Serializable> {

    @Override
    public void write(Serializable obj) {

    }
}

//OK
public class AccessTokenWriter implements Writer<AccessToken> {

    @Override
    public void write(AccessToken obj) {

    }
}
````

##import
* import文は省略しないでください。  
スターインポートは禁止です。全てのimportを記述してください。  

##返り値
* 公開APIでの`return null;`の禁止  
`public`および`package private`なメソッドの返り値で`null`が返る可能性がある場合、  
`Optional<T>`でラップしてください。  
`private`メソッドでは`null`を返しても構いませんが、おすすめ*しません。*  

````
//NG
public Config readConfig() {
    try {
        ..
    } catch (IOException e) {
        return null;
    }
}

//OK
public Optional<Config> readConfig() {
    try {
        ..
        return Optional.of(hoge);
    } catch (IOException e) {
        return Optional.empty();
    }
}

//OK (but NOT recommended)
private Config readConfig() {
    try {
        ..
        return config;
    } catch (IOException e) {
        return null;
    }
}
````

##その他

* `finally`でなく、`try-with-resources`を使う  
`finally`の使用は避けてください。Java 7から導入された`try-with-resources`を使います。

````
//NG
try {
    Reader hogeReader = ..
    resources = hogeReader.read();
} catch (IOException e) {
    ..
} finally {
    hogeReader.close();
}

//OK
try(Reader hogeReader = ..) {
    resources = hogeReader.read();
} catch (IOException e) {
    ..
}
````

詳しくはOracleのドキュメントを参考にしてください。  
[http://docs.oracle.com/javase/jp/7/technotes/guides/language/try-with-resources.html](http://docs.oracle.com/javase/jp/7/technotes/guides/language/try-with-resources.html)

* `e.printStackTrace();`のみの`catch`を避ける  
標準/エラー出力はデフォルトで無効化してあります。  
(なのでこのように書いてしまうと実質的に例外無視です)  
また、例外的状況にもかかわらず処理が続行されてしまう可能性があります。  
適切な例外をスローしてください。  

* イミュータブルな設計にする  
setterメソッドなど外部からオブジェクトの状態を直接書き換えるやり方はあまり好ましくありません。  
フィールド変数にはできるだけ`final`をつけてください。  
できるだけイミュータブルな設計となるよう心がけてください。  
すべてのフィールド変数が`final`な場合でも、  
`Map`などのコレクションを複数のメソッドで共有して値をやりとりするような書き方は  
思いがけない副作用をもたらす可能性があり、実質的にミュータブルなオブジェクトです。  

* `@Override`  
Overrideアノテーションは省略しないでください。  

* 型でオブジェクトを区別し、変数名で区別することを避ける  
区別されるべきデータを同じクラスで表現し、変数名で区別するような書き方をしてしまうと、  
コンパイル時にエラーが見つからないばかりか無駄なエラーチェックを必要とします。  
`String`を用いる場合は表現しようとしているオブジェクトが本質的に文字列の場合のみ使用するようにしてください。  
簡単な例として次のようなコードの場合を考えます。

````
String input = getInput();
String str = escape(input);  //NG
````

入力された生データ`input`は`String`型ですが、  
エスケープされた文字列`str`は`input`と区別されるべきです。なので
`EscapedString`クラスなどを作って区別するべきです。  
