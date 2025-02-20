package com.viplogistics.service.impl;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Party;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.repository.IPartyRepository;
import com.viplogistics.service.IPartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PartyServiceImpl implements IPartyService {

    private final IPartyRepository partyRepository;

    @Override
    public Party addParty(Party party) {

        if (party.getPartyNo() == null || party.getPartyNo().isEmpty()) {
            String lastPartyNo = partyRepository.findTopByOrderByPartyNoDesc();
            String newPartyNo = generateNewPartyNo(lastPartyNo).get("newPartyNo");
            party.setPartyNo(newPartyNo);
        }
        party.setPartyDate(LocalDate.now());
        System.out.println("party: "+party.getPartyName());
        return partyRepository.save(party);
    }

    private Map<String, String> generateNewPartyNo(String lastPartyNo) {
        Map<String, String> result = new HashMap<>();

        if (lastPartyNo == null || lastPartyNo.isEmpty()) {
            result.put("newPartyNo", "P0001");
            return result;
        }

        int numericPart = Integer.parseInt(lastPartyNo.substring(1));
        String newPartyNo = String.format("P%04d", numericPart + 1);

        result.put("newPartyNo", newPartyNo);

        return result;
    }


    @Override
    public List<Party> getAllParties() {
        return partyRepository.findAll();
    }

    @Override
    public Map<String, String> getLatestPartyNo() {
        String lastPartyNo = partyRepository.findTopByOrderByPartyNoDesc();

        return generateNewPartyNo(lastPartyNo);
    }

    @Override
    public Party updateParty(Party party) throws ResourceNotFoundException {

        Party existedParty=partyRepository.findById(party.getPartyNo())
                .orElseThrow(()->new ResourceNotFoundException("Party not found with party no: "+party.getPartyNo()));

        existedParty.setPartyName(party.getPartyName());
        existedParty.setDistrict(party.getDistrict());
        existedParty.setAccountType(party.getAccountType());
        existedParty.setBalanceMarkAmount(party.getBalanceMarkAmount());
        existedParty.setBalanceMarkType(party.getBalanceMarkType());
        existedParty.setCodeNo(party.getCodeNo());
        existedParty.setDivision(party.getDivision());
        existedParty.setGstNumber(party.getGstNumber());
        existedParty.setOpeningBalance(party.getOpeningBalance());
        existedParty.setStateCode(party.getStateCode());
        existedParty.setPartyDate(existedParty.getPartyDate());
        existedParty.setAddress(party.getAddress());
        return partyRepository.save(existedParty);
    }

    @Override
    public ApiResponse<?> deleteParty(String partyNo) {
        Optional<Party> optionalParty = partyRepository.findById(partyNo);

        if(optionalParty.isPresent()){
            Party party=optionalParty.get();
            partyRepository.delete(party);
            return new ApiResponse<>(true,"Party deleted successfully",null, HttpStatus.OK);
        }else{
            return new ApiResponse<>(false,"Party not deleted",null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Party> getSearchedParties(String searchData) {
        return partyRepository.findBySearchData(searchData);
    }

    @Override
    public Map<String, Long> getPartyCount() {
        HashMap<String,Long> result=new HashMap<>();

        result.put("totalParties",partyRepository.count());

        return result;
    }

    @Override
    public void testParty() {
        List<Party> parties = new ArrayList<>();

        for (int i = 1; i <= 2000; i++) {
            Party party = new Party();
            party.setPartyNo("PARTY-"+i);
            party.setPartyName("Party " + i);
            party.setAccountType("Type " + (i % 5));
            party.setBalanceMarkType("Mark " + (i % 3));
            party.setOpeningBalance(1000.0 + i);
            party.setBalanceMarkAmount(500.0 + i);
            party.setPartyDate(LocalDate.now());
            party.setAddress("Address " + i);
            party.setDistrict("District " + (i % 10));
            party.setCodeNo("C" + i);
            party.setDivision("Division " + (i % 4));
            party.setGstNumber("GST" + i);
            party.setStateCode("State " + (i % 5));

            parties.add(party);
        }

        partyRepository.saveAll(parties);
        System.out.println("2000 parties added successfully.");
    }

    @Override
    public void deleteTestParty() {
        List<Party> allParties = partyRepository.findAll();

        if (allParties.size() > 15) {
            List<Party> toDelete = allParties.subList(15, allParties.size());
            partyRepository.deleteAll(toDelete);
            System.out.println("Deleted " + toDelete.size() + " parties, skipping first 15.");
        } else {
            System.out.println("Less than 15 parties exist, no deletion performed.");
        }
    }

}
