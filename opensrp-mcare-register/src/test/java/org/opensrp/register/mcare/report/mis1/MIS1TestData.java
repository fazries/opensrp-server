package org.opensrp.register.mcare.report.mis1;


import org.opensrp.register.mcare.domain.Members;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class MIS1TestData {
    public int totalCount;
    public int validCount;
    public long startDateTime;
    public long endDateTime;

    public MIS1TestData(int totalCount, int validCount, long startDateTime, long endDateTime) {
        this.totalCount = totalCount;
        this.validCount = validCount;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public long getRandomNumberBetween(long start, long end) {
        return ThreadLocalRandom.current().nextLong(start, end);
    }

    protected Members createMemberWithEligibleCoupleFollowUpList(List<Map<String, String>> followUpList) {
        Members member = new Members();
        member.setelco_Followup(followUpList);
        return member;
    }

    protected Members createMemberWithClientVersion(long clientVersion) {
        Members member = new Members();
        member.setClientVersion(clientVersion);
        return member;
    }


}
