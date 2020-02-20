package com.mimi.mlibrary.model.users;

import com.mimi.mlibrary.model.works.Copy;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Libraries")
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String address;

    @OneToMany(mappedBy = "library")
    private List<Copy> copies;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Copy> getCopies() {
        return copies;
    }

    public void setCopies(List<Copy> copies) {
        this.copies = copies;
    }
}