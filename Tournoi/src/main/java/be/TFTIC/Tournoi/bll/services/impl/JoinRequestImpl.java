package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.JoinRequestService;
import be.TFTIC.Tournoi.dal.repositories.JoinRequestRepository;
import be.TFTIC.Tournoi.dl.entities.Clan;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JoinRequestImpl implements JoinRequestService {

    private JoinRequestRepository joinRequestRepository;

    @Override
    public void deleteRequestByClan(Clan clan) {
        joinRequestRepository.deleteByClan(clan);

    }
}
