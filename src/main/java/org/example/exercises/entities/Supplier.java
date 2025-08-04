package org.example.exercises.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_importer", nullable = false)
    private boolean isImporter;

    @OneToMany(mappedBy = "supplier")
    private Set<Part> parts;

    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }
}

