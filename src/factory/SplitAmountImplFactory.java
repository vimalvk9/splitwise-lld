package factory;

import enums.SplitType;
import strategy.EqualSplitAmountImpl;
import strategy.ISplitAmount;

public class SplitAmountImplFactory {

    public static ISplitAmount getSplitAmountImplementation(SplitType splitType) {

        if (splitType == SplitType.EQUAL) {
            return new EqualSplitAmountImpl();
        }
        else if (splitType == SplitType.UNEQUAL) {
            // todo
            throw new IllegalArgumentException( splitType + " yet to implement !");
        }
        else if (splitType == SplitType.PERCENTAGE) {
            // todo
            throw new IllegalArgumentException( splitType + " yet to implement !");
        }
        else {
            throw new IllegalArgumentException("No split amount strategy found against splitType");
        }
    }
}
