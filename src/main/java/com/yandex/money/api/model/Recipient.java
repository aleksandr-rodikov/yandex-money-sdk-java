/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 NBCO Yandex.Money LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.yandex.money.api.model;

import static com.yandex.money.api.utils.Common.checkNotEmpty;

/**
 * Recipient's data: shop, showcase or account's number.
 *
 * @author Slava Yasevich
 */
public class Recipient {

    /**
     * Shop's identifier. Set for payments to a shop.
     */
    public final Long shopId;
    /**
     * Article's identifier within a shop. Can be set for payments to the shop. Not required for shops with a single
     * article.
     */
    public final Long shopArticleId;
    /**
     * Showcase's identifier. Set for payments using showcase.
     */
    public final String patternId;
    /**
     * Account's identifier of a recipient. Can be account number, phone number or email. Set for P2P transfers.
     */
    public final String account;

    private Recipient(Long shopId, Long shopArticleId, String patternId, String account) {
        this.shopId = shopId;
        this.shopArticleId = shopArticleId;
        this.patternId = patternId;
        this.account = account;
    }

    /**
     * @see #from(long, long)
     */
    public static Recipient from(long shopId) {
        return new Recipient(shopId, null, null, null);
    }

    /**
     * Payment directly to a specific shop.
     *
     * @param shopId           shop's identifier
     * @param shopArticleId    article's identifier
     * @return a recipient
     */
    public static Recipient from(long shopId, long shopArticleId) {
        return new Recipient(shopId, shopArticleId, null, null);
    }

    /**
     * Payment to a showcase. Recipient will be determined according to showcase's parameters.
     *
     * @param patternId    showcase's identifier
     * @return a recipient
     */
    public static Recipient fromPatternId(String patternId) {
        return new Recipient(null, null, checkNotEmpty(patternId, "patternId"), null);
    }

    /**
     * P2P transfer to Yandex.Money's wallet.
     *
     * @param account    account's identifier of a recipient
     * @return a recipient
     */
    public static Recipient fromAccount(String account) {
        return new Recipient(null, null, null, checkNotEmpty(account, "account"));
    }
}
