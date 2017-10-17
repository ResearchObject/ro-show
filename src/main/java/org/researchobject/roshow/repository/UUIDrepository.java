package org.researchobject.roshow.repository;

import java.util.UUID;

import org.researchobject.roshow.model.UUIDdb;
import org.springframework.data.repository.CrudRepository;

public interface UUIDrepository extends CrudRepository<UUIDdb, UUID>{
    UUIDdb findByUuid(UUID uuid);
}
