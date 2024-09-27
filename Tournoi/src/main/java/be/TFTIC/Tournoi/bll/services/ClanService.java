package be.TFTIC.Tournoi.bll.services;


import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.ClanRole;
import be.TFTIC.Tournoi.pl.models.User.UserDTO;
import be.TFTIC.Tournoi.pl.models.clan.*;
import be.TFTIC.Tournoi.pl.models.message.MessageDTO;

import java.util.List;

public interface ClanService {

    ClanDTO createClan(ClanFormCreate cLanFormCreate, Long id);
    ClanDTO getClanById(long id);
    Clan getById(long id);
    List<ClanDTO> getAllClans();
    ClanDTO updateClan(User user,Long id, ClanFormEdit clanFormEdit);
    MessageDTO deleteClan(Long id, User user);
    MessageDTO leaveClan(Long clanId, Long userId);
    MessageDTO setRole(Long clanId, UserDTO user, ClanRole role, User currentUser);

}
