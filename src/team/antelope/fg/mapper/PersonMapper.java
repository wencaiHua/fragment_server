package team.antelope.fg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import team.antelope.fg.pojo.Person;
import team.antelope.fg.pojo.PersonExample;

public interface PersonMapper {
    long countByExample(PersonExample example);

    int deleteByExample(PersonExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Person record);

    int insertSelective(Person record);

    List<Person> selectByExample(PersonExample example);

    Person selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Person record, @Param("example") PersonExample example);

    int updateByExample(@Param("record") Person record, @Param("example") PersonExample example);

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);
}