package it.fivano.symusic.backend.model;

import java.io.Serializable;

public class ReleaseRatingKey implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column release_rating.id_user
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    private Long idUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column release_rating.id_release
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    private Long idRelease;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table release_rating
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column release_rating.id_user
     *
     * @return the value of release_rating.id_user
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    public Long getIdUser() {
        return idUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column release_rating.id_user
     *
     * @param idUser the value for release_rating.id_user
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column release_rating.id_release
     *
     * @return the value of release_rating.id_release
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    public Long getIdRelease() {
        return idRelease;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column release_rating.id_release
     *
     * @param idRelease the value for release_rating.id_release
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    public void setIdRelease(Long idRelease) {
        this.idRelease = idRelease;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table release_rating
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
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
        ReleaseRatingKey other = (ReleaseRatingKey) that;
        return (this.getIdUser() == null ? other.getIdUser() == null : this.getIdUser().equals(other.getIdUser()))
            && (this.getIdRelease() == null ? other.getIdRelease() == null : this.getIdRelease().equals(other.getIdRelease()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table release_rating
     *
     * @mbggenerated Sun Sep 01 01:31:17 CEST 2013
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIdUser() == null) ? 0 : getIdUser().hashCode());
        result = prime * result + ((getIdRelease() == null) ? 0 : getIdRelease().hashCode());
        return result;
    }
}