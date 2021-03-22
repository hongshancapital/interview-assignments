package xiejin.java.interview;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;


public class GeneratorServiceEntity {

    /*public static void main(String[] args) throws SQLException {
        GeneratorServiceEntity serviceEntity = new GeneratorServiceEntity();
        String packageName = "com.speed.walk.user";
        serviceEntity.generateByTables(packageName,
                "table_name");
    }*/

    @Test
    public void generateCode() throws SQLException {
//        String packageName = "com.speed.walk.user";
        String packageName = "xiejin.java.interview";
//        generateByTables(packageName,
//                "table_name1",
//                "table_name2",
                //"table_name3");
//        generateByTables(packageName,
//                "table_name");
//        generateByTables(packageName,"message_code","user_auth","user_config","user_info","user_operate_log","user_identity","user_level");
        generateByTables(packageName,
                "domain");
    }

    private void generateByTables(String packageName, String... tableNames) throws SQLException {
        GlobalConfig config = new GlobalConfig();
//        String dbUrl = "jdbc:oracle:thin:@//localhost:1521/knowledge";
        String dbUrl = "jdbc:mysql:///domain-change?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8&userSSL=false";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        /*dataSourceConfig.setDbType(DbType.ORACLE)
                .setUrl(dbUrl)
                .setUsername("knowledge")
                .setPassword("knowledge")
                .setDriverName("oracle.jdbc.OracleDriver");*/
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("admin")
                .setDriverName("com.mysql.cj.jdbc.Driver");
        //3、策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true)//开启全局大写命名
                      .setNaming(NamingStrategy.underline_to_camel)//下划线到驼峰的命名方式
//                      .setTablePrefix("t_")//表名前缀
                      .setEntityLombokModel(true)//使用lombok
                      .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        config.setActiveRecord(false)
                .setAuthor("xiejin")
                .setOutputDir("D:\\WorkSpace\\GeneratorCode")
                .setEnableCache(false)
                .setBaseResultMap(true)//生成resultMap
                .setBaseColumnList(true)//在xml中生成基础列
                .setFileOverride(true)
                .setServiceName("%sService");//生成的service接口名字首字母是否为I，这样设置就没有I
      	//4、包名策略配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(packageName)//设置包名的parent
                     .setMapper("mapper")
                     .setService("service")
                     .setController("controller")
                     .setEntity("entity")
                     .setXml("mapper");//设置xml文件的目录
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .execute();
    }
}