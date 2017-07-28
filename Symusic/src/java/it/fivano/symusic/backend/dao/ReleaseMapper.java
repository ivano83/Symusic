package it.fivano.symusic.backend.dao;

import it.fivano.symusic.backend.model.Release;
import it.fivano.symusic.backend.model.ReleaseExample;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ReleaseMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table symusic..release
	 * @mbggenerated  Mon Sep 09 23:38:11 CEST 2013
	 */
	int countByExample(ReleaseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table symusic..release
	 * @mbggenerated  Mon Sep 09 23:38:11 CEST 2013
	 */
	int deleteByExample(ReleaseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table symusic..release
	 * @mbggenerated  Mon Sep 09 23:38:11 CEST 2013
	 */
	int deleteByPrimaryKey(Long idRelease);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table symusic..release
	 * @mbggenerated  Mon Sep 09 23:38:11 CEST 2013
	 */
	int insert(Release record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table symusic..release
	 * @mbggenerated  Mon Sep 09 23:38:11 CEST 2013
	 */
	int insertSelective(Release record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table symusic..release
	 * @mbggenerated  Mon Sep 09 23:38:11 CEST 2013
	 */
	List<Release> selectByExample(ReleaseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table symusic..release
	 * @mbggenerated  Mon Sep 09 23:38:11 CEST 2013
	 */
	Release selectByPrimaryKey(Long idRelease);
	
	
	List<String> selectDistinctCrew();
	
	Date selectLastReleaseDate(Integer idGenre);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table symusic..release
	 * @mbggenerated  Mon Sep 09 23:38:11 CEST 2013
	 */
	int updateByExampleSelective(@Param("record") Release record,
			@Param("example") ReleaseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table symusic..release
	 * @mbggenerated  Mon Sep 09 23:38:11 CEST 2013
	 */
	int updateByExample(@Param("record") Release record,
			@Param("example") ReleaseExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table symusic..release
	 * @mbggenerated  Mon Sep 09 23:38:11 CEST 2013
	 */
	int updateByPrimaryKeySelective(Release record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table symusic..release
	 * @mbggenerated  Mon Sep 09 23:38:11 CEST 2013
	 */
	int updateByPrimaryKey(Release record);
}