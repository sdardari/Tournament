package be.TFTIC.Tournoi.bll.services;

import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.pl.models.clan.JoinClanDTO;
import be.TFTIC.Tournoi.pl.models.message.MessageDTO;

public interface JoinRequestService {


    void deleteRequestByClan(Clan clan);
    MessageDTO handleJoinRequest(Long clanId, Long userId, User user, boolean accept);
    JoinClanDTO joinClan(Long clanId, User user);

}
