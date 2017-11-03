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

    public UUIDdb() {

    }

    public UUIDdb(UUID uuid, String date) {
        this.uuid = uuid;
        this.date = date;
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

    @Override
    public String toString() {
        return "UUIDdb{" +
                "uuid=" + uuid +
                ", date=" + date +
                '}';
    }
}
