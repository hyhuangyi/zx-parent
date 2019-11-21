## zx-parent 
* 版本 springBoot 2.1.9
* 打包 mvn install -Dmaven.test.skip=true -o
* 启动 设置堆内存最小(Xms)/最大(Xmx)/新生代(Xmn) nohup java -server -Xms512m -Xmx512m -Xmn256m -jar zx.jar &