package entity;

import lombok.AllArgsConstructor;
import lombok.Data;


public class Wx {
    private String name;
    private String password;

    public Wx(){}
    public Wx(String name,String password){
        this.name=name;
        this.password=password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
