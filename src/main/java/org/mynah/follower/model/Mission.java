package org.mynah.follower.model;

public class Mission {

    private String id;
    private String name;
    private String skill;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkill() {
        return this.skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        return "Mission [id=" + id + ", name=" + name + ", skill=" + skill + "]";
    }

}
