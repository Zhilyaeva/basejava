package ru.ithub.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.ithub.basejava.exception.ExistStorageException;
import ru.ithub.basejava.exception.NotExistStorageException;
import ru.ithub.basejava.exception.StorageException;
import ru.ithub.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME3 = new Resume(UUID_3);
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME4 = new Resume(UUID_4);
    private static final String DUMMY = "dummy";
    private static final Resume DUMMY_TEST = new Resume(DUMMY);


    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME1);
        storage.save(RESUME2);
        storage.save(RESUME3);
    }

    @Test
    public void update() throws Exception {
        Resume newResume = new Resume(UUID_2);
        storage.update(newResume);
        Assert.assertSame(newResume, storage.get(newResume.getUuid()));
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(DUMMY_TEST);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME4);
        Assert.assertEquals(4, storage.size());
        Assert.assertNotNull(RESUME4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(new Resume(UUID_2));
    }

    @Test(expected = StorageException.class)
    public void saveOverFlow() throws Exception {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("Unexpected StorageException");
        }
        storage.save(new Resume());
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() throws Exception {
        storage.get(RESUME2.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void getAll() throws Exception {
        Resume[] expected = new Resume[3];
        expected[0] = new Resume(UUID_1);
        expected[1] = new Resume(UUID_2);
        expected[2] = new Resume(UUID_3);
        Assert.assertArrayEquals(expected, storage.getAll());
        Assert.assertEquals(expected.length, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete("uuid4");
        Assert.assertEquals(3, storage.size());
        storage.get("uuid4");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }
}