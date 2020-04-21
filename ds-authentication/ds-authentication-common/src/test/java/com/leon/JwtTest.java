package com.leon;

import com.leon.authentication.entity.UserInfo;
import com.leon.authentication.utils.JwtUtils;
import com.leon.authentication.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

/**
 * @author LeonMac
 * @description
 * @date 2020/4/9
 */

public class JwtTest {
    private static final String pubKeyPath = "C:\\Users\\Administrator\\Desktop\\rsa\\rsa.pub";

    private static final String priKeyPath = "C:\\Users\\Administrator\\Desktop\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    /*
    * @description:
                测试生成公钥私钥
    * @date 2020/4/9 15:51
    * @param
    * @return void
    */
    @Test
    public void testRsa() throws IOException, NoSuchAlgorithmException {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }


    @Before
    public void testGetToken() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    /*
    * @description:
                测试生成token
    * @date 2020/4/9 15:50
    * @param
    * @return void
    */
    @Test
    public void testGenerateToken() throws Exception {
        //生成token
        String token = JwtUtils.generateToken(new UserInfo(207878132L, "hahahaahhahhahahhaa"), privateKey, 5);
        System.out.println("token=" + token);
    }

    @Test
    public void testParseToken() {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjA3ODc4MTMyLCJ1c2VybmFtZSI6ImhhaGFoYWFoaGFoaGFoYWhoYWEiLCJleHAiOjE1ODY4NTQwMzl9.PKjNeL3JTr3TRGP-XfTMQVKNx0M8P6mwwZa1jzxPNlf-Fmf9fiKVbN4WGDvkEcU48tz7EWJ0njNSwJRxgaiF36gGGThfUyoVv3MrKICPNelWjjO62cgh6HGB5QJWGucm5DOf94uMf4_t37rxqoVYTs70RY4NXTHYYISmR-ELgUM";
        //解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id=" + user.getId());
        System.out.println("username=" + user.getUsername());

    }


}
