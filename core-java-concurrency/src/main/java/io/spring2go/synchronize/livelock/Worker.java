package io.spring2go.synchronize.livelock;

public class Worker {
    private String name;
    private boolean active;

    public Worker (String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public String getName () {
        return name;
    }

    public boolean isActive () {
        return active;
    }

    public synchronized void work (SharedResource sharedResource, Worker otherWorker) {
        while (active) {
            // wait for the resource to become available.
            if (sharedResource.getOwner() != this) {
                try {
                    wait(10);
                } catch (InterruptedException e) {
                   //ignore
                }
                continue;
            }

            // If other worker is also active let it do it's work first
            if (otherWorker.isActive()) {
                System.out.println(getName() +
                            " : handover the resource to the worker " +
                                                       otherWorker.getName());
                sharedResource.setOwner(otherWorker);
                continue;
            }

            //now use the sharedResource
            System.out.println(getName() + ": working on the shared resource");
            active = false;
            sharedResource.setOwner(otherWorker);
        }
    }
}