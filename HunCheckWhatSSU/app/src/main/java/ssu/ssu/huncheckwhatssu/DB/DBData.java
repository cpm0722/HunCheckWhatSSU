package ssu.ssu.huncheckwhatssu.DB;

public class DBData {
    int key;
    int foreign_key;
    String name;
    String another[];

    public DBData(int key, int foreign_key, String name, String[] another) {
        this.key = key;
        this.foreign_key = foreign_key;
        this.name = name;
        this.another = another;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getForeignkey() {
        return foreign_key;
    }

    public void setForeignkey(int foreign_key) {
        this.foreign_key = foreign_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getAnother() {
        return another;
    }

    public void setAnother(String[] another) {
        this.another = another;
    }
}