package org.mynah.follower.model;

import java.util.ArrayList;
import java.util.List;

public class Follower {

    private String id;
    private String name;
    private List<Integer> skills = new ArrayList<Integer>();

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

    public List<Integer> getSkills() {
        return this.skills;
    }

    public void setSkills(List<Integer> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Follower [id=" + id + ", skills=" + skills + ", name=" + name + "]";
    }

}
