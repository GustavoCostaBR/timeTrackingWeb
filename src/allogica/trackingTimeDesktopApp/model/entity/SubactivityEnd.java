package allogica.trackingTimeDesktopApp.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "subactivity_end")
public class SubactivityEnd extends Subactivity {

    public SubactivityEnd() {
        // Default constructor required by JPA
    }

    public SubactivityEnd(Activity activity, LocalDateTime endTime) {
        super.setActivity(activity);
        super.setTime(endTime);
    }
}






//@Entity
//@Table(name = "subactivity_end")
//public class SubactivityEnd {
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//	public Long getId() {
//		return id;
//	}
//
//    @ManyToOne
//    @JoinColumn(name = "activity_id")
//    private Activity activity;
//
//    @Column(name = "end_time")
//    private LocalDateTime endTime;
//	public LocalDateTime getStartTime() {
//		return endTime;
//	}
//	public void setStartTime(LocalDateTime endTime) {
//		this.endTime = endTime;
//	}  
//	
//	
//	
//	public SubactivityEnd() {
//        // Default constructor required by JPA
//    }
//
//    public SubactivityEnd(Activity activity, LocalDateTime endTime) {
//        this.activity = activity;
//        this.endTime = endTime;
//    }
//	
//}
