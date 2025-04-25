package io.back3nd.backend.domain.api;

import io.back3nd.backend.domain.app.UserService;
import io.back3nd.backend.domain.dto.SignUpRequest;
import io.back3nd.backend.domain.dto.UserDetailsImpl;
import io.back3nd.backend.domain.dto.UserResponse;
import io.back3nd.backend.global.common.CommonResponse;
import io.back3nd.backend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static io.back3nd.backend.global.common.StatusCode.USER_CREATED;
import static io.back3nd.backend.global.common.StatusCode.USER_FOUND;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<CommonResponse<Object>> signUp(@RequestBody SignUpRequest request) {

        userService.signUp(request);

        return ResponseEntity.status(USER_CREATED.getStatus())
                        .body(CommonResponse.from(
                                USER_CREATED.getMessage()
                        ));
    }

    @GetMapping("/user/info")
    public ResponseEntity<CommonResponse<UserResponse>> findUser(@AuthenticationPrincipal UserDetailsImpl user){

        if(user==null){
            throw new CustomException("로그인을 해주세요.");
        }

        UserResponse response = userService.findUser(user.getUsername());

        return ResponseEntity.status(USER_FOUND.getStatus())
                .body(CommonResponse.from(
                        USER_FOUND.getMessage(), response
                ));
    }
}
