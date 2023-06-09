package model.entites;

import java.io.Serializable;

public class Department implements Serializable{
    
    private int id;
    private String name;
    private static final long serialVersionUID = 1L;
    
    public Department (){  
    }

    public Department (String name, int id) {
        this.setName(name);
        this.setId(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Department other = (Department) obj;
        if (id != other.id)
            return false;
        return true;
    }
    
    public String toString () {
        return "[Id: " + this.getId() + ", Name: " + this.getName() + "]";
    }

}
