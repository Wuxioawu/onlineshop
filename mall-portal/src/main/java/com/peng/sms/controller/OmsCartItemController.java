package com.peng.sms.controller;

import com.peng.sms.api.CommonResult;
import com.peng.sms.domain.CartProduct;
import com.peng.sms.domain.CartPromotionItem;
import com.peng.sms.model.OmsCartItem;
import com.peng.sms.service.OmsCartItemService;
import com.peng.sms.service.UmsMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Shopping Cart Management Controller
 * Created by macro on 2018/8/2.
 */
@Controller
@Tag(name = "OmsCartItemController", description = "Shopping Cart Management")
@RequestMapping("/cart")
public class OmsCartItemController {

    @Autowired
    private OmsCartItemService cartItemService;

    @Autowired
    private UmsMemberService memberService;

    @Operation(summary = "Add a product to the shopping cart")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult add(@RequestBody OmsCartItem cartItem) {
        int count = cartItemService.add(cartItem);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @Operation(summary = "Get the shopping cart list of the current member")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<OmsCartItem>> list() {
        List<OmsCartItem> cartItemList =
                cartItemService.list(memberService.getCurrentMember().getId());
        return CommonResult.success(cartItemList);
    }

    @Operation(summary = "Get the shopping cart list of the current member, including promotion information")
    @RequestMapping(value = "/list/promotion", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<CartPromotionItem>> listPromotion(
            @RequestParam(required = false) List<Long> cartIds) {

        List<CartPromotionItem> cartPromotionItemList =
                cartItemService.listPromotion(
                        memberService.getCurrentMember().getId(),
                        cartIds
                );
        return CommonResult.success(cartPromotionItemList);
    }

    @Operation(summary = "Update the quantity of a product in the shopping cart")
    @RequestMapping(value = "/update/quantity", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult updateQuantity(
            @RequestParam Long id,
            @RequestParam Integer quantity) {

        int count = cartItemService.updateQuantity(
                id,
                memberService.getCurrentMember().getId(),
                quantity
        );
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @Operation(summary = "Get product specifications from the cart for re-selecting attributes")
    @RequestMapping(value = "/getProduct/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CartProduct> getCartProduct(
            @PathVariable Long productId) {

        CartProduct cartProduct =
                cartItemService.getCartProduct(productId);
        return CommonResult.success(cartProduct);
    }

    @Operation(summary = "Update product attributes in the shopping cart")
    @RequestMapping(value = "/update/attr", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateAttr(@RequestBody OmsCartItem cartItem) {
        int count = cartItemService.updateAttr(cartItem);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @Operation(summary = "Delete a product from the shopping cart")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        int count = cartItemService.delete(
                memberService.getCurrentMember().getId(),
                ids
        );
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @Operation(summary = "Clear the shopping cart")
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult clear() {
        int count =
                cartItemService.clear(memberService.getCurrentMember().getId());
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}