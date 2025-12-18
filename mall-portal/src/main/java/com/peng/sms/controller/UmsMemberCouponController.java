package com.peng.sms.controller;

import com.peng.sms.api.CommonResult;
import com.peng.sms.domain.CartPromotionItem;
import com.peng.sms.domain.SmsCouponHistoryDetail;
import com.peng.sms.model.SmsCoupon;
import com.peng.sms.model.SmsCouponHistory;
import com.peng.sms.service.OmsCartItemService;
import com.peng.sms.service.UmsMemberCouponService;
import com.peng.sms.service.UmsMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Member Coupon Management Controller
 * Created by macro on 2018/8/29.
 */
@Controller
@Tag(name = "UmsMemberCouponController", description = "User Coupon Management")
@RequestMapping("/member/coupon")
public class UmsMemberCouponController {

    @Autowired
    private UmsMemberCouponService memberCouponService;

    @Autowired
    private OmsCartItemService cartItemService;

    @Autowired
    private UmsMemberService memberService;

    @Operation(summary = "Claim a specified coupon")
    @RequestMapping(value = "/add/{couponId}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult add(@PathVariable Long couponId) {
        memberCouponService.add(couponId);
        return CommonResult.success(null, "Successfully claimed");
    }

    @Operation(summary = "Get member coupon history list")
    @Parameter(
            name = "useStatus",
            description = "Coupon filter type: 0 -> unused; 1 -> used; 2 -> expired",
            in = ParameterIn.QUERY,
            schema = @Schema(type = "integer", allowableValues = {"0", "1", "2"})
    )
    @RequestMapping(value = "/listHistory", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<SmsCouponHistory>> listHistory(
            @RequestParam(value = "useStatus", required = false) Integer useStatus) {

        List<SmsCouponHistory> couponHistoryList =
                memberCouponService.listHistory(useStatus);
        return CommonResult.success(couponHistoryList);
    }

    @Operation(summary = "Get member coupon list")
    @Parameter(
            name = "useStatus",
            description = "Coupon filter type: 0 -> unused; 1 -> used; 2 -> expired",
            in = ParameterIn.QUERY,
            schema = @Schema(type = "integer", allowableValues = {"0", "1", "2"})
    )
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<SmsCoupon>> list(
            @RequestParam(value = "useStatus", required = false) Integer useStatus) {

        List<SmsCoupon> couponList = memberCouponService.list(useStatus);
        return CommonResult.success(couponList);
    }

    @Operation(summary = "Get coupons related to the logged-in member's shopping cart")
    @Parameter(
            name = "type",
            description = "Availability type: 0 -> unavailable; 1 -> available",
            in = ParameterIn.PATH,
            schema = @Schema(
                    type = "integer",
                    defaultValue = "1",
                    allowableValues = {"0", "1"}
            )
    )
    @RequestMapping(value = "/list/cart/{type}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<SmsCouponHistoryDetail>> listCart(
            @PathVariable Integer type) {

        List<CartPromotionItem> cartPromotionItemList =
                cartItemService.listPromotion(
                        memberService.getCurrentMember().getId(),
                        null
                );

        List<SmsCouponHistoryDetail> couponHistoryList =
                memberCouponService.listCart(cartPromotionItemList, type);

        return CommonResult.success(couponHistoryList);
    }

    @Operation(summary = "Get coupons related to the current product")
    @RequestMapping(value = "/listByProduct/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<SmsCoupon>> listByProduct(
            @PathVariable Long productId) {

        List<SmsCoupon> couponHistoryList =
                memberCouponService.listByProduct(productId);

        return CommonResult.success(couponHistoryList);
    }
}