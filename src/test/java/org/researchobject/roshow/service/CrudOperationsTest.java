package org.researchobject.roshow.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.researchobject.roshow.RoApplication;
import org.researchobject.roshow.model.UUIDdb;
import org.researchobject.roshow.repository.UUIDrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoApplication.class)
public class CrudOperationsTest {
    
    
    @Autowired
    UUIDrepository uuiDrepository;


    private List<UUIDdb> uuiDdbList = new LinkedList<>();

    private void addToRepository (UUID uuid, String date){
        uuiDrepository.save(new UUIDdb(uuid, date));
    }

    @Before
    public void before() {
        addToRepository(UUID.randomUUID(), Mockito.anyString());
        addToRepository(UUID.randomUUID(), Mockito.anyString());
        addToRepository(UUID.randomUUID(), Mockito.anyString());
        addToRepository(UUID.randomUUID(), Mockito.anyString());
        addToRepository(UUID.randomUUID(), Mockito.anyString());

        for (UUIDdb uuiDdb : uuiDrepository.findAll()) {
            uuiDdbList.add(uuiDdb);
        }
    }


    @Test
    public void testFindAll() {
        Assert.assertTrue(uuiDdbList.size() == 5);
    }

    @Test
    public void testFindByUUID() {
        UUIDdb uuiDdb = uuiDdbList.get(1);
        UUID uuid = uuiDdb.getUuid();
        Assert.assertTrue(uuiDrepository.findByUuid(uuid).getUuid().equals(uuid));
    }
}
