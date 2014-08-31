#Notifications

通知を表示します。通知方法は選択された実装によって異なります。  
同時に複数の方法で同じ通知がなされる場合もあります。
標準の通知方法は、ディスプレイの隅に小さなダイアログを表示させます。

e.g.
````
var n = notification.showInfo('Infomation','description');
n.onClosing(function func);
n.onClosed(function func);
````


