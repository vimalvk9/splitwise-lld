package strategy;

import response.PostSplitAmountUserInfo;

import java.util.List;
import java.util.Map;

public interface ISplitAmount {
    PostSplitAmountUserInfo split(Double amount, String paidByUserId, List<String> sharedAmong, Map<String, Double> splitShare);
}
