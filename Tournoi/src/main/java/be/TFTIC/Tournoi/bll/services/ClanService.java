package be.TFTIC.Tournoi.bll.services;


import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.ClanRole;
import be.TFTIC.Tournoi.pl.models.User.UserDTO;
import be.TFTIC.Tournoi.pl.models.clan.*;

import java.util.List;

public interface ClanService {

    ClanDTO createClan(ClanFormCreate cLanFormCreate, Long id);
    ClanDTO getClanById(long id);
    Clan getById(long id);
    List<ClanDTO> getAllClans();
    JoinClanDTO joinClan(Long clanId, User user);
    ClanDTO updateClan(User user,Long id, ClanFormEdit clanFormEdit);
    void deleteClan(Long id, User user);
    void leaveClan(Long clanId, Long userId);
    void setRole(Long clanId, UserDTO user, ClanRole role, User currentUser);
    void handleJoinRequest(Long clanId, Long userId, User user, boolean accept);

}
