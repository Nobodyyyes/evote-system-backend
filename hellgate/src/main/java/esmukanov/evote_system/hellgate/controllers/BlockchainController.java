package esmukanov.evote_system.hellgate.controllers;

import esmukanov.evote_system.blockchain.models.BlockchainRecord;
import esmukanov.evote_system.blockchain.services.BlockchainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/blockchain")
@RequiredArgsConstructor
public class BlockchainController {

    private final BlockchainService blockchainService;

    @GetMapping("/records/{relatedObjectId}")
    public List<BlockchainRecord> getRecords(@PathVariable UUID relatedObjectId) {
        return blockchainService.getRecordsByRelatedObjectId(relatedObjectId);
    }
}
