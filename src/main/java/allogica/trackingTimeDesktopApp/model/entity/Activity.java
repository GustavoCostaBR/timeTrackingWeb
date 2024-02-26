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

	
	@Column(name = "current")
    private boolean current;
	public boolean isCurrent() {
		return current;
	}
	public void setCurrent(boolean current) {
		this.current = current;
	}

	
	@Column(name = "description")
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    private List<ActivityTime> activityStarts;
	public List<ActivityTime> getStart() {
		return activityStarts;
	}
	public List<LocalDateTime> getStartTime(){
		List <LocalDateTime> startTime = new ArrayList<LocalDateTime>();
		for (ActivityTime start :  getStart()) {
			startTime.add(start.getTime());
		}
		return startTime;
	}
	public ActivityTime getLastStart() throws ThereIsNoStartException {
		int siize = activityStarts.size();
		if (siize == 0) {
			throw new ThereIsNoStartException("There is no start for activity " + this.getName() + "with ID = " + this.getId() + ".");
		}
		ActivityTime lastItem = activityStarts.get(siize - 1);
		return (lastItem);
	}
	public ActivityTime getFirstStart() throws ThereIsNoStartException {
		int siize = activityStarts.size();
		if (siize == 0) {
			throw new ThereIsNoStartException("There is no start for activity " + this.getName() + "with ID = " + this.getId() + ".");
		}
		ActivityTime firstItem = activityStarts.get(0);
		return (firstItem);
	}
	public int getActivityStartCount () {
		return activityStarts.size();
	}
	public void deleteActivityStart(ActivityStart activityStart) {
		activityStarts.remove(activityStart);
	}
	public void addStart(LocalDateTime start) {
		activityStarts.add(new ActivityStart(this, start));
	}
	public void addStart(ActivityTime start) {
		activityStarts.add(start);
	}

	
	
	@OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    private List<ActivityEnd> activityEnds;
	public List<ActivityEnd> getEnd() {
		return activityEnds;
	}
	public List<LocalDateTime> getEndTime(){
		List <LocalDateTime> endTime = new ArrayList<LocalDateTime>();
		for (ActivityEnd end :  getEnd()) {
			endTime.add(end.getTime());
		}
		return endTime;
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
	public void deleteActivityEnd(ActivityEnd activityEnd) {
		activityEnds.remove(activityEnd);
	}
	public void addEnd(LocalDateTime end) {
		activityEnds.add(new ActivityEnd(this, end));
	}
	public void addEnd(ActivityEnd end) {
		activityEnds.add(end);
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
	public Duration sumUsefulTime(List<LocalDateTime> starts, List<LocalDateTime> ends) {
		Duration usefulTime = Duration.ZERO;
		Long counter1 = 0L;
		Long counter2;
		for (LocalDateTime end : ends) {
			counter1++;
			counter2 = 0L;
			for (LocalDateTime start : starts) {
				counter2++;
				if (counter1 == counter2) {
					usefulTime = usefulTime.plus(Duration.between(end, start));
				}
			}
		}
		this.usefulTime = usefulTime;
		return usefulTime;
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