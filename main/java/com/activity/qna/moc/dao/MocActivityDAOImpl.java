package com.  .activity.qna.moc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.  .activity.qna.moc.entity.MocActivity;
import com.  .activity.qna.moc.exception.MocException;
import com.  .activity.qna.model.DBActivityResult;



public class MocActivityDAOImpl implements iMocActivityDAO {

	private static Log log = LogFactory.getLog(MocActivityDAOImpl.class);
	DataSource activityDataSource;
	JdbcTemplate jdbcTemplate;

	public void setActivityDataSource(DataSource dataSource) {
		this.activityDataSource = dataSource;
	}
	final int MOC_CREDIT_ID = 9;
	final int MOC_ACCT_STATUS = 9;
	final String ACTIVITY_SQL = "select distinct e.ACTIVITY_ID, ai.PARS_ACTIVITY_ID, e.EVAL_REQUIRED, PROVIDER_PRI, ACCREDITOR_PRI, CREDITS " +
			"from activity_eligibility e, article a, activity_info ai " +
			"where a.activity_id = e.activity_id " +
			"and a.activity_id = ai.activity_id " +
			"and a.QUESTIONNAIRE_ID = ? " +
			"and e.CREDIT_TYPE_ID = "+ MOC_CREDIT_ID;

	final String ACTIVITY_RESULT_SQL = "select distinct QUESTIONNAIRE_ID, GLOBAL_USER_ID, PROFILE_COMPLETE, PROFESSION_ID, SPECIALTY_ID, DEGREE_ID, OCCUPATION_ID, COUNTRY_ABBREV " +
			"from ACTIVITY_RESULT r, ARTICLE a " +
			"where r.ACTIVITY_ID = a.ACTIVITY_ID " +
			"and r.GLOBAL_USER_ID  = ? " +
			"AND r.credit_type_id=1 " +
			"and a.QUESTIONNAIRE_ID = ?";

	final String mocInsertQuery = "insert into ACTIVITY_RESULT(GLOBAL_USER_ID,ACTIVITY_ID,PROVIDER_ID,CREDIT_TYPE_ID,CREDITS_EARNED," +
			"PROFILE_COMPLETE,PROFESSION_ID,OCCUPATION_ID,DEGREE_ID,SPECIALTY_ID,COUNTRY_ABBREV,ACCREDITOR_ID,TEST_DATE,USER_AGENT,ACCOUNT_STATUS_ID) " +
			"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	final String mocUpdateQuery = "update ACTIVITY_RESULT set CREDITS_EARNED = ? where activity_id = ? and global_user_id = ? and credit_type_id = 9";

	/**
	 * Looks up an activity to determine if it is an MOC activity
	 * @param activityId
	 * @return
	 */
	@Override
	public boolean checkIfMOCActivity(int activityId) {
		jdbcTemplate = new JdbcTemplate(activityDataSource);
		try{
			Integer resultActivityId = jdbcTemplate.queryForObject("select ACTIVITY_ID from ACTIVITY_ELIGIBILITY where CREDIT_TYPE_ID = "+MOC_CREDIT_ID+" and ACTIVITY_ID = " + activityId, Integer.class);
			if(resultActivityId != null && resultActivityId.intValue()>0){
				return true;
			}
		}catch(DataAccessException dae){
			log.error("Exception looking up activity " + dae.getMessage());
			//Returning false, we don't want to break workflow if there is DB access error
			return false;
		}
		return false;
	}

	/**
	 * Looks up an activity to determine if it is an MOC activity
	 * @param questionnaireId
	 * @return
	 */
	@Override
	public boolean checkIfQuestionnaireIsMOC(int questionnaireId) {
		jdbcTemplate = new JdbcTemplate(activityDataSource);
		try{
			Integer resultActivityId = jdbcTemplate.queryForObject("select a.ACTIVITY_ID from ACTIVITY_ELIGIBILITY ae, ARTICLE a " +
					"where ae.activity_id = a.activity_id " +
					"and CREDIT_TYPE_ID = "+MOC_CREDIT_ID+" and questionnaire_id = " + questionnaireId, Integer.class);
			if(resultActivityId != null && resultActivityId.intValue()>0){
				return true;
			}
		}catch(DataAccessException dae){
			log.error("Exception looking up activity " + dae.getMessage());
			//Returning false, we don't want to break workflow if there is DB access error
			return false;
		}
		return false;
	}

	/**
	 * Looks up abim ID for user
	 * @param guid
	 * @return
	 */
	@Override
	public boolean checkIfUserhasAbim(int guid) {
		jdbcTemplate = new JdbcTemplate(activityDataSource);
		try{
			Integer abimId = jdbcTemplate.queryForObject("select ABIM_ID from REG_PROFESSIONAL where LOCALE = 'en_us' and GLOBAL_USER_ID = " + guid, Integer.class);
			if(abimId != null && abimId.intValue()>0){
				return true;
			}
		}catch(DataAccessException dae){
			log.error("Exception looking up user ABIM ID " + dae.getMessage());
			//Returning false, we don't want to break workflow if there is DB access error
			return false;
		}
		return false;
	}

