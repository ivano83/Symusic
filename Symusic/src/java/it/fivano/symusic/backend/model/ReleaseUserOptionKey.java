package it.fivano.symusic.backend.model;

import java.io.Serializable;

public class ReleaseUserOptionKey implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column symusic..release_user_option.id_release
     *
     * @mbggenerated Fri Jan 03 15:41:40 CET 2014
     */
    private Long idRelease;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column symusic..release_user_option.id_user
     *
     * @mbggenerated Fri Jan 03 15:41:40 CET 2014
     */
    private Long idUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table symusic..release_user_option
     *
     * @mbggenerated Fri Jan 03 15:41:40 CET 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column symusic..release_user_option.id_release
     *
     * @return the value of symusic..release_user_option.id_release
     *
     * @mbggenerated Fri Jan 03 15:41:40 CET 2014
     */
    public Long getIdRelease() {
        return idRelease;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column symusic..release_user_option.id_release
     *
     * @param idRelease the value for symusic..release_user_option.id_release
     *
     * @mbggenerated Fri Jan 03 15:41:40 CET 2014
     */
    public void setIdRelease(Long idRelease) {
        this.idRelease = idRelease;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column symusic..release_user_option.id_user
     *
     * @return the value of symusic..release_user_option.id_user
     *
     * @mbggenerated Fri Jan 03 15:41:40 CET 2014
     */
    public Long getIdUser() {
        return idUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column symusic..release_user_option.id_user
     *
     * @param idUser the value for symusic..release_user_option.id_user
     *
     * @mbggenerated Fri Jan 03 15:41:40 CET 2014
     */
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table symusic..release_user_option
     *
     * @mbggenerated Fri Jan 03 15:41:40 CET 2014
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ReleaseUserOptionKey other = (ReleaseUserOptionKey) that;
        return (this.getIdRelease() == null ? other.getIdRelease() == null : this.getIdRelease().equals(other.getIdRelease()))
            && (this.getIdUser() == null ? other.getIdUser() == null : this.getIdUser().equals(other.getIdUser()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table symusic..release_user_option
     *
     * @mbggenerated Fri Jan 03 15:41:40 CET 2014
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIdRelease() == null) ? 0 : getIdRelease().hashCode());
        result = prime * result + ((getIdUser() == null) ? 0 : getIdUser().hashCode());
        return result;
    }
}