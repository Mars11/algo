package db;

import cn.cqsztech.db.transaction.RequiredNewAndRequiredNewServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/application*.xml" })
public class TransactionTest {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    RequiredNewAndRequiredNewServiceImpl service;
    @Test
    public void testConnect(){
        DataSource dataSource = jdbcTemplate.getDataSource();
        if(dataSource  == null){
            System.out.println("dead error datasource is null");
        }
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from algo");
        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
    }
    @Test
    public void testTransactionRNRN(){
        service.testUpdateInnerError();
        service.testUpdateOuterError();
    }
}
