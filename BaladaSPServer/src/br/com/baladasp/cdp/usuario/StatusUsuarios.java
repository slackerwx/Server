package br.com.baladasp.cdp.usuario;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@NamedQueries({ @NamedQuery(name = "StatusUsuarios.getAll", query = "from StatusUsuarios order by createdAt DESC") })
@Entity
@Table(name = "tb_status_usuarios")
public class StatusUsuarios implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	private long userID;
	private String screenName;
	private String createdAt;
	private String profileImageURL;
	private String text;

	public StatusUsuarios() {

	}

	public StatusUsuarios(long userID, String screenName, String date, String profileImageURL, String text) {
		super();
		this.userID = userID;
		this.screenName = screenName;
		this.createdAt = date;
		this.profileImageURL = profileImageURL;
		this.text = text;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setProfileImageURL(String profileImageURL) {
		this.profileImageURL = profileImageURL;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getScreenName() {
		return this.screenName;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public String getProfileImageURL() {

		return this.profileImageURL;
	}

	public String getText() {

		return this.text;
	}

	@Override
	public String toString() {
		return "StatusUsuarios [userID=" + userID + ", screenName=" + screenName + ", createdAt=" + createdAt
				+ ", profileImageURL=" + profileImageURL + ", text=" + text + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((screenName == null) ? 0 : screenName.hashCode());
		result = prime * result + (int) (userID ^ (userID >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatusUsuarios other = (StatusUsuarios) obj;
		if (screenName == null) {
			if (other.screenName != null)
				return false;
		} else if (!screenName.equals(other.screenName))
			return false;
		if (userID != other.userID)
			return false;
		return true;
	}

}
