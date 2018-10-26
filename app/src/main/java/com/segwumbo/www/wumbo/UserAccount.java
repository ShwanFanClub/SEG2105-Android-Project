package com.segwumbo.www.wumbo;

public class UserAccount {

    private String id;
    private String username;
    private String password;
    private String email;
    private String role;

    public UserAccount() {
        // Default constructor required for calls to DataSnapshot.getValue(UserAccount.class)
    }

    public UserAccount(String id, String username, String password, String role){
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public void setRole(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }
}
