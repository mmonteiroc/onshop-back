package xyz.mmonteiroc.eshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Code created by: mmonteiroc
 * Email: miguelmonteiroclaveri@gmail.com
 * Github: https://github.com/mmonteiroc
 * LinkedIn: https://www.linkedin.com/in/mmonteiroc/?locale=en_US
 * Date of creation: 22/08/2020
 * Package: xyz.mmonteiroc.eshop.exceptions
 * Project: eshop
 */
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Token not valid")
public class TokenNotValidException extends Exception {
}
