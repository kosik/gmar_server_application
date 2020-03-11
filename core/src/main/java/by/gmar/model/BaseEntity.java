package by.gmar.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import by.gmar.utilities.CustomJsonDateDeserializer;
import by.gmar.utilities.CustomJsonDateDeserializer;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Base JPA entity class from which all domain entity objects should inherit. Exception to this rule
 * are auxilary entities that exist for the purpose of establishing one-to-many or many-to-many
 * relations - these are not considered domain objects.
 *
 * @author s.kosik
 */
@MappedSuperclass
public class BaseEntity implements Serializable {
    
    @Basic(optional = false)
    @Column(name = "create_time_stamp", nullable = false, length = 11, updatable = false)
    @JsonProperty("timeStamp")
    private Long timeStamp;
    
    @Basic(optional = false)
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("createTime")
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date createTime;

    @Basic
//  @JsonIgnore due to we need in on contentFlow
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @PrePersist
    void prePersist() {
        if(null == createTime){
            //the fork only for drefered geolocations posibility
            createTime = new Date();
        }
        if(null == timeStamp)
            timeStamp = new Date().getTime();
    }

    @PreUpdate
    void preUpdate() {
        updateTime = new Date();
    }

    public Long getTimeStamp() {
        if(null == timeStamp){
            timeStamp = new Date().getTime();
        }
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

}
