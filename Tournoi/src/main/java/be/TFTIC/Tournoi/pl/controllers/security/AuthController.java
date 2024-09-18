package be.TFTIC.Tournoi.pl.controllers.security;


import be.TFTIC.Tournoi.bll.services.security.AuthService;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.il.utils.JwtUtils;
import be.TFTIC.Tournoi.pl.models.authDTO.UserLoginForm;
import be.TFTIC.Tournoi.pl.models.authDTO.UserRegisterForm;
import be.TFTIC.Tournoi.pl.models.authDTO.UserTokenDTO;
import be.TFTIC.Tournoi.pl.models.messageErreur.ErrorDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginForm form) {
        try {
            User u = authService.login(form.username(), form.password());
            return ResponseEntity.ok(mapUserToken(u));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDTO("Invalid credentials"));
        }
    }

    private UserTokenDTO mapUserToken(User u) {

        String token = jwtUtils.generateToken(u);
        return UserTokenDTO.fromEntity(u,token);
    }
}
