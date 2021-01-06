package org.uplifteds.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.uplifteds.entity.Storage;

public class StorageService {
    public Storage initStorageObject() {
        String parentContextFile = "storageContext.xml";
        ClassPathXmlApplicationContext parentContext = new ClassPathXmlApplicationContext(parentContextFile);
        Storage storage = parentContext.getBean("storage", Storage.class);
        parentContext.close();
        return storage;
    }
}
