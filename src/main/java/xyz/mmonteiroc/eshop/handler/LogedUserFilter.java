package xyz.mmonteiroc.eshop.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.mmonteiroc.eshop.exceptions.TokenNotRecivedException;
import xyz.mmonteiroc.eshop.exceptions.TokenNotValidException;
import xyz.mmonteiroc.eshop.exceptions.TokenOverdatedException;
import xyz.mmonteiroc.eshop.manager.TokenManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 21/08/2020
 * Package: xyz.mmonteiroc.eshop.handler
 * Project: eshop
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LogedUserFilter implements HandlerInterceptor {

    @Autowired
    TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws TokenNotRecivedException, TokenOverdatedException, TokenNotValidException {

        /*
         * Options petition we let pass
         * */
        if (request.getMethod().equals("OPTIONS")) return true;


        String auth = request.getHeader("Authorization");

        if (auth != null && !auth.isEmpty()) {
            String token = auth.replace("Bearer ", "");
            request.setAttribute("userToken", token);
            return tokenManager.validateToken(token);
        } else {
            throw new TokenNotRecivedException();
        }
    }
}
