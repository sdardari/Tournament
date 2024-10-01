package be.TFTIC.Tournoi.bll.services.service;

import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.pl.models.clan.ClanDTO;
import be.TFTIC.Tournoi.pl.models.messageException.MessageDTO;

public interface JoinRequestService {


    void deleteRequestByClan(Clan clan);
    MessageDTO handleJoinRequest(Long clanId, Long userId, User user, boolean accept);
    ClanDTO joinClan(Long clanId, User user);

}
