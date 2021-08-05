package cn.cqsztech.db.transaction;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * ccmars
 * 2021/5/26
 **/
@Service
public class RequiredNewAndRequiredNewServiceImpl {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRES_NEW)
    public void testUpdateInnerError(){
        jdbcTemplate.update("update algo set id = 1");
        updateInnerError();
    }
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRES_NEW)
    public void testUpdateOuterError(){
        updateInner();
        jdbcTemplate.update("update algo set id = 1");
        throw new RuntimeException("outer error");
    }
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRES_NEW)
    public void updateInnerError(){
        jdbcTemplate.update("update algo set id = 3");
        throw new  RuntimeException("update error");
    }
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRES_NEW)
    public void updateInner(){
        jdbcTemplate.update("update algo set id = 3");
    }
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRES_NEW)
    public void select (){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from algo");
        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
    }
}
