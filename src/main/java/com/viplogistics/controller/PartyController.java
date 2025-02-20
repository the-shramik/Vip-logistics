package com.viplogistics.controller;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Party;
import com.viplogistics.service.IPartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * PartyController - Handles API requests for parties.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/party")
@CrossOrigin("*")
public class PartyController {
    private final IPartyService partyService;

    /**
     * Adds a new party.
     *
     * @param party The party details to be added.
     * @return {@link ResponseEntity} containing the result of the operation.
     */
    @PostMapping("/add-party")
    public ResponseEntity<?> addParty(@RequestBody Party party){
        return ResponseEntity.status(HttpStatus.OK).body(partyService.addParty(party));
    }

    /**
     * Retrieves all parties.
     *
     * @return {@link ResponseEntity} containing a list of all parties.
     */
    @GetMapping("/get-parties")
    public ResponseEntity<?> getAllParties(){
        return ResponseEntity.status(HttpStatus.OK).body(partyService.getAllParties());
    }

    /**
     * Retrieves the latest party number.
     *
     * @return {@link ResponseEntity} containing the latest party number.
     */
    @GetMapping("/get-latest-party-no")
    public ResponseEntity<?> getLatestPartyNo(){
        return ResponseEntity.status(HttpStatus.OK).body(partyService.getLatestPartyNo());
    }

    /**
     * Updates an existing party's details.
     *
     * @param party The updated party details.
     * @return {@link ResponseEntity} with the updated party details.
     */
    @PutMapping("/update-party")
    public ResponseEntity<?> updateParty(@RequestBody Party party){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(partyService.updateParty(party));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Deletes a party by its party number.
     *
     * @param partyNo The party number of the party to be deleted.
     * @return {@link ResponseEntity} containing the result of the delete operation.
     */
    @DeleteMapping("/delete-party")
    public ResponseEntity<?> deleteParty(@RequestParam String partyNo){
        ApiResponse<?> response = partyService.deleteParty(partyNo);

        if(response.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Searches for parties based on search criteria.
     *
     * @param searchData The search criteria for finding parties.
     * @return {@link ResponseEntity} containing a list of matching parties.
     */
    @GetMapping("/get-searched-parties/{searchData}")
    public ResponseEntity<?> getSearchedParties(@PathVariable String searchData){
        return ResponseEntity.status(HttpStatus.OK).body(partyService.getSearchedParties(searchData));
    }

    /**
     * Retrieves the total count of parties.
     *
     * @return {@link ResponseEntity} containing the total number of parties.
     */
    @GetMapping("/get-party-count")
    public ResponseEntity<?> getPartyCount(){
        return ResponseEntity.status(HttpStatus.OK).body(partyService.getPartyCount());
    }

    @PostMapping("/test-party")
    public ResponseEntity<?> testParty(){
        partyService.testParty();
        return ResponseEntity.ok("All parties added!");
    }

    @PostMapping("/test-delete-Party")
    public ResponseEntity<?> deleteParty(){
        partyService.deleteTestParty();
        return ResponseEntity.ok("All parties deleted!");
    }

}
