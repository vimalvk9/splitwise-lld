package strategy;

import enums.SplitType;
import response.PostSplitAmountUserInfo;

import java.util.List;
import java.util.Map;

public class EqualSplitAmountImpl implements ISplitAmount {

    @Override
    public PostSplitAmountUserInfo split(Double amount, String paidByUserId, List<String> sharedAmong, Map<String, Double> splitShare) {

        int totalUsersShared = sharedAmong.size();
        if (totalUsersShared == 0) {
            throw new IllegalArgumentException("Users shared should be > 0");
        }

        Double dividedAmount = amount / totalUsersShared;
        for(String currentUserId : sharedAmong) {
            if (!currentUserId.equals(paidByUserId)) {
                splitShare.put(currentUserId, -1 * dividedAmount);
            }
        }
        return new PostSplitAmountUserInfo(SplitType.EQUAL, splitShare);
    }
}
