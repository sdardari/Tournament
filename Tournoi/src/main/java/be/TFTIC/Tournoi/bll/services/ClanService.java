package be.TFTIC.Tournoi.bll.services;


import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.ClanRole;
import be.TFTIC.Tournoi.pl.models.User.UserDTO;
import be.TFTIC.Tournoi.pl.models.clan.CLanForm;
import be.TFTIC.Tournoi.pl.models.clan.CLanFormCreate;
import be.TFTIC.Tournoi.pl.models.clan.ClanDTO;
import be.TFTIC.Tournoi.pl.models.clan.JoinClanDTO;

import java.util.List;

public interface ClanService {

    ClanDTO createClan(CLanFormCreate cLanFormCreate, Long id);
    ClanDTO getClanById(long id);
    Clan getById(long id);
    List<ClanDTO> getAllClans();
    JoinClanDTO joinClan(Long clanId, User user);
    ClanDTO updateClan(Long id, CLanForm cLanForm);
    void deleteClan(Long id, User user);
    void leaveClan(Long clanId, User user);
    void setRole(Long clanId, UserDTO user, ClanRole role, User currentUser);
    void handleJoinRequest(Long clanId, User user, boolean accept);

}
