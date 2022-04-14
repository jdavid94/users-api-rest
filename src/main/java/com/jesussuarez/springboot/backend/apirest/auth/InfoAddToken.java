package com.jesussuarez.springboot.backend.apirest.auth;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.jesussuarez.springboot.backend.apirest.models.entity.User;
import com.jesussuarez.springboot.backend.apirest.models.services.IUserService;


@Component
public class InfoAddToken implements TokenEnhancer {
	
	@Autowired
	private IUserService userService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		User user = userService.findByEmail(authentication.getName());
		Map<String, Object> info = new HashMap<>();
		info.put("aditional_info", "Token ".concat(authentication.getName()));
		info.put("email", user.getEmail());
		info.put("name", user.getName());			
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}
