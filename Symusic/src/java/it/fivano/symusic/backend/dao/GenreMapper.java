package it.fivano.symusic.backend.dao;

import it.fivano.symusic.backend.model.Genre;
import it.fivano.symusic.backend.model.GenreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GenreMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table genre
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    int countByExample(GenreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table genre
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    int deleteByExample(GenreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table genre
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    int deleteByPrimaryKey(Integer idGenre);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table genre
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    int insert(Genre record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table genre
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    int insertSelective(Genre record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table genre
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    List<Genre> selectByExample(GenreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table genre
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    Genre selectByPrimaryKey(Integer idGenre);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table genre
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    int updateByExampleSelective(@Param("record") Genre record, @Param("example") GenreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table genre
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    int updateByExample(@Param("record") Genre record, @Param("example") GenreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table genre
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    int updateByPrimaryKeySelective(Genre record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table genre
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    int updateByPrimaryKey(Genre record);
}