clean.sh脚本

清空编译结果

compile.sh脚本
 
用于编译源代码

deploy.sh脚本

用于把编译好的结果部署到tomcat下
注意：请设置好$CATALINA_HOME环境变量

初始化数据库
将mySQL/mysql.sql,导入数据库，初始化表

使用方法：
clean.sh && compile.sh && deploy.sh


