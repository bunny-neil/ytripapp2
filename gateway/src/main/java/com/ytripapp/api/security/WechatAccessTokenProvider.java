package com.ytripapp.api.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserApprovalRequiredException;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import java.util.HashMap;
import java.util.Map;

public class WechatAccessTokenProvider implements AccessTokenProvider {

    private AuthorizationCodeAccessTokenProvider delegate;

    public WechatAccessTokenProvider() {
        delegate = new AuthorizationCodeAccessTokenProvider();
    }

    @Override
    public OAuth2AccessToken obtainAccessToken(OAuth2ProtectedResourceDetails details,
                                               AccessTokenRequest parameters)
        throws UserRedirectRequiredException, UserApprovalRequiredException, AccessDeniedException {
        try {
            return delegate.obtainAccessToken(details, parameters);
        }
        catch (UserRedirectRequiredException ex) {
            resetRedirectParams(ex);
            throw ex;
        }
    }

    @Override
    public boolean supportsResource(OAuth2ProtectedResourceDetails resource) {
        return delegate.supportsResource(resource);
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(OAuth2ProtectedResourceDetails resource,
                                                OAuth2RefreshToken refreshToken,
                                                AccessTokenRequest request) throws UserRedirectRequiredException {
        try {
            return delegate.refreshAccessToken(resource, refreshToken, request);
        }
        catch (UserRedirectRequiredException ex) {
            resetRedirectParams(ex);
            throw ex;
        }
    }

    @Override
    public boolean supportsRefresh(OAuth2ProtectedResourceDetails resource) {
        return delegate.supportsRefresh(resource);
    }

    private void resetRedirectParams(UserRedirectRequiredException ex) {
        Map<String, String> params = ex.getRequestParams();
        params = changeClientIdToAppId(params);
        ex.getRequestParams().clear();
        ex.getRequestParams().putAll(params);
    }


    private Map<String, String> changeClientIdToAppId(Map<String, String> params) {
        Map<String, String> results = new HashMap<>();
        results.putAll(params);

        String appId = results.get("client_id");
        results.remove("client_id");
        results.put("appid", appId);
        return results;
    }
}
