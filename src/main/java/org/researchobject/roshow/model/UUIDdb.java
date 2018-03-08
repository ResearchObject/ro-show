package org.researchobject.roshow.model;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "uuid_table")
public class UUIDdb implements Serializable {

    @Id
    @Type(type="uuid-char")
    private UUID uuid;

    @Column(name = "date")
    private String date;

    @Column(name = "roName")
    private String roName;

    public UUIDdb() {

    }

    public UUIDdb(UUID uuid, String date, String roName) {
        this.uuid = uuid;
        this.date = date;
        this.roName = roName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoName() {
        return roName;
    }

    public void setRoName(String roName) {
        this.roName = roName;
    }

    @Override
    public String toString() {
        return "UUIDdb{" +
                "uuid=" + uuid +
                ", date=" + date +
                ", roName=" + roName +
                '}';
    }
}
