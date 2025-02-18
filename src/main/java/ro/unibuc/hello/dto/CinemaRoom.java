package ro.unibuc.hello.dto;

import org.apache.tomcat.jni.Local;
import ro.unibuc.hello.data.LocationEntity;

public class CinemaRoom {

    Integer number;

    Location location;

    public CinemaRoom(){};

    public CinemaRoom(LocationEntity locationEntity, Integer number){
        Location location1 = new Location(locationEntity.address, locationEntity.name, locationEntity.phoneNumber);
        this.location = location1;

        this.number = number;
    }
    public Location getLocation(){
        return location;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number){
        this.number = number;
    }

    public void setLocation(LocationEntity locationEntity) {
        Location location1 = new Location(locationEntity.address, locationEntity.name, locationEntity.phoneNumber);
        this.location = location1;
    }
}
