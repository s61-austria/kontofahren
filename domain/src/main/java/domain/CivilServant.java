package domain;

import javax.persistence.*;

@Entity
@DiscriminatorValue("CivilServant")
public class CivilServant extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
