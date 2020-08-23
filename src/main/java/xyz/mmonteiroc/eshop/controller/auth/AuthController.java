package xyz.mmonteiroc.eshop.controller.auth;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.mmonteiroc.eshop.entity.User;
import xyz.mmonteiroc.eshop.exceptions.MandatoryParamsNotRecivedException;
import xyz.mmonteiroc.eshop.exceptions.ParamsNotPermitedException;
import xyz.mmonteiroc.eshop.exceptions.TokenNotValidException;
import xyz.mmonteiroc.eshop.exceptions.TokenOverdatedException;
import xyz.mmonteiroc.eshop.manager.TokenManager;
import xyz.mmonteiroc.eshop.manager.UserManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 22/08/2020
 * Package: xyz.mmonteiroc.eshop.controller.auth
 * Project: eshop
 */
@RestController
public class AuthController {

    @Autowired
    private UserManager userManager;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private Gson gson;

    @PostMapping("/auth/account")
    public ResponseEntity<String> createAccount(@RequestBody String json) throws MandatoryParamsNotRecivedException, ParamsNotPermitedException {
        User user = gson.fromJson(json, User.class);

        if (user.getIdUser() != null) throw new ParamsNotPermitedException();
        if (user.getPasswd() == null
                || user.getEmail() == null
                || user.getName() == null
                || user.getSurname() == null)
            throw new MandatoryParamsNotRecivedException("Missing params to create user");

        user.setAdmin(false);
        this.userManager.create(user);
        return ResponseEntity.ok("Account created correctly");
    }

    @PostMapping("/auth")
    public ResponseEntity<Map<String, String>> doLogin(@RequestBody String json) throws MandatoryParamsNotRecivedException {
        User user = gson.fromJson(json, User.class);

        if (user.getEmail() == null || user.getPasswd() == null)
            throw new MandatoryParamsNotRecivedException("Params not recived");

        User toVerify = this.userManager.findByEmail(user.getEmail());

        boolean validated = this.userManager.validatePassword(user, toVerify);
        if (!validated) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String access_token = this.tokenManager.createAccessToken(toVerify);
        String refresh_token = this.tokenManager.createRefreshToken(toVerify);

        Map<String, String> toReturn = new HashMap<>();
        toReturn.put("access_token", access_token);
        toReturn.put("refresh_token", refresh_token);

        return ResponseEntity.ok(toReturn);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<Map<String, String>> refreshTokenLogin(@RequestBody String json) throws TokenNotValidException, MandatoryParamsNotRecivedException, TokenOverdatedException {

        JsonObject object = gson.fromJson(json, JsonObject.class);
        String token = "";
        if (object.get("refresh_token") == null) throw new MandatoryParamsNotRecivedException("Token not recived");
        else token = object.get("refresh_token").getAsString();

        this.tokenManager.validateToken(token);

        User user = this.tokenManager.getUsuarioFromToken(token);
        String access_token = this.tokenManager.createAccessToken(user);
        String refresh_token = this.tokenManager.createRefreshToken(user);

        Map<String, String> toReturn = new HashMap<>();
        toReturn.put("access_token", access_token);
        toReturn.put("refresh_token", refresh_token);

        return ResponseEntity.ok(toReturn);
    }
}
