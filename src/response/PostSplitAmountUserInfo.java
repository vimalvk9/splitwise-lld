package response;

import enums.SplitType;

import java.util.Map;

public class PostSplitAmountUserInfo {


    private SplitType splitType;
    private Map<String, Double> postSplitAmountShare;

    public PostSplitAmountUserInfo(SplitType splitType, Map<String, Double> postSplitAmountShare) {
        this.splitType = splitType;
        this.postSplitAmountShare = postSplitAmountShare;
    }

    public SplitType getSplitType() {
        return splitType;
    }

    public Map<String, Double> getPostSplitAmountShare() {
        return postSplitAmountShare;
    }
}
