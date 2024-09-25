package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.ClanService;
import be.TFTIC.Tournoi.bll.services.JoinRequestService;
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
import be.TFTIC.Tournoi.pl.models.clan.*;
import be.TFTIC.Tournoi.pl.models.message.MessageDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import be.TFTIC.Tournoi.il.utils.JwtUtils;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClanServiceImpl implements ClanService{

    private final ClanRepository clanRepository;
    private final UserRepository userRepository;
    private final JoinRequestRepository joinRequestRepository;
    private final JoinRequestService joinRequestService;
    private final JwtUtils jwtUtils;


    @Override
    public ClanDTO createClan(ClanFormCreate cLanFormCreate, Long userId) {
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
        return ClanDTO.fromEntity(clan, "clan created ");

    }

    @Override
    public ClanDTO getClanById(long id) {
        return ClanDTO.fromEntity(getById(id), "Result of your research");
    }

    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clan not found"));
    }

    public Clan getById(long id){
        return clanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clan not found"));
    }

    @Override
    public List<ClanDTO> getAllClans() {
        return clanRepository.findAll().stream()
                .map((Clan clan) -> ClanDTO.fromEntity(clan, ""))
                .collect(Collectors.toList());
    }

    @Override
    public JoinClanDTO joinClan(Long clanId, User user) {
        Clan clan = getById(clanId);
        Long userId= user.getId();

        boolean userAlreadyMemberOfClan= clan.getMembers().stream()
                .anyMatch(member-> member.getId().equals(userId));

        if (userAlreadyMemberOfClan) {
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
    public ClanDTO updateClan(User user,Long clanId, ClanFormEdit clanFormEdit) {
        Clan clan= getById(clanId);

        Long userId= user.getId();
        ClanRole userRole= clan.getRoles().get(userId);
        if(userRole!= ClanRole.PRESIDENT&& userRole != ClanRole.VICE_PRESIDENT ){
            return ClanDTO.fromEntity(clan,"Clan can only modified by the President, Vice-president of the clan ");
        }
        clan.setName(clanFormEdit.getName());
        clan.setPrivate(clanFormEdit.isPrivate());
        clan.setMinimumTrophies(clanFormEdit.getMinimumTrophies());

        clan=clanRepository.save(clan);
        return ClanDTO.fromEntity(clan, "Clan succesfully modified");
    }

    @Override
    public MessageDTO deleteClan(Long clanId, User user) {
        Clan clan = getById(clanId);
         Map<Long, ClanRole> roles = clan.getRoles();
         ClanRole userRole = roles.get(user.getId());

        if (user.getRole() != UserRole.ADMIN  && userRole != ClanRole.PRESIDENT) {
            return new MessageDTO("Only Admin or President can delete the clan.");
        }
        joinRequestService.deleteRequestByClan(clan);
        clanRepository.delete(clan);
        return new MessageDTO("Clan succesfully deleted");
    }

    @Override
    public MessageDTO leaveClan(Long clanId, Long userId) {
        Clan clan = getById(clanId);
        
        boolean isMember = clan.getMembers().stream()
                .anyMatch(member -> member.getId().equals(userId));

        if (isMember) {
            if (clan.getRoles().get(userId) == ClanRole.PRESIDENT) {
                Long newPresidentId = clan.getRoles().entrySet().stream()
                        .filter(entry -> entry.getValue() == ClanRole.VICE_PRESIDENT)
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .orElse(null);
                
                if (newPresidentId != null) {
                    clan.getRoles().put(newPresidentId, ClanRole.PRESIDENT);
                } else {
                    return new MessageDTO("Promote first a president or vice-president to leave a clan.");
                }
            }
            clan.getMembers().removeIf(member -> member.getId().equals(userId));
            clan.getRoles().remove(userId);
            clanRepository.save(clan);
            return new MessageDTO("Succesfully left the clan ");

        } else {
            throw new RuntimeException("User is not a member of this clan");
        }

    }

    @Override
    public MessageDTO setRole(Long clanId, UserDTO targetUserDTO, ClanRole newRole, User currentUser) {
        Clan clan = getById(clanId);

        ClanRole currentUserRole = clan.getRoles().get(currentUser.getId());
        if (currentUserRole == null) {
            return new MessageDTO("You are not a member of this clan.");
        }
        if (currentUserRole != ClanRole.PRESIDENT && currentUserRole != ClanRole.VICE_PRESIDENT) {
            return new MessageDTO("Only the President or Vice President can set member roles.");
        }
        if (currentUserRole == ClanRole.VICE_PRESIDENT && newRole.ordinal() >= ClanRole.VICE_PRESIDENT.ordinal()) {
            return new MessageDTO("Vice President can only set roles up to Elder.");
        }
        if (currentUserRole == ClanRole.PRESIDENT && newRole == ClanRole.PRESIDENT) {
            clan.getRoles().put(currentUser.getId(), ClanRole.VICE_PRESIDENT);
        }

        User targetUser= getUserById(targetUserDTO.getId());

        clan.getRoles().put(targetUser.getId(), newRole);
        clanRepository.save(clan);
        return new MessageDTO("Role succesfully changed");
    }


    @Override
    public MessageDTO handleJoinRequest(Long clanId, Long userIdToChange, User currentUser, boolean accept) {
        Clan clan = getById(clanId);

        ClanRole currentUserRole = clan.getRoles().get(currentUser.getId());
        if (currentUserRole == null ||
                !(currentUserRole == ClanRole.PRESIDENT || currentUserRole == ClanRole.VICE_PRESIDENT)) {
            return new MessageDTO("Only Presidents or Vice Presidents can handle join requests.");
        }
        User userToChange= getUserById(userIdToChange);

        JoinRequest joinRequest= joinRequestRepository.findByUserAndClanAndStatus(userToChange,clan,RequestStatus.PENDING)
                .orElseThrow(()-> new RuntimeException("Join request not found "));

        if (accept) {
            clan.getRoles().put(userToChange.getId(), ClanRole.MEMBER);
            userRepository.save(userToChange);
            joinRequest.setStatus(RequestStatus.APPROVED);
            clanRepository.save(clan);
            return new MessageDTO("Join request accepted, User has been added to the clan ");

        } else {
            joinRequest.setStatus(RequestStatus.REJECTED);
            return new MessageDTO("Join request has been rejected.");
        }

    }
}
