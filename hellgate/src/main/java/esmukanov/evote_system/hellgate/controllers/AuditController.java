package esmukanov.evote_system.hellgate.controllers;

import esmukanov.evote_system.audit.models.AuditLog;
import esmukanov.evote_system.audit.services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping
    public List<AuditLog> getLogsByObject(@RequestParam String objectType,
                                          @RequestParam UUID objectId) {
        return auditService.getLogsByObject(objectType, objectId);
    }
}
