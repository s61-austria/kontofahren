package domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Activity() {
    }
}
