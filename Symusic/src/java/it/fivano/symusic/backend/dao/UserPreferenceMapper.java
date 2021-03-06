package it.fivano.symusic.backend.dao;

import it.fivano.symusic.backend.model.UserPreference;
import it.fivano.symusic.backend.model.UserPreferenceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPreferenceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_preference
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    int countByExample(UserPreferenceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_preference
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    int deleteByExample(UserPreferenceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_preference
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    int deleteByPrimaryKey(Long idUserPreference);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_preference
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    int insert(UserPreference record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_preference
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    int insertSelective(UserPreference record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_preference
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    List<UserPreference> selectByExample(UserPreferenceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_preference
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    UserPreference selectByPrimaryKey(Long idUserPreference);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_preference
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    int updateByExampleSelective(@Param("record") UserPreference record, @Param("example") UserPreferenceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_preference
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    int updateByExample(@Param("record") UserPreference record, @Param("example") UserPreferenceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_preference
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    int updateByPrimaryKeySelective(UserPreference record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_preference
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    int updateByPrimaryKey(UserPreference record);
}