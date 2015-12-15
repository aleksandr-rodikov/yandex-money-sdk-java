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

import com.yandex.money.api.utils.Enums;

import java.util.List;

import static com.yandex.money.api.utils.Common.checkNotNull;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

/**
 * Payment's parameters.
 *
 * @author Slava Yasevich
 */
public class Payment {

    /**
     * @see Recipient
     */
    public final Recipient recipient;
    /**
     * @see Order
     */
    public final Order order;
    /**
     * @see Payer
     */
    public final Payer payer;
    /**
     * Desired payment's schemes to make a payment. Can contain any amount. If list is empty then Yandex.Money will
     * suggest all available payment's schemes for the {@link #order}.
     */
    public final List<Scheme> schemes;

    private Payment(Builder builder) {
        recipient = checkNotNull(builder.recipient, "recipient");
        order = checkNotNull(builder.order, "order");
        payer = builder.payer;
        schemes = unmodifiableList(builder.schemes);
    }

    public static class Builder {

        private Recipient recipient;
        private Order order;
        private Payer payer;
        private List<Scheme> schemes = emptyList();

        public Builder setRecipient(Recipient recipient) {
            this.recipient = recipient;
            return this;
        }

        public Builder setOrder(Order order) {
            this.order = order;
            return this;
        }

        public Builder setPayer(Payer payer) {
            this.payer = payer;
            return this;
        }

        public Builder setSchemes(List<Scheme> schemes) {
            this.schemes = checkNotNull(schemes, "schemes");
            return this;
        }

        public Payment create() {
            return new Payment(this);
        }
    }

    /**
     * Payment's schemes.
     */
    public enum Scheme implements Enums.WithCode<Scheme> {

        /**
         * Any bank card.
         */
        BANKCARDS("BankCards"),
        /**
         * Cash payment via terminal.
         */
        CASH("Cash"),
        /**
         * Sberbank Online with SMS confirmation.
         */
        SBERBANK("Sberbank"),
        /**
         * Payment from Yandex.Money wallet.
         */
        WALLET("Wallet");

        public final String code;

        Scheme(String code) {
            this.code = code;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public Scheme[] getValues() {
            return values();
        }
    }
}
