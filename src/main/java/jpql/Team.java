package jpql;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.BatchSize;

@Entity
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

//    @BatchSize(size = 10) // Lazy Loading 시, 10개씩 가져옴(최대 1000이하로) -> persistence.xml에 글로벌설정으로함
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
