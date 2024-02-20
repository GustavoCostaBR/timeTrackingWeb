package allogica.trackingTimeDesktopApp.model.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import allogica.trackingTimeDesktoppApp.exceptions.ThereIsNoEndException;
import allogica.trackingTimeDesktoppApp.exceptions.ThereIsNoStartException;

@Entity
@Table(name = "activity")
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

	@Column(name = "parent_activity_id") // Mapping for the parent activity ID
	private Long parentActivityId; // Field to store the parent activity ID

	public Long getParentActivityId() {
		return parentActivityId;
	}

	public void setParentActivityId(Long parentActivityId) {
		this.parentActivityId = parentActivityId;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    private List<ActivityStart> activityStarts;
	public List<ActivityStart> getStart() {
		return activityStarts;
	}
	public ActivityStart getLastStart() throws ThereIsNoStartException {
		int siize = activityStarts.size();
		if (siize == 0) {
			throw new ThereIsNoStartException("There is no start for activity " + this.getName() + "with ID = " + this.getId() + ".");
		}
		ActivityStart lastItem = activityStarts.get(siize - 1);
		return (lastItem);
	}
	public ActivityStart getFirstStart() throws ThereIsNoStartException {
		int siize = activityStarts.size();
		if (siize == 0) {
			throw new ThereIsNoStartException("There is no start for activity " + this.getName() + "with ID = " + this.getId() + ".");
		}
		ActivityStart firstItem = activityStarts.get(0);
		return (firstItem);
	}
	public int getActivityStartCount () {
		return activityStarts.size();
	}
	public void deleteSubActivityStart(ActivityStart activityStart) {
		activityStarts.remove(activityStart);
	}
	public void addStart(LocalDateTime start) {
		activityStarts.add(new ActivityStart(this, start));
	}

	
	
	@OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    private List<ActivityEnd> activityEnds;
	public List<ActivityEnd> getEnd() {
		return activityEnds;
	}
	public ActivityEnd getLastEnd() throws ThereIsNoEndException {
		int siize = activityEnds.size();
		if (siize == 0) {
			throw new ThereIsNoEndException("There is no end for activity " + this.getName() + "with ID = " + this.getId() + ".");
		}
		ActivityEnd lastItem = activityEnds.get(siize - 1);
		return (lastItem);
	}
	public ActivityEnd getFirsEnd() throws ThereIsNoEndException {
		int siize = activityEnds.size();
		if (siize == 0) {
			throw new ThereIsNoEndException("There is no end for activity " + this.getName() + "with ID = " + this.getId() + ".");
		}
		ActivityEnd firstItem = activityEnds.get(0);
		return (firstItem);
	}
	public int getActivityEndCount () {
		return activityEnds.size();
	}
	public void deleteSubActivityEnd(ActivityEnd activityEnd) {
		activityEnds.remove(activityEnd);
	}
	public void addEnd(LocalDateTime end) {
		activityEnds.add(new ActivityEnd(this, end));
	}

	

	
	@Column(name = "total_time")
	private Duration totalTime;
	public Duration getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(Duration totalTime) {
		this.totalTime = totalTime;
	}

	
	
	@Column(name = "useful_time")
	private Duration usefulTime;
	public Duration getUsefulTime() {
		return usefulTime;
	}
	public void setUsefulTime(Duration usefulTime) {
		this.usefulTime = usefulTime;
	}

	
	
	@OneToMany(cascade = CascadeType.ALL) // Specify the cascade type
	@JoinColumn(name = "parent_activity_id") // Specify the column linking subactivities to their parent
	private Map<Long, Activity> subactivities;
	public Map<Long, Activity> getSubactivities() {
		return subactivities;
	}
	public void addSubactivity(Long id, Activity subactivity) {
		subactivities.put(id, subactivity);
	}
	public void setSubActivities(Map<Long, Activity> activities) {
		for (Map.Entry<Long, Activity> activity : activities.entrySet()) {
			this.addSubactivity(activity.getKey(), activity.getValue());
		}
	}

	public Activity(String name) {
		this.name = name;
		this.subactivities = new HashMap<>();
		this.parentActivityId = 0L;
		this.activityStarts = new ArrayList<>();
        this.activityEnds = new ArrayList<>();
	}

//	public Activity(Long parentActivityId, String name, LocalDateTime start) {
//		this.name = name;
//		this.starts = new ArrayList<>();
//        this.ends = new ArrayList<>();
//	}

}
/*
 * CREATE TABLE Atividade ( id INT PRIMARY KEY, nome VARCHAR(100), descricao
 * TEXT, id_atividade_pai INT, -- Referência para a atividade pai FOREIGN KEY
 * (id_atividade_pai) REFERENCES Atividade(id) );
 */