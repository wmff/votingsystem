package local.vda.votingsystem.web;

import local.vda.votingsystem.AuthorizedUser;
import local.vda.votingsystem.model.Vote;
import local.vda.votingsystem.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(VoteRestController.REST_URL)
public class VoteRestController {
    static final String REST_URL = "/rest/vote";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> vote(@RequestParam int restaurantId,
                                     @AuthenticationPrincipal AuthorizedUser authUser) {
        Vote created = service.set(restaurantId, authUser.getId());
        log.info("created {}", created);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }
}
