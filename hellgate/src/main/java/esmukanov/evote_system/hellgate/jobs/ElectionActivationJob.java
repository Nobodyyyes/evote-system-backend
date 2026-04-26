package esmukanov.evote_system.hellgate.jobs;

import esmukanov.evote_system.election_management.services.ElectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElectionActivationJob {

    private final ElectionService electionService;

    @Scheduled(fixedDelayString = "${app.jobs.election-active}")
    @Transactional
    public void activateScheduledElection() {
        int activatedElectionCount = electionService.activateElections(LocalDateTime.now());
        if (activatedElectionCount > 0) {
            log.info("Activated scheduled elections. Count: {}", activatedElectionCount);
        }
    }
}
