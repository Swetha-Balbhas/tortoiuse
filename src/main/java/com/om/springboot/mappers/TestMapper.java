package com.om.springboot.mappers;

import com.om.springboot.dto.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface TestMapper {
    void insertTest(Test test);
    Test findTestById(Integer id);
    List<Test> findAllTest();
}
