package ru.ithub.basejava.storage;

import ru.ithub.basejava.model.Resume;

import java.util.Arrays;

public class ArrayStorage {

    private Resume[] storage = new Resume[10000];

    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (getPosition(resume.getUuid()) >= 0) {
            System.out.println("Resume already exists");
        } else if (size >= storage.length) {
            System.out.println("No free space");
        } else {
            storage[size++] = resume;
        }
    }

    public Resume get(String uuid) {
        int position = getPosition(uuid);

        if (position == -1) {
            System.out.println("Resume was not found");
            return null;
        } else {
            return storage[position];
        }
    }

    public void delete(String uuid) {
        int position = getPosition(uuid);

        if (position >= 0) {
            storage[position] = storage[size - 1];
            storage[--size] = null;
        } else {
            System.out.println("Resume was not found");
        }
    }

    public void update(Resume resume) {
        int position = getPosition(resume.getUuid());

        if (position >= 0) {
            storage[position] = resume;
        } else {
            System.out.println("Resume was not updated");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int getPosition(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}