package org.researchobject.roshow.model;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "uuid_table")
public class UUIDdb implements Serializable {

    private static final long serialVersionUID = -3009157732242241606L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "date")
    private LocalDate date;

    public UUIDdb() {

    }

    public UUIDdb(UUID uuid, LocalDate date) {
        this.uuid = uuid;
        this.date = date;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "UUIDdb{" +
                "uuid=" + uuid +
                ", date=" + date +
                '}';
    }
}
