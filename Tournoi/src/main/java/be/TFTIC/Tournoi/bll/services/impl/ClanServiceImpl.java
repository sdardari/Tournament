package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.exception.authority.NotEnoughAuthorityException;
import be.TFTIC.Tournoi.bll.exception.exist.DoNotExistException;
import be.TFTIC.Tournoi.bll.exception.member.NotMemberException;
import be.TFTIC.Tournoi.bll.services.ChatService;
import be.TFTIC.Tournoi.bll.exception.request.BadRequestException;
import be.TFTIC.Tournoi.bll.services.ClanService;
import be.TFTIC.Tournoi.bll.services.JoinRequestService;
import be.TFTIC.Tournoi.bll.services.UserService;
import be.TFTIC.Tournoi.dal.repositories.ClanRepository;
import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.ClanRole;
import be.TFTIC.Tournoi.dl.enums.UserRole;
import be.TFTIC.Tournoi.pl.models.User.UserDTO;
import be.TFTIC.Tournoi.pl.models.chat.ChatDTO;
import be.TFTIC.Tournoi.pl.models.clan.*;
import be.TFTIC.Tournoi.pl.models.messageException.MessageDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClanServiceImpl implements ClanService{

    private final ClanRepository clanRepository;

    private final UserService userService;
    private final JoinRequestService joinRequestService;
    private final ChatService chatService;

    @Override
    public ClanDTO createClan(ClanFormCreate cLanFormCreate, Long userId) {
        User user = getUserById(userId);

        Clan clan = cLanFormCreate.toEntity();
        clan.setName(cLanFormCreate.getName());
        clan.setPrivate(cLanFormCreate.isPrivate());
        clan.getRoles().put(userId, ClanRole.PRESIDENT);
        clan.setPresident(user.getUsername()) ;
        clan.getMembers().add(user);

        clanRepository.save(clan);

        Set<Long> membersIds =clan.getMembers().stream()
                .map(User::getId).collect(Collectors.toSet());
        String chatName= clan.getName() + " Clan chat";
        ChatDTO clanChat = chatService.createClanChat(userId, membersIds, chatName);

        return ClanDTO.fromEntity(clan, "clan created successfully with chat");

    }

    @Override
    public ClanDTO getClanById(long id) {
        return ClanDTO.fromEntity(getById(id), "Result of your research");
    }

    public User getUserById(long userId) {
        return userService.getUserById(userId);
    }

    public Clan getById(long id){
        return clanRepository.findById(id)
                .orElseThrow(() -> new DoNotExistException("Clan not found"));
    }

    @Override
    public List<ClanDTO> getAllClans() {
        return clanRepository.findAll().stream()
                .map((Clan clan) -> ClanDTO.fromEntity(clan, ""))
                .collect(Collectors.toList());
    }

    @Override
    public ClanDTO updateClan(User user,Long clanId, ClanFormEdit clanFormEdit) {
        Clan clan= getById(clanId);

        Long userId= user.getId();
        ClanRole userRole= clan.getRoles().get(userId);
        if(userRole!= ClanRole.PRESIDENT&& userRole != ClanRole.VICE_PRESIDENT ){
            throw new NotEnoughAuthorityException("Clan can only be updated by the President or Vice-President of the clan");
        }
        clan.setName(clanFormEdit.getName());
        clan.setPrivate(clanFormEdit.isPrivate());

        clan=clanRepository.save(clan);
        return ClanDTO.fromEntity(clan, "Clan succesfully modified");
    }

    @Override
    public MessageDTO deleteClan(Long clanId, User user) {
        Clan clan = getById(clanId);
         Map<Long, ClanRole> roles = clan.getRoles();
         ClanRole userRole = roles.get(user.getId());

        if (user.getRole() != UserRole.ADMIN  && userRole != ClanRole.PRESIDENT) {
            throw new NotEnoughAuthorityException("Only Admin or President can delete the clan.");
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
                    throw new BadRequestException("Promote first a president or vice-president to leave a clan.");
                }
            }
            clan.getMembers().removeIf(member -> member.getId().equals(userId));
            clan.getRoles().remove(userId);
            clanRepository.save(clan);
            return new MessageDTO("Succesfully left the clan ");

        } else {
            throw new NotMemberException("User is not a member of this clan");
        }

    }

    @Override
    public MessageDTO setRole(Long clanId, UserDTO targetUserDTO, ClanRole newRole, User currentUser) {
        Clan clan = getById(clanId);

        ClanRole currentUserRole = clan.getRoles().get(currentUser.getId());
        if (currentUserRole == null) {
            throw  new NotMemberException("You are not a member of this clan.");
        }
        if (currentUserRole != ClanRole.PRESIDENT && currentUserRole != ClanRole.VICE_PRESIDENT) {
            throw  new NotEnoughAuthorityException("Only the President or Vice President can set member roles.");
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

   }
