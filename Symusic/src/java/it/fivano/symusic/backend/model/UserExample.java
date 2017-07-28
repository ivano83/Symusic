package it.fivano.symusic.backend.model;

import java.util.ArrayList;
import java.util.List;

public class UserExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user
     *
     * @mbggenerated Fri Jan 03 15:48:17 CET 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user
     *
     * @mbggenerated Fri Jan 03 15:48:17 CET 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user
     *
     * @mbggenerated Fri Jan 03 15:48:17 CET 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Fri Jan 03 15:48:17 CET 2014
     */
    public UserExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Fri Jan 03 15:48:17 CET 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Fri Jan 03 15:48:17 CET 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Fri Jan 03 15:48:17 CET 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Fri Jan 03 15:48:17 CET 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Fri Jan 03 15:48:17 CET 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Fri Jan 03 15:48:17 CET 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Fri Jan 03 15:48:17 CET 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Fri Jan 03 15:48:17 CET 2014
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Fri Jan 03 15:48:17 CET 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Fri Jan 03 15:48:17 CET 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table user
     *
     * @mbggenerated Fri Jan 03 15:48:17 CET 2014
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdUserIsNull() {
            addCriterion("id_user is null");
            return (Criteria) this;
        }

        public Criteria andIdUserIsNotNull() {
            addCriterion("id_user is not null");
            return (Criteria) this;
        }

        public Criteria andIdUserEqualTo(Long value) {
            addCriterion("id_user =", value, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserNotEqualTo(Long value) {
            addCriterion("id_user <>", value, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserGreaterThan(Long value) {
            addCriterion("id_user >", value, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserGreaterThanOrEqualTo(Long value) {
            addCriterion("id_user >=", value, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserLessThan(Long value) {
            addCriterion("id_user <", value, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserLessThanOrEqualTo(Long value) {
            addCriterion("id_user <=", value, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserIn(List<Long> values) {
            addCriterion("id_user in", values, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserNotIn(List<Long> values) {
            addCriterion("id_user not in", values, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserBetween(Long value1, Long value2) {
            addCriterion("id_user between", value1, value2, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserNotBetween(Long value1, Long value2) {
            addCriterion("id_user not between", value1, value2, "idUser");
            return (Criteria) this;
        }

        public Criteria andIdUserPreferenceIsNull() {
            addCriterion("id_user_preference is null");
            return (Criteria) this;
        }

        public Criteria andIdUserPreferenceIsNotNull() {
            addCriterion("id_user_preference is not null");
            return (Criteria) this;
        }

        public Criteria andIdUserPreferenceEqualTo(Long value) {
            addCriterion("id_user_preference =", value, "idUserPreference");
            return (Criteria) this;
        }

        public Criteria andIdUserPreferenceNotEqualTo(Long value) {
            addCriterion("id_user_preference <>", value, "idUserPreference");
            return (Criteria) this;
        }

        public Criteria andIdUserPreferenceGreaterThan(Long value) {
            addCriterion("id_user_preference >", value, "idUserPreference");
            return (Criteria) this;
        }

        public Criteria andIdUserPreferenceGreaterThanOrEqualTo(Long value) {
            addCriterion("id_user_preference >=", value, "idUserPreference");
            return (Criteria) this;
        }

        public Criteria andIdUserPreferenceLessThan(Long value) {
            addCriterion("id_user_preference <", value, "idUserPreference");
            return (Criteria) this;
        }

        public Criteria andIdUserPreferenceLessThanOrEqualTo(Long value) {
            addCriterion("id_user_preference <=", value, "idUserPreference");
            return (Criteria) this;
        }

        public Criteria andIdUserPreferenceIn(List<Long> values) {
            addCriterion("id_user_preference in", values, "idUserPreference");
            return (Criteria) this;
        }

        public Criteria andIdUserPreferenceNotIn(List<Long> values) {
            addCriterion("id_user_preference not in", values, "idUserPreference");
            return (Criteria) this;
        }

        public Criteria andIdUserPreferenceBetween(Long value1, Long value2) {
            addCriterion("id_user_preference between", value1, value2, "idUserPreference");
            return (Criteria) this;
        }

        public Criteria andIdUserPreferenceNotBetween(Long value1, Long value2) {
            addCriterion("id_user_preference not between", value1, value2, "idUserPreference");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andMailIsNull() {
            addCriterion("mail is null");
            return (Criteria) this;
        }

        public Criteria andMailIsNotNull() {
            addCriterion("mail is not null");
            return (Criteria) this;
        }

        public Criteria andMailEqualTo(String value) {
            addCriterion("mail =", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailNotEqualTo(String value) {
            addCriterion("mail <>", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailGreaterThan(String value) {
            addCriterion("mail >", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailGreaterThanOrEqualTo(String value) {
            addCriterion("mail >=", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailLessThan(String value) {
            addCriterion("mail <", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailLessThanOrEqualTo(String value) {
            addCriterion("mail <=", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailLike(String value) {
            addCriterion("mail like", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailNotLike(String value) {
            addCriterion("mail not like", value, "mail");
            return (Criteria) this;
        }

        public Criteria andMailIn(List<String> values) {
            addCriterion("mail in", values, "mail");
            return (Criteria) this;
        }

        public Criteria andMailNotIn(List<String> values) {
            addCriterion("mail not in", values, "mail");
            return (Criteria) this;
        }

        public Criteria andMailBetween(String value1, String value2) {
            addCriterion("mail between", value1, value2, "mail");
            return (Criteria) this;
        }

        public Criteria andMailNotBetween(String value1, String value2) {
            addCriterion("mail not between", value1, value2, "mail");
            return (Criteria) this;
        }

        public Criteria andAbilitatoIsNull() {
            addCriterion("abilitato is null");
            return (Criteria) this;
        }

        public Criteria andAbilitatoIsNotNull() {
            addCriterion("abilitato is not null");
            return (Criteria) this;
        }

        public Criteria andAbilitatoEqualTo(Boolean value) {
            addCriterion("abilitato =", value, "abilitato");
            return (Criteria) this;
        }

        public Criteria andAbilitatoNotEqualTo(Boolean value) {
            addCriterion("abilitato <>", value, "abilitato");
            return (Criteria) this;
        }

        public Criteria andAbilitatoGreaterThan(Boolean value) {
            addCriterion("abilitato >", value, "abilitato");
            return (Criteria) this;
        }

        public Criteria andAbilitatoGreaterThanOrEqualTo(Boolean value) {
            addCriterion("abilitato >=", value, "abilitato");
            return (Criteria) this;
        }

        public Criteria andAbilitatoLessThan(Boolean value) {
            addCriterion("abilitato <", value, "abilitato");
            return (Criteria) this;
        }

        public Criteria andAbilitatoLessThanOrEqualTo(Boolean value) {
            addCriterion("abilitato <=", value, "abilitato");
            return (Criteria) this;
        }

        public Criteria andAbilitatoIn(List<Boolean> values) {
            addCriterion("abilitato in", values, "abilitato");
            return (Criteria) this;
        }

        public Criteria andAbilitatoNotIn(List<Boolean> values) {
            addCriterion("abilitato not in", values, "abilitato");
            return (Criteria) this;
        }

        public Criteria andAbilitatoBetween(Boolean value1, Boolean value2) {
            addCriterion("abilitato between", value1, value2, "abilitato");
            return (Criteria) this;
        }

        public Criteria andAbilitatoNotBetween(Boolean value1, Boolean value2) {
            addCriterion("abilitato not between", value1, value2, "abilitato");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table user
     *
     * @mbggenerated do_not_delete_during_merge Fri Jan 03 15:48:17 CET 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table user
     *
     * @mbggenerated Fri Jan 03 15:48:17 CET 2014
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}