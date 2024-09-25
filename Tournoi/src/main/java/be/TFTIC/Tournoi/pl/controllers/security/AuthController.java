package be.TFTIC.Tournoi.pl.controllers.security;


import be.TFTIC.Tournoi.bll.services.security.AuthService;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.il.utils.JwtUtils;
import be.TFTIC.Tournoi.pl.models.authDTO.UserLoginForm;
import be.TFTIC.Tournoi.pl.models.authDTO.UserRegisterForm;
import be.TFTIC.Tournoi.pl.models.authDTO.UserTokenDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<UserTokenDTO> register(@Valid@RequestBody UserRegisterForm form) {
        User u = authService.register(form.toEntity());
        return ResponseEntity.ok(mapUserToken(u));
    }

    // a changer le ? en voir project
    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<UserTokenDTO> login(@Valid @RequestBody UserLoginForm form) {
            User u = authService.login(form.username(), form.password());
            return ResponseEntity.ok(mapUserToken(u));
    }

    private UserTokenDTO mapUserToken(User u) {

        String token = jwtUtils.generateToken(u);
        return UserTokenDTO.fromEntity(u,token);
    }
}
