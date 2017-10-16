package org.researchobject.roshow.storage;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class UUIDutility {

    @Autowired
    UUIDrepository uuiDrepository;

    public UUID createAndCheckUUIDexists() throws NullPointerException{
        UUID uuid = UUID.randomUUID();
//        if (uuiDrepository.findByUuid(uuid) != null){
//            return createAndCheckUUIDexists();
//        }
        return uuid;
    }
}
