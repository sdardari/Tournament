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
import lombok.AllArgsConstructor;
import org.hibernate.mapping.Join;
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

      //  if(!clan.getIsPrivate()&&user.getRanking()>= clan.getMinimumTrophies()){
      //      clan.getMembers().add(user);
      //      clan.getRoles().put(user.getId(), ClanRole.MEMBER);
      //      clanRepository.save(clan);
      //      return JoinClanDTO.fromEntity(clan, "Welcome in " + clan.getName() + " !");
//
       // }

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
        Clan clan= clanRepository.findById(clanId)
                .orElseThrow(()->new RuntimeException("Clan not found"));

        Long userId= user.getId();
        ClanRole userRole= clan.getRoles().get(userId);
        if(userRole!= ClanRole.PRESIDENT&& userRole != ClanRole.VICE_PRESIDENT ){
            throw new RuntimeException("Only the President or Vice President of the clan itself can update the clan ");
        }
        clan.setName(clanFormEdit.getName());
        clan.setPrivate(clanFormEdit.isPrivate());
      //  clan.setMinimumTrophies(clanFormEdit.getMinimumTrophies());

        clan=clanRepository.save(clan);
        return ClanDTO.fromEntity(clan);
    }

    @Override
    public void deleteClan(Long clanId, User user) {
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new RuntimeException("Clan not found"));
        //TODO pourquoi le user avec id president ne peut pas supprimer le clan ???

         Map<Long, ClanRole> roles = clan.getRoles();
         ClanRole userRole = roles.get(user.getId());

        if (user.getRole() != UserRole.ADMIN  && userRole != ClanRole.PRESIDENT) {
            throw new RuntimeException("Only Admin or President can delete the clan." + userRole + " " + user.getRole());
        }
        joinRequestService.deleteRequestByClan(clan);
        clanRepository.delete(clan);
    }

    @Override
    public void leaveClan(Long clanId, Long userId) {
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new RuntimeException("Clan not found"));
        
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
                    throw new RuntimeException("Promote first a president or vice-president to leave a clan.");
                }
            }
            clan.getMembers().removeIf(member -> member.getId().equals(userId));
            clan.getRoles().remove(userId);
            clanRepository.save(clan);

        } else {
            throw new RuntimeException("User is not a member of this clan");
        }
    }

    @Override
    public void setRole(Long clanId, UserDTO targetUserDTO, ClanRole newRole, User currentUser) {
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new RuntimeException("Clan not found"));

        ClanRole currentUserRole = clan.getRoles().get(currentUser.getId());
        if (currentUserRole == null) {
            throw new RuntimeException("You are not a member of this clan.");
        }
        if (currentUserRole != ClanRole.PRESIDENT && currentUserRole != ClanRole.VICE_PRESIDENT) {
            throw new RuntimeException("Only the President or Vice President can set member roles.");
        }
        if (currentUserRole == ClanRole.VICE_PRESIDENT && newRole.ordinal() >= ClanRole.VICE_PRESIDENT.ordinal()) {
            throw new RuntimeException("Vice President can only set roles up to Elder.");
        }
        if (currentUserRole == ClanRole.PRESIDENT && newRole == ClanRole.PRESIDENT) {
            clan.getRoles().put(currentUser.getId(), ClanRole.VICE_PRESIDENT);
        }

        User targetUser= userRepository.findById(targetUserDTO.getId())
                .orElseThrow(()->new RuntimeException("user not found"));

        clan.getRoles().put(targetUser.getId(), newRole);
        clanRepository.save(clan);
    }


    @Override
    public void handleJoinRequest(Long clanId, Long userIdToChange, User currentUser, boolean accept) {
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new RuntimeException("Clan not found"));

        ClanRole currentUserRole = clan.getRoles().get(currentUser.getId());
        if (currentUserRole == null ||
                !(currentUserRole == ClanRole.PRESIDENT || currentUserRole == ClanRole.VICE_PRESIDENT)) {
            throw new RuntimeException("Only Presidents or Vice Presidents can handle join requests.");
        }
        User userToChange= userRepository.findById(userIdToChange)
                .orElseThrow(()-> new RuntimeException("User not found"));

        JoinRequest joinRequest= joinRequestRepository.findByUserAndClanAndStatus(userToChange,clan,RequestStatus.PENDING)
                .orElseThrow(()-> new RuntimeException("Join request not found "));

        if (accept) {
            clan.getRoles().put(userToChange.getId(), ClanRole.MEMBER);
            userRepository.save(userToChange);
            joinRequest.setStatus(RequestStatus.APPROVED);
        } else {
            joinRequest.setStatus(RequestStatus.REJECTED);
            throw new RuntimeException("Join request has been rejected.");
        }
        clanRepository.save(clan);
    }
}
