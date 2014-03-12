package com.github.mpi.users_and_access.domain;

public class User {

    public static final User ANONYMOUS = new User("Anonymous", "Anonymous");
    
    private final String username;
    private final String displayName;

    public User(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
    }
    
    @Override
    public boolean equals(Object x) {

        if(!(x instanceof User)){
            return false;
        }
        
        User other = (User) x;
        return username.equals(other.username);
    }
    
    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s<%s>", displayName, username);
    }

    public String username() {
        return username;
    }

    public String displayName() {
        return displayName;
    }
}
