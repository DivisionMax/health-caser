package com.csc3003.healthcaser;

/**
 * Created by Alan Berman on 8/10/2015.
 */
public class User {
    private String username;
    private String password;
    private boolean loggedIn;
    private String state;
    //Represents the statistics of all health cases
    //the user has done
  //  private ArrayList<CaseStatistics> statistics;

    public User()
    {
        //statistics = new ArrayList<CaseStatistics>();
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setState(String state)
    {
        this.state=state;
    }

    public String State()
    {
        return state;
    }
    //Check user is in the correct state
    public boolean takeHealthCase()
    {
        if ((!(state.equals("takingTest"))) && loggedIn)
            return true;
        return false;
        //doSomething
    }
}
