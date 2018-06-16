package io.spring2go.synchronize.livelock;

public class SharedResource {
    private Worker owner;

    public SharedResource (Worker d) {
        owner = d;
    }

    public Worker getOwner () {
        return owner;
    }

    public synchronized void setOwner (Worker d) {
        owner = d;
    }
}