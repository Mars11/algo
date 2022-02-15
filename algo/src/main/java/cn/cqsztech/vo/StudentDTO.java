package cn.cqsztech.vo;

import lombok.Data;

import java.util.List;

/**
 * ccmars
 * 2022/2/9
 **/
@Data
public class StudentDTO {
    String name;
    int age;
    List<TeacherDTO> teacherDTOList;
}
