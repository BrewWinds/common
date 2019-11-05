package mahout;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

/**
 * @Date: 2019/8/12 16:55
 * @Description:
 */
public class MahoutDemo {

    public static void main(String[] args) {

        double[] vals = {12.4,  55.4,66.7,9.44,7.55,10.5};

        Variance variance = new Variance();
        StandardDeviation stdDev = new StandardDeviation();
        System.out.println(stdDev.evaluate(vals));

        System.out.println(variance.evaluate(vals));
    }
}
