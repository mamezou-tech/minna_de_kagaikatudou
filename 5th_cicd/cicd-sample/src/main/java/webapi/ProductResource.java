package webapi;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductResource implements ProcutEndPointSpec {

    private static final int TYPE_NOMAL = 1;
    private static final int TYPE_SILVER = 2;
    private static final int TYPE_GOLD = 3;

    private static final Map<Integer, Integer> TYPE_DISCOUNT_RATE_MAP = Map.of(
                TYPE_NOMAL, 10,
                TYPE_SILVER, 20,
                TYPE_GOLD, 30
            );

    @Override
    public List<Integer> calculatePrice(String memberNo, int price) {
        int memberType;
        switch (memberNo) {
            case "A0001":
                memberType = TYPE_NOMAL;
                break;
            case "A0002":
                memberType = TYPE_SILVER;
                break;
            case "A0003":
                memberType = TYPE_GOLD;
                break;
            default:
                memberType = TYPE_NOMAL;
        }

        // 意図的にstopBugsの警告を出す
//        if (memberNo == "A0001") {
//            return null;
//        }

        int discountRate = TYPE_DISCOUNT_RATE_MAP.get(memberType);
        int discountPrice = (int) (price * ((100.0 - discountRate) /100));
        return List.of(
                    discountPrice,
                    memberType,
                    discountRate
                );
    }
}