	/**
	 * Looks up the activity info of the qna test we are saving
	 * @param qnaId
	 * @return MocActivity
	 */
	@Override
	public MocActivity getActivityInfo(int qnaId) throws MocException {

		jdbcTemplate = new JdbcTemplate(activityDataSource);

		MocActivity activityInfo = null;
		try{
			activityInfo = jdbcTemplate.queryForObject(
					ACTIVITY_SQL,
					new Object[]{qnaId},
					new RowMapper<MocActivity>() {
						@Override
						public MocActivity mapRow(ResultSet rs, int rowNum) throws SQLException {
							MocActivity activity = new MocActivity();
							activity.setActivityId(rs.getInt("ACTIVITY_ID"));
							activity.setProviderId(rs.getInt("PROVIDER_PRI"));
							activity.setAccreditorId(rs.getInt("ACCREDITOR_PRI"));
							activity.setPoints(rs.getDouble("CREDITS"));
							activity.setParsActivityId(rs.getInt("PARS_ACTIVITY_ID"));
							activity.setEvalRequired(rs.getInt("EVAL_REQUIRED"));
							return activity;
						}
					});
		}catch(Exception e){
			log.error("Exception looking up activity for questionnaire " + qnaId + ": " + e.getMessage());
			throw new MocException(e.getMessage(), "Exception looking up ID's", e);
		}

		return activityInfo;

	}


	/**
	 * Gets an in progress ACTIVITY_RESULT record
	 * @param
	 * @result DBActivityResult
	 */
	@Override
	public DBActivityResult getInProgressActivityResult(int qnaId, long guid) throws MocException {

		jdbcTemplate = new JdbcTemplate(activityDataSource);

		DBActivityResult dbActivityResult = null;
		try{
			dbActivityResult = jdbcTemplate.queryForObject(
					ACTIVITY_RESULT_SQL,
					new Object[]{guid, qnaId},
					new RowMapper<DBActivityResult>() {
						@Override
						public DBActivityResult mapRow(ResultSet rs, int rowNum) throws SQLException {
							DBActivityResult activityResult = new DBActivityResult();
							activityResult.setQnaId(rs.getInt("QUESTIONNAIRE_ID"));
							activityResult.setGuid(rs.getInt("GLOBAL_USER_ID"));
							activityResult.setProfileComplete(rs.getString("PROFILE_COMPLETE"));
							activityResult.setProfessionId(rs.getInt("PROFESSION_ID"));
							activityResult.setSpecialtyId(rs.getInt("SPECIALTY_ID"));
							activityResult.setDegreeId(rs.getInt("DEGREE_ID"));
							activityResult.setOccupationId(rs.getInt("OCCUPATION_ID"));
							return activityResult;
						}
					});
		}catch(Exception e){
			log.error("Exception looking up activity for questionnaire " + qnaId + ": " + e.getMessage());
			throw new MocException(e.getMessage(), "Exception looking up ID's", e);
		}

		return dbActivityResult;
	}


	/**
	 * Saves record to ACTIVITY_RESULT table
	 * @param
	 */
	@Override
	public int saveMocActivityResult(MocActivity mocActivity, DBActivityResult activityInfo) throws MocException {

		Object[] params = new Object[] {
				activityInfo.getGuid(),
				mocActivity.getActivityId(),
				mocActivity.getProviderId(),
				MOC_CREDIT_ID,
				activityInfo.getPass() >= 1 ? mocActivity.getPoints() : 0,
				activityInfo.getProfileComplete(),
				activityInfo.getProfessionId(),
				activityInfo.getOccupationId(),
				activityInfo.getDegreeId(),
				activityInfo.getSpecialtyId(),
				activityInfo.getCountryAbbrev(),
				mocActivity.getAccreditorId(),
				new java.util.Date(),
				activityInfo.getUserAgent(),
				MOC_ACCT_STATUS
		};

		Object[] updateParams = new Object[3];

		int[] types = new int[] {
				Types.INTEGER,
				Types.INTEGER,
				Types.INTEGER,
				Types.INTEGER,
				Types.DOUBLE,
				Types.VARCHAR,
				Types.INTEGER,
				Types.INTEGER,
				Types.INTEGER,
				Types.INTEGER,
				Types.VARCHAR,
				Types.INTEGER,
				Types.DATE,
				Types.VARCHAR,
				Types.INTEGER
		};

		int[] updateTypes = new int[] {
				Types.DOUBLE,
				Types.INTEGER,
				Types.INTEGER
		};

		//Only insert if there is no existing record (from in-progress workflow)
		boolean doInsert = true;
		try {
			List existingMocRecord = jdbcTemplate.queryForList("Select activity_result_id from activity_result where credit_type_id = "+MOC_CREDIT_ID+" and activity_id = "+mocActivity.getActivityId()+" and global_user_id = " + activityInfo.getGuid());
			if (existingMocRecord != null && existingMocRecord.size() >= 1) {
				doInsert = false;
				List completedDate = jdbcTemplate.queryForList("Select date_completed from activity_result where credit_type_id = 1 and activity_id = "+mocActivity.getActivityId()+" and global_user_id = " + activityInfo.getGuid()+ " and date_completed is not null");
				if (completedDate != null && completedDate.size() >= 1) {
					updateParams[0] = mocActivity.getPoints();
					updateParams[1] = mocActivity.getActivityId();
					updateParams[2] = activityInfo.getGuid();
					jdbcTemplate.update(mocUpdateQuery, updateParams, updateTypes);//if found an existing record update points and date completed.
				}
			}
		} catch(DataAccessException dae) {
			log.debug("No previous record in ActivityResult table for MOC. " + dae.getMessage());
		}

		if (doInsert) {
			return jdbcTemplate.update(mocInsertQuery, params, types);
		} else {
			return 0;
		}

	}

}
