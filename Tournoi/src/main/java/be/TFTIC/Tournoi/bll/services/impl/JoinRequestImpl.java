package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.service.JoinRequestService;
import be.TFTIC.Tournoi.dal.repositories.ClanRepository;
import be.TFTIC.Tournoi.dal.repositories.JoinRequestRepository;
import be.TFTIC.Tournoi.dal.repositories.UserRepository;
import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.entities.JoinRequest;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.ClanRole;
import be.TFTIC.Tournoi.dl.enums.RequestStatus;
import be.TFTIC.Tournoi.pl.models.clan.ClanDTO;
import be.TFTIC.Tournoi.pl.models.messageException.MessageDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class JoinRequestImpl implements JoinRequestService {

    private final JoinRequestRepository joinRequestRepository;
    private final ClanRepository clanRepository;
    private final UserRepository userRepository;

    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clan not found"));
    }

    public Clan getById(long id){
        return clanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clan not found"));
    }

    @Override
    public void deleteRequestByClan(Clan clan) {
        joinRequestRepository.deleteByClan(clan);

    }
    @Override
    public ClanDTO joinClan(Long clanId, User user) {
        Clan clan = getById(clanId);
        Long userId= user.getId();

        boolean userAlreadyMemberOfClan= clan.getMembers().stream()
                .anyMatch(member-> member.getId().equals(userId));

        if (userAlreadyMemberOfClan) {
            return ClanDTO.fromEntity(clan, "You are already a member of this clan !");
        }

        Optional<JoinRequest> existingRequest = joinRequestRepository.findByUserAndClanAndStatus(user, clan, RequestStatus.PENDING);
        if (existingRequest.isPresent()) {
            return ClanDTO.fromEntity(clan, "You already have a pending join request for this clan.");
        }

        if(!clan.getIsPrivate()&&user.getRanking()>= clan.getMinimumTrophies()){
            clan.getMembers().add(user);
            clan.getRoles().put(user.getId(), ClanRole.MEMBER);
            clanRepository.save(clan);
            return ClanDTO.fromEntity(clan, "Welcome in " + clan.getName() + " !");

        }

        else if (clan.getIsPrivate()){
            JoinRequest joinRequest= new JoinRequest();
            joinRequest.setClan(clan);
            joinRequest.setUser(user);
            joinRequest.setStatus(RequestStatus.PENDING);
            joinRequestRepository.save(joinRequest);
            return ClanDTO.fromEntity(clan, "Clan "+ clan.getName() +" is private, your request is pending ");
        }

        else{
            return ClanDTO.fromEntity(clan, "Cannot join this clan. Rank requirement not met ");
        }
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
