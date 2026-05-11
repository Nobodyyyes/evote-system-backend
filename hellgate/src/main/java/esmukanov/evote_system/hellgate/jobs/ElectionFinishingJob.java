package esmukanov.evote_system.hellgate.jobs;

import esmukanov.evote_system.election_management.services.ElectionResultService;
import esmukanov.evote_system.election_management.services.ElectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElectionFinishingJob {

    private final ElectionService electionService;
    private final ElectionResultService electionResultService;

    @Scheduled(fixedDelayString = "${app.jobs.election-finish}")
    public void finishActiveElection() {
        LocalDateTime now = LocalDateTime.now();

        List<UUID> completedElectionIds = electionService.finishExpiredElections(now);

        if (completedElectionIds.isEmpty()) {
            return;
        }

        log.info("Completed elections count: {}", completedElectionIds.size());

        for (UUID electionId : completedElectionIds) {
            try {
                electionResultService.calculateResult(electionId.toString());
                log.info("Result calculated for electionId={}", electionId);
            } catch (Exception exception) {
                log.error("Cannot calculate result for electionId={}", electionId, exception);
            }
        }
    }
}
