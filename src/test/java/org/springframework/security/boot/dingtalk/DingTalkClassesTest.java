package org.springframework.security.boot.dingtalk;

import org.junit.jupiter.api.Test;
import org.springframework.security.boot.dingtalk.authentication.*;
import org.springframework.security.boot.dingtalk.exception.*;
import org.springframework.security.boot.dingtalk.userdetails.DingTalkPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for DingTalk authentication classes.
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class DingTalkClassesTest {

    // DingTalkTmpCodeAuthenticationToken
    @Test
    void tmpCodeAuthenticationTokenConstructor() {
        DingTalkTmpCodeAuthenticationToken token = new DingTalkTmpCodeAuthenticationToken("principal");
        assertThat(token.getPrincipal()).isEqualTo("principal");
        assertThat(token.isAuthenticated()).isFalse();
    }

    @Test
    void tmpCodeAuthenticationTokenWithCredentials() {
        DingTalkTmpCodeAuthenticationToken token = new DingTalkTmpCodeAuthenticationToken("principal", "credentials");
        assertThat(token.getPrincipal()).isEqualTo("principal");
        assertThat(token.getCredentials()).isEqualTo("credentials");
        assertThat(token.isAuthenticated()).isFalse();
    }

    @Test
    void tmpCodeAuthenticationTokenWithAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        DingTalkTmpCodeAuthenticationToken token = new DingTalkTmpCodeAuthenticationToken("principal", "credentials", authorities);
        assertThat(token.getPrincipal()).isEqualTo("principal");
        assertThat(token.getCredentials()).isEqualTo("credentials");
        assertThat(token.isAuthenticated()).isTrue();
        assertThat(token.getAuthorities()).hasSize(1);
    }

    @Test
    void tmpCodeAuthenticationTokenSetAuthenticated() {
        DingTalkTmpCodeAuthenticationToken token = new DingTalkTmpCodeAuthenticationToken("principal");
        token.setAuthenticated(false);
        assertThat(token.isAuthenticated()).isFalse();
    }

    @Test
    void tmpCodeAuthenticationTokenEraseCredentials() {
        Collection<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        DingTalkTmpCodeAuthenticationToken token = new DingTalkTmpCodeAuthenticationToken("principal", "credentials", authorities);
        token.eraseCredentials();
        assertThat(token.getCredentials()).isNull();
    }

    @Test
    void tmpCodeAuthenticationTokenSetPrincipal() {
        DingTalkTmpCodeAuthenticationToken token = new DingTalkTmpCodeAuthenticationToken("principal");
        token.setPrincipal("new-principal");
        assertThat(token.getPrincipal()).isEqualTo("new-principal");
    }

    // DingTalkScanCodeAuthenticationToken
    @Test
    void scanCodeAuthenticationTokenConstructor() {
        DingTalkScanCodeAuthenticationToken token = new DingTalkScanCodeAuthenticationToken("principal");
        assertThat(token.getPrincipal()).isEqualTo("principal");
        assertThat(token.isAuthenticated()).isFalse();
    }

    @Test
    void scanCodeAuthenticationTokenWithCredentials() {
        DingTalkScanCodeAuthenticationToken token = new DingTalkScanCodeAuthenticationToken("principal", "credentials");
        assertThat(token.getPrincipal()).isEqualTo("principal");
        assertThat(token.getCredentials()).isEqualTo("credentials");
    }

    @Test
    void scanCodeAuthenticationTokenWithAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        DingTalkScanCodeAuthenticationToken token = new DingTalkScanCodeAuthenticationToken("principal", "credentials", authorities);
        assertThat(token.isAuthenticated()).isTrue();
    }

    @Test
    void scanCodeAuthenticationTokenSetters() {
        DingTalkScanCodeAuthenticationToken token = new DingTalkScanCodeAuthenticationToken("principal");
        token.setUnionid("union-1");
        token.setOpenid("open-1");
        assertThat(token.getUnionid()).isEqualTo("union-1");
        assertThat(token.getOpenid()).isEqualTo("open-1");
    }

    @Test
    void scanCodeAuthenticationTokenEraseCredentials() {
        Collection<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        DingTalkScanCodeAuthenticationToken token = new DingTalkScanCodeAuthenticationToken("principal", "credentials", authorities);
        token.eraseCredentials();
        assertThat(token.getCredentials()).isNull();
    }

    // DingTalkMaAuthenticationToken
    @Test
    void maAuthenticationTokenConstructor() {
        DingTalkMaAuthenticationToken token = new DingTalkMaAuthenticationToken("principal");
        assertThat(token.getPrincipal()).isEqualTo("principal");
        assertThat(token.isAuthenticated()).isFalse();
    }

    @Test
    void maAuthenticationTokenWithCredentials() {
        DingTalkMaAuthenticationToken token = new DingTalkMaAuthenticationToken("principal", "credentials");
        assertThat(token.getPrincipal()).isEqualTo("principal");
        assertThat(token.getCredentials()).isEqualTo("credentials");
    }

    @Test
    void maAuthenticationTokenSetAuthenticated() {
        DingTalkMaAuthenticationToken token = new DingTalkMaAuthenticationToken("principal");
        token.setAuthenticated(false);
        assertThat(token.isAuthenticated()).isFalse();
    }

    @Test
    void maAuthenticationTokenEraseCredentials() {
        DingTalkMaAuthenticationToken token = new DingTalkMaAuthenticationToken("principal", "credentials");
        token.eraseCredentials();
        assertThat(token.getCredentials()).isNull();
    }

    // DingTalkTmpCodeLoginRequest
    @Test
    void tmpCodeLoginRequestConstructor() {
        DingTalkTmpCodeLoginRequest request = new DingTalkTmpCodeLoginRequest("corpId", "key", "token", "code");
        assertThat(request.getCorpId()).isEqualTo("corpId");
        assertThat(request.getKey()).isEqualTo("key");
        assertThat(request.getToken()).isEqualTo("token");
        assertThat(request.getCode()).isEqualTo("code");
    }

    @Test
    void tmpCodeLoginRequestSetters() {
        DingTalkTmpCodeLoginRequest request = new DingTalkTmpCodeLoginRequest("corpId", "key", "token", "code");
        request.setAccessToken("access-token");
        assertThat(request.getAccessToken()).isEqualTo("access-token");
    }

    // DingTalkScanCodeLoginRequest
    @Test
    void scanCodeLoginRequestConstructor() {
        DingTalkScanCodeLoginRequest request = new DingTalkScanCodeLoginRequest("corpId", "key", "token", "tmpCode");
        assertThat(request.getCorpId()).isEqualTo("corpId");
        assertThat(request.getKey()).isEqualTo("key");
        assertThat(request.getToken()).isEqualTo("token");
        assertThat(request.getLoginTmpCode()).isEqualTo("tmpCode");
    }

    // DingTalkMaLoginRequest
    @Test
    void maLoginRequestConstructor() {
        DingTalkMaLoginRequest request = new DingTalkMaLoginRequest("corpId", "key", "token", "authCode");
        assertThat(request.getCorpId()).isEqualTo("corpId");
        assertThat(request.getKey()).isEqualTo("key");
        assertThat(request.getToken()).isEqualTo("token");
        assertThat(request.getAuthCode()).isEqualTo("authCode");
    }

    @Test
    void maLoginRequestSetters() {
        DingTalkMaLoginRequest request = new DingTalkMaLoginRequest("corpId", "key", "token", "authCode");
        request.setAccessToken("access-token");
        assertThat(request.getAccessToken()).isEqualTo("access-token");
    }

    // Exception classes
    @Test
    void dingTalkAuthenticationServiceException() {
        DingTalkAuthenticationServiceException ex = new DingTalkAuthenticationServiceException("error");
        assertThat(ex.getMessage()).isEqualTo("error");
    }

    @Test
    void dingTalkAuthenticationServiceExceptionWithCause() {
        Throwable cause = new RuntimeException("root");
        DingTalkAuthenticationServiceException ex = new DingTalkAuthenticationServiceException("error", cause);
        assertThat(ex.getCause()).isEqualTo(cause);
    }

    @Test
    void dingTalkCodeExpiredException() {
        DingTalkCodeExpiredException ex = new DingTalkCodeExpiredException("expired");
        assertThat(ex.getMessage()).isEqualTo("expired");
    }

    @Test
    void dingTalkCodeExpiredExceptionWithCause() {
        Throwable cause = new RuntimeException("root");
        DingTalkCodeExpiredException ex = new DingTalkCodeExpiredException("expired", cause);
        assertThat(ex.getCause()).isEqualTo(cause);
    }

    @Test
    void dingTalkCodeIncorrectException() {
        DingTalkCodeIncorrectException ex = new DingTalkCodeIncorrectException("incorrect");
        assertThat(ex.getMessage()).isEqualTo("incorrect");
    }

    @Test
    void dingTalkCodeIncorrectExceptionWithCause() {
        Throwable cause = new RuntimeException("root");
        DingTalkCodeIncorrectException ex = new DingTalkCodeIncorrectException("incorrect", cause);
        assertThat(ex.getCause()).isEqualTo(cause);
    }

    @Test
    void dingTalkCodeNotFoundException() {
        DingTalkCodeNotFoundException ex = new DingTalkCodeNotFoundException("not found");
        assertThat(ex.getMessage()).isEqualTo("not found");
    }

    @Test
    void dingTalkCodeNotFoundExceptionWithCause() {
        Throwable cause = new RuntimeException("root");
        DingTalkCodeNotFoundException ex = new DingTalkCodeNotFoundException("not found", cause);
        assertThat(ex.getCause()).isEqualTo(cause);
    }

    // DingTalkPrincipal
    @Test
    void dingTalkPrincipalConstructor() {
        DingTalkPrincipal principal = new DingTalkPrincipal("user", "pass", "ROLE_USER");
        assertThat(principal.getUsername()).isEqualTo("user");
        assertThat(principal.getPassword()).isEqualTo("pass");
    }

    @Test
    void dingTalkPrincipalGetters() {
        DingTalkPrincipal principal = new DingTalkPrincipal("user", "pass", "ROLE_USER");
        // Default values are null since Lombok @Data generates getters
        assertThat(principal.getUsername()).isEqualTo("user");
        assertThat(principal.getPassword()).isEqualTo("pass");
    }

    // DingTalkMatchedAuthenticationEntryPoint
    @Test
    void matchedAuthenticationEntryPointSupports() {
        DingTalkMatchedAuthenticationEntryPoint entryPoint = new DingTalkMatchedAuthenticationEntryPoint();
        assertThat(entryPoint.supports(new DingTalkAuthenticationServiceException("test"))).isTrue();
        assertThat(entryPoint.supports(new DingTalkCodeIncorrectException("test"))).isTrue();
        assertThat(entryPoint.supports(new DingTalkCodeExpiredException("test"))).isTrue();
    }

    // DingTalkMatchedAuthenticationFailureHandler
    @Test
    void matchedAuthenticationFailureHandlerSupports() {
        DingTalkMatchedAuthenticationFailureHandler handler = new DingTalkMatchedAuthenticationFailureHandler();
        assertThat(handler.supports(new DingTalkAuthenticationServiceException("test"))).isTrue();
        assertThat(handler.supports(new DingTalkCodeIncorrectException("test"))).isTrue();
        assertThat(handler.supports(new DingTalkCodeExpiredException("test"))).isTrue();
    }
}
