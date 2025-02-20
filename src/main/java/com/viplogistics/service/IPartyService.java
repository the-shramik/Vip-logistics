package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Party;
import com.viplogistics.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

public interface IPartyService {

    Party addParty(Party party);

    List<Party> getAllParties();

    Map<String, String> getLatestPartyNo();

    Party updateParty(Party party) throws ResourceNotFoundException;

    ApiResponse<?> deleteParty(String partyNo);

    List<Party> getSearchedParties(String searchData);

    Map<String,Long> getPartyCount();

    void testParty();

    void deleteTestParty();
}
