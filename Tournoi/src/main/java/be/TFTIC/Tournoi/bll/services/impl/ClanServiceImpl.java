package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.ClanService;
import be.TFTIC.Tournoi.dal.repositories.ClanRepository;
import be.TFTIC.Tournoi.dal.repositories.UserRepository;
import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.CLanRole;
import be.TFTIC.Tournoi.dl.enums.UserRole;
import be.TFTIC.Tournoi.pl.models.User.UserDTO;
import be.TFTIC.Tournoi.pl.models.clan.CLanForm;
import be.TFTIC.Tournoi.pl.models.clan.CLanFormCreate;
import be.TFTIC.Tournoi.pl.models.clan.ClanDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import be.TFTIC.Tournoi.il.utils.JwtUtils;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClanServiceImpl implements ClanService{

    private final ClanRepository clanRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Override
    public ClanDTO createClan(CLanFormCreate cLanFormCreate, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Clan clan = cLanFormCreate.toEntity();
        clan.setName(cLanFormCreate.getName());
        clan.setPrivate(cLanFormCreate.isPrivate());
        clan.setMinimumTrophies(cLanFormCreate.getMinimumTrophies());
        clan.setPresident(CLanRole.PRESIDENT); //pas trop surs pourquoi
        clan.getRoles().put(user, CLanRole.PRESIDENT);
        clanRepository.save(clan);
        return ClanDTO.fromEntity(clan);
    }

    @Override
    public ClanDTO getClanById(long id) {
        Clan clan = clanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clan not found"));
        return ClanDTO.fromEntity(clan);
    }

    @Override
    public List<ClanDTO> getAllClans() {
        return clanRepository.findAll().stream()
                .map(ClanDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Clan joinClan(Long clanId, User user) {
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(()-> new RuntimeException("Clan not found "));
        if(!clan.getIsPrivate()&&user.getRanking()>= clan.getMinimumTrophies()){
            clan.getMembers().add(user);
            clan.getRoles().put(user,CLanRole.MEMBER);
            return clanRepository.save(clan);
        }else {
            throw new RuntimeException("Cannot join this clan. Either it's private or rank requirement not met.");
        }// essayer de voir si un membre essaie de rejoindre a nouveau?
    }

    @Override
    public ClanDTO updateClan(Long clanId, CLanForm cLanForm) {
        Clan clan= clanRepository.findById(clanId)
                .orElseThrow(()->new RuntimeException("Clan not found"));
        User user= userRepository.findById(cLanForm.getPresidentId())
                .orElseThrow(()->new RuntimeException("User not found"));

        CLanRole userRole= clan.getRoles().get(user);
        if(userRole!= CLanRole.PRESIDENT&& userRole != CLanRole.VICE_PRESIDENT){
            throw new RuntimeException("Only the President or Vice Presidetn can update the clan ");
        }
        clan.setName(cLanForm.getName());
        clan.setPrivate(cLanForm.isPrivate());
        clan.setMinimumTrophies(cLanForm.getMinimumTrophies());

        clan=clanRepository.save(clan);
        return ClanDTO.fromEntity(clan);
    }

    @Override
    public void deleteClan(Long id, User user ) {
        Clan clan =clanRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Clan not found"));
        userRepository.findById(user.getId())
                .orElseThrow(()->new RuntimeException("User not found"));

        if(user.getRole()!= UserRole.ADMIN){
            throw new RuntimeException("Only Admin can delete the clan.");
        }
        clanRepository.delete(clan);

    }

    @Override
    public void leaveClan(Long clanId, User user) {
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(()->new RuntimeException("User not found"));
        if(clan.getMembers().contains(user)){
            clan.getMembers().remove(user);
            clan.getRoles().remove(user);
            clanRepository.save(clan);
        }else{
            throw new RuntimeException("User is not a member of this clan");
        }


    }

    @Override
    public void setRole(Long clanId, UserDTO targetUserDTO, CLanRole newRole, User currentUser) {
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new RuntimeException("Clan not found"));

// Get the current user's role in the clan
        CLanRole currentUserRole = clan.getRoles().get(currentUser.getId());

        // Ensure the current user is a member of the clan and has a role
        if (currentUserRole == null) {
            throw new RuntimeException("You are not a member of this clan.");
        }

        // Ensure that only the President or Vice President can set roles
        if (currentUserRole != CLanRole.PRESIDENT && currentUserRole != CLanRole.VICE_PRESIDENT) {
            throw new RuntimeException("Only the President or Vice President can set member roles.");
        }

        // Vice Presidents can only assign roles up to "Elder"
        if (currentUserRole == CLanRole.VICE_PRESIDENT && newRole.ordinal() >= CLanRole.VICE_PRESIDENT.ordinal()) {
            throw new RuntimeException("Vice President can only set roles up to Elder.");
        }

        // Role assignment logic
        if (currentUserRole == CLanRole.PRESIDENT && newRole == CLanRole.PRESIDENT) {
            // If the current President is assigning the President role to someone else,
            // they will become a Vice President
            clan.getRoles().put(currentUser, CLanRole.VICE_PRESIDENT);
        }

        User targetUser= userRepository.findById(targetUserDTO.getId())
                .orElseThrow(()->new RuntimeException("user not found"));
        // Update the role of the target user
        clan.getRoles().put(targetUser, newRole);

        // Save the updated clan entity to the repository
        clanRepository.save(clan);
    }
    @Override
    public void handleJoinRequest(Long clanId, User user, boolean accept) {
        // Fetch the clan from the repository
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new RuntimeException("Clan not found"));

        CLanRole currentUserRole = clan.getRoles().get(user.getId());

        // Check if the user making the request is a member of the clan and has the right role
        if (currentUserRole == null ||
                !(currentUserRole == CLanRole.PRESIDENT || currentUserRole == CLanRole.VICE_PRESIDENT)) {
            throw new RuntimeException("Only Presidents or Vice Presidents can handle join requests.");
        }

        // Handle join request (accept or reject)
        if (accept) {
            // Add user to the clan with default role as MEMBER
            clan.getRoles().put(user, CLanRole.MEMBER);
            user.setClan(clan);
            userRepository.save(user); // Save user with updated clan association
        } else {
            throw new RuntimeException("Join request has been rejected.");
        }

        // Save the updated clan entity to the repository
        clanRepository.save(clan);
    }
}
