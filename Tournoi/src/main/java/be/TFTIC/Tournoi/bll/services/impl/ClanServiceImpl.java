package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.ClanService;
import be.TFTIC.Tournoi.dal.repositories.ClanRepository;
import be.TFTIC.Tournoi.dal.repositories.JoinRequestRepository;
import be.TFTIC.Tournoi.dal.repositories.UserRepository;
import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.entities.JoinRequest;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.ClanRole;
import be.TFTIC.Tournoi.dl.enums.RequestStatus;
import be.TFTIC.Tournoi.dl.enums.UserRole;
import be.TFTIC.Tournoi.pl.models.User.UserDTO;
import be.TFTIC.Tournoi.pl.models.clan.CLanForm;
import be.TFTIC.Tournoi.pl.models.clan.CLanFormCreate;
import be.TFTIC.Tournoi.pl.models.clan.ClanDTO;
import be.TFTIC.Tournoi.pl.models.clan.JoinClanDTO;
import lombok.AllArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.stereotype.Service;
import be.TFTIC.Tournoi.il.utils.JwtUtils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClanServiceImpl implements ClanService{

    private final ClanRepository clanRepository;
    private final UserRepository userRepository;
    private final JoinRequestRepository joinRequestRepository;
    private final JwtUtils jwtUtils;

    @Override
    public ClanDTO createClan(CLanFormCreate cLanFormCreate, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Clan clan = cLanFormCreate.toEntity();
        clan.setName(cLanFormCreate.getName());
        clan.setPrivate(cLanFormCreate.isPrivate());
        clan.setMinimumTrophies(cLanFormCreate.getMinimumTrophies());
        clan.getRoles().put(userId, ClanRole.PRESIDENT);
        clan.setPresident(user.getUsername()) ;
        clan.getMembers().add(user);
        clanRepository.save(clan);
        return ClanDTO.fromEntity(clan);

    }

    @Override
    public ClanDTO getClanById(long id) {
        return ClanDTO.fromEntity(getById(id));
    }

    public Clan getById(long id){
        return clanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clan not found"));
    }

    @Override
    public List<ClanDTO> getAllClans() {
        return clanRepository.findAll().stream()
                .map(ClanDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public JoinClanDTO joinClan(Long clanId, User user) {
        Clan clan = getById(clanId);

        if (clan.getMembers().contains(user)) {
            return JoinClanDTO.fromEntity(clan, "You are already a member of this clan !");
        }

        Optional<JoinRequest> existingRequest = joinRequestRepository.findByUserAndClanAndStatus(user, clan, RequestStatus.PENDING);
        //TODO passer par une méthode implémenter dans un service plutôt que le repo

        if (existingRequest.isPresent()) {
            return JoinClanDTO.fromEntity(clan, "You already have a pending join request for this clan.");
        }

        if(!clan.getIsPrivate()&&user.getRanking()>= clan.getMinimumTrophies()){
            clan.getMembers().add(user);
            clan.getRoles().put(user.getId(), ClanRole.MEMBER);
            clanRepository.save(clan);
            return JoinClanDTO.fromEntity(clan, "Welcome in " + clan.getName() + " !");

        }

        else if (clan.getIsPrivate()){
            JoinRequest joinRequest= new JoinRequest();
            joinRequest.setClan(clan);
            joinRequest.setUser(user);
            joinRequest.setStatus(RequestStatus.PENDING);
            joinRequestRepository.save(joinRequest);
            return JoinClanDTO.fromEntity(clan, "Clan "+ clan.getName() +" is private, your request is pending ");
        }

        else{
            return JoinClanDTO.fromEntity(clan, "Cannot join this clan. Rank requirement not met ");
        }
    }

    @Override
    public ClanDTO updateClan(Long clanId, CLanForm cLanForm) {
        Clan clan= clanRepository.findById(clanId)
                .orElseThrow(()->new RuntimeException("Clan not found"));
        User user= userRepository.findById(cLanForm.getPresidentId())
                .orElseThrow(()->new RuntimeException("User not found"));

        ClanRole userRole= clan.getRoles().get(user);
        if(userRole!= ClanRole.PRESIDENT&& userRole != ClanRole.VICE_PRESIDENT){
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
    public void setRole(Long clanId, UserDTO targetUserDTO, ClanRole newRole, User currentUser) {
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new RuntimeException("Clan not found"));

// Get the current user's role in the clan
        ClanRole currentUserRole = clan.getRoles().get(currentUser.getId());

        // Ensure the current user is a member of the clan and has a role
        if (currentUserRole == null) {
            throw new RuntimeException("You are not a member of this clan.");
        }

        // Ensure that only the President or Vice President can set roles
        if (currentUserRole != ClanRole.PRESIDENT && currentUserRole != ClanRole.VICE_PRESIDENT) {
            throw new RuntimeException("Only the President or Vice President can set member roles.");
        }

        // Vice Presidents can only assign roles up to "Elder"
        if (currentUserRole == ClanRole.VICE_PRESIDENT && newRole.ordinal() >= ClanRole.VICE_PRESIDENT.ordinal()) {
            throw new RuntimeException("Vice President can only set roles up to Elder.");
        }

        // Role assignment logic
        if (currentUserRole == ClanRole.PRESIDENT && newRole == ClanRole.PRESIDENT) {
            // If the current President is assigning the President role to someone else,
            // they will become a Vice President
            clan.getRoles().put(currentUser.getId(), ClanRole.VICE_PRESIDENT);
        }

        User targetUser= userRepository.findById(targetUserDTO.getId())
                .orElseThrow(()->new RuntimeException("user not found"));
        // Update the role of the target user
        clan.getRoles().put(targetUser.getId(), newRole);

        // Save the updated clan entity to the repository
        clanRepository.save(clan);
    }
    @Override
    public void handleJoinRequest(Long clanId, User user, boolean accept) {
        // Fetch the clan from the repository
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new RuntimeException("Clan not found"));

        ClanRole currentUserRole = clan.getRoles().get(user.getId());

        // Check if the user making the request is a member of the clan and has the right role
        if (currentUserRole == null ||
                !(currentUserRole == ClanRole.PRESIDENT || currentUserRole == ClanRole.VICE_PRESIDENT)) {
            throw new RuntimeException("Only Presidents or Vice Presidents can handle join requests.");
        }

        // Handle join request (accept or reject)
        if (accept) {
            // Add user to the clan with default role as MEMBER
            clan.getRoles().put(user.getId(), ClanRole.MEMBER);
            userRepository.save(user); // Save user with updated clan association
        } else {
            throw new RuntimeException("Join request has been rejected.");
        }

        // Save the updated clan entity to the repository
        clanRepository.save(clan);
    }
}
