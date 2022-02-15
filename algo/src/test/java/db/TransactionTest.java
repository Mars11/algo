package db;

import cn.cqsztech.db.transaction.RequiredNewAndRequiredNewServiceImpl;
import cn.cqsztech.vo.Student;
import cn.cqsztech.vo.StudentDTO;
import cn.cqsztech.vo.StudentMapping;
import cn.cqsztech.vo.TeacherDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/application*.xml" })
public class TransactionTest {
//    @Resource
//    private JdbcTemplate jdbcTemplate;
//    @Resource
//    RequiredNewAndRequiredNewServiceImpl service;
    @Autowired
    private  StudentMapping studentMapping;
//    @Test
//    public void testConnect(){
//        DataSource dataSource = jdbcTemplate.getDataSource();
//        if(dataSource  == null){
//            System.out.println("dead error datasource is null");
//        }
//        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from algo");
//        for (Map<String, Object> map : maps) {
//            System.out.println(map);
//        }
//    }
//    @Test
//    public void testTransactionRNRN(){
//        service.testApollo();
//
//        service.testUpdateInnerError();
//        service.testUpdateOuterError();
//    }
    @Test
    public void testMapStruct(){
        StudentDTO studentDTO = new StudentDTO();
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setAge(1);
        teacherDTO.setName("li");
        studentDTO.setAge(2);
        studentDTO.setName("ma");
        studentDTO.setTeacherDTOList(Collections.singletonList(teacherDTO));
        Student student = studentMapping.toDto(studentDTO);
        System.out.println(student);
    }
}
