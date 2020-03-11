package by.gmar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author s.kosik
 */
@Entity
@Table(name = "schedules")
public class Schedule {

    private Long id;
    private Long lastUpdateTime;
    private Long periodInMinutes;
    private Boolean active = true;
    private String title;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getPeriodInMinutes() {
        return periodInMinutes;
    }

    public void setPeriodInMinutes(Long periodInMinutes) {
        this.periodInMinutes = periodInMinutes;
    }

//    @Column(columnDefinition = "BIT", length = 9)
    public Boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Column(nullable = false, length = 65)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
