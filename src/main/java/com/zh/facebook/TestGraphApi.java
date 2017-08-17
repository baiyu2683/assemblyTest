package com.zh.facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.User;
import org.junit.Test;

/**
 * Created by zh on 2017-08-04.
 */
public class TestGraphApi {

    private String appId = "1750597118303212";
    private String appSecret = "f97e44196d1c498303403d623652cedf";
    private String accessTokenUrl = "https://graph.facebook.com/oauth/access_token?client_id=1750597118303212&client_secret=f97e44196d1c498303403d623652cedf&grant_type=client_credentials";
    private String accessToken = "1750597118303212|lDkHE1AjYkzZXAjB_CZ0JbCPMSQ";

    @Test
    public void getAccessToken() {
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST);
        User user = facebookClient.fetchObject("me", User.class);
        System.out.println(user.toString());
    }
}
