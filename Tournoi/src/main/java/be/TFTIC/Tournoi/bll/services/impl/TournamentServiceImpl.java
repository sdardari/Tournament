package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.TournamentService;
import be.TFTIC.Tournoi.dal.repositories.TournamentRepository;
import be.TFTIC.Tournoi.dl.entities.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;

    @Override
    public List<Tournament> getAll() {
        return tournamentRepository.findAll();
    }

    @Override
    public Tournament getById(Long id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The tournament with id " + id + " not found"));
    }
// TODO DELETE 
    @Override
    public Long create(Tournament tournament) {
        tournament.setDateDebut(LocalDateTime.now());
        return tournamentRepository.save(tournament).getTournamentId();
    }

    @Override
    public void update(Long id, Tournament tournament) {
        Tournament existingTournament = getById(id);
        existingTournament.setName(tournament.getName());
        existingTournament.setLocation(tournament.getLocation());
        existingTournament.setNbPlace(tournament.getNbPlace());
        tournamentRepository.save(existingTournament);
    }

    @Override
    public void tournamentFinish(Long id) {
        Tournament existingTournament = getById(id);
        existingTournament.setDateFin(LocalDateTime.now());
        tournamentRepository.save(existingTournament);
    }

    @Override
    public void delete(Long id) {
        Tournament existingTournament = getById(id);
        tournamentRepository.delete(existingTournament);
    }
}
