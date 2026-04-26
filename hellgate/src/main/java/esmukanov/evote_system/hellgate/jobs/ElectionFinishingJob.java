package esmukanov.evote_system.hellgate.jobs;

import esmukanov.evote_system.election_management.services.ElectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElectionFinishingJob {

    private final ElectionService electionService;

    @Scheduled(fixedDelayString = "${app.jobs.election-finish}")
    public void finishActiveElection() {
        int finishedElectionCount = electionService.finishActiveElections(LocalDateTime.now());
        if (finishedElectionCount > 0) {
            log.info("Finished active elections. Count: {}", finishedElectionCount);
        }
    }
}
