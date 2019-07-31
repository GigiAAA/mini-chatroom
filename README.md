创建一个文件的测试类(单元测试)
ctrl+shift+t

因使用数据库可能不同，故可设置配置信息，如：驱动类型/URL/username/password
用户在连接其他数据库时可直接修改配置信息即可，不用修改源代码，大大提高了代码的实用性

如何加载配置文件？
1.创建配置文件包;
2.书写工具类(静态方法)
    1)Properties properties=new Properties();
    2)获得配置文件的输入流
    3)加载配置文件中的所有内容

JDBCUtils(数据库公共方法类)
    -加载驱动
    -获取连接 Connection -new Thread()
    -释放资源