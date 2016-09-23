package messanger.resources;

import messanger.model.Profile;
import messanger.service.ProfileService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path(value = "/profiles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfileResource {
    static ProfileService profileService=new ProfileService();

    @GET
    public List<Profile> getAllProfiles(){
        return profileService.getAllProfiles();
    }
    @POST
    public Profile addProfile(Profile profile){
        return profileService.addProfile(profile);
    }

    @GET
    @Path("/{profileName}")
    public Profile getProfile(@PathParam("profileName") String profileName){
        return profileService.getProfile(profileName);
    }

    @PUT
    @Path("/{profileName}")
    public Profile updateProfile(@PathParam("profileName") String profileName,Profile profile){
        profile.setProfileName(profileName);
        return  profileService.updateProfile(profile);
    }


    @DELETE
    @Path("/{profileName}")
    public Profile updateProfile(@PathParam("profileName") String profileName){
        return  profileService.removeProfile(profileName);
    }

}
