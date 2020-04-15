package top.yxf.mybatisplus.config;


import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
@MapperScan("top.yxf.mybatisplus.mapper")
@EnableTransactionManagement
@Slf4j
public class MyBatisPlusConfig {

    /**
     * 注册乐观锁插件
     *
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }



    /**
     * 逻辑删除组件
     *
     * @return
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * SQL执行效率插件
     * 设置 dev test 环境开启，保证我们的效率
     */
    @Bean
    @Profile({"dev", "test"})
    public PerformanceInterceptor performanceInterceptor() {

        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        // ms设置sql执行的最大时间，如果超过了则不 执行
        performanceInterceptor.setMaxTime(100);
        //格式化sql语句
//        Properties properties = new Properties();
//        properties.setProperty("format", "true");
//        properties.setProperty("maxTime","1000");
//        performanceInterceptor.setProperties(properties);
        performanceInterceptor.setMaxTime(1000L);
        performanceInterceptor.setFormat(true);





        return performanceInterceptor;
    }

    /**
     * 分页过滤器
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        List<ISqlParser> sqlParserList = new ArrayList<>();
        // sql解析器
        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        tenantSqlParser.setTenantHandler(new TenantHandler() {
            @Override
            public Expression getTenantId() {


                log.error("Expression ------->");

                System.out.println(" ");
                return new LongValue(2L);
            }

            @Override
            public String getTenantIdColumn() {
                log.error("getTenantIdColumn ------->");
                return "id";
            }

            @Override
            public boolean doTableFilter(String tableName) {

                log.error(tableName + " ------->");
                return false;
            }
        });
        sqlParserList.add(tenantSqlParser);
        paginationInterceptor.setSqlParserList(sqlParserList);

        paginationInterceptor.setSqlParserFilter(new ISqlParserFilter() {
            @Override
            public boolean doFilter(MetaObject metaObject) {
                MappedStatement ms = SqlParserHelper.getMappedStatement(metaObject);
                // 过滤自定义查询此时无租户信息约束【 麻花藤 】出现
                if ("top.yxf.mybatisplus.mapper.UserMapper.selectList".equals(ms.getId())) {
                    return true;
                }
               log.error(ms.getId() + "------> paginationInterceptor");
                return false;
            }
        });
        return paginationInterceptor;
    }

    private Expression multipleTenantIdCondition(String tenantIdColumn) {
        final InExpression inExpression = new InExpression();
        inExpression.setLeftExpression(new Column(tenantIdColumn));
        final ExpressionList itemsList = new ExpressionList();
        final List<Expression> inValues = new ArrayList<>(2);
        //ID自己想办法获取到
        inValues.add(new LongValue(1));
        inValues.add(new LongValue(2));
        itemsList.setExpressions(inValues);
        inExpression.setRightItemsList(itemsList);
        return inExpression;
    }
}
