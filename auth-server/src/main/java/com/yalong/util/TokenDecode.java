package com.yalong.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liuyalong
 */
public class TokenDecode {
    private static final String PUBLIC_KEY = "publicKey.cer";

    private static String publickey = "";

    /***
     * 获取用户信息
     */
    public static Map<String, Object> getUserInfo() {
        //获取授权信息
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        //令牌解码
        return decodeToken(details.getTokenValue());
    }

    /***
     * 读取令牌数据
     */
    public static Map decodeToken(String token) {
        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(getPubKey()));

        //获取Jwt原始内容
        String claims = jwt.getClaims();
        return JSON.parseObject(claims, Map.class);
    }


    /**
     * 获取非对称加密公钥 Key
     *
     * @return 公钥 Key
     */
    public static String getPubKey() {
        try {
            CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
//        FileInputStream fis = new FileInputStream());

            ClassPathResource classPathResource = new ClassPathResource(PUBLIC_KEY);
            InputStream fis = classPathResource.getInputStream();

            // X509Certificate cert = (X509Certificate) certificatefactory.generateCertificate(fis);
            Certificate cert = certificatefactory.generateCertificate(fis);
            PublicKey pk = cert.getPublicKey();
            String pkString = new Base64().encodeToString(pk.getEncoded());
            //这里需要拼接开头和结尾,不然无法解析出来结果,可能有其他验证方法
            return "-----BEGIN PUBLIC KEY-----" + pkString + "-----END PUBLIC KEY-----";
        } catch (Exception ioe) {
            return null;
        }
    }

    /**
     * 获取公钥方式2,从文件中读取明文public key
     */
    public static String getPubKey2() {
        if (!StringUtils.isEmpty(publickey)) {
            return publickey;
        }
        Resource resource = new ClassPathResource(PUBLIC_KEY);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            publickey = br.lines().collect(Collectors.joining("\n"));
            return publickey;
        } catch (IOException ioe) {
            return null;
        }
    }

}
