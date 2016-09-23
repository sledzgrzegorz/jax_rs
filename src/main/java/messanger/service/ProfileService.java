package messanger.service;

import messanger.database.DatabaseClass;
import messanger.model.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileService {

    private Map<String,Profile> profiles= DatabaseClass.getProfiles();

    public ProfileService(){
        profiles.put("koushik",new Profile(1L,"koushik","Koushik","Kothagal"));
    }

    public List<Profile>getAllProfiles(){
        return new ArrayList<>(profiles.values());
    }

    public Profile getProfile(String name){
        return profiles.get(name);
    }

    public Profile addProfile(Profile profile){
        profile.setId(profiles.size()+1);
        profiles.put(profile.getProfileName(),profile);
        return profile;
    }

    public Profile updateProfile(Profile profile){
        if(profile.getId()<=0){
            return null;
        }
        profiles.put(profile.getProfileName(),profile);
        return profile;
    }
    public Profile removeProfile(String profileName){
        return this.profiles.remove(profileName);
    }

}
