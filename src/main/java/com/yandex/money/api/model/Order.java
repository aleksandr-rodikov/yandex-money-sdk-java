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

import com.yandex.money.api.utils.Currency;
import com.yandex.money.api.utils.Enums;

import java.math.BigDecimal;
import java.util.Map;

import static com.yandex.money.api.utils.Common.checkNotNull;
import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;

/**
 * Order's parameters. Anything that is required by a shop.
 *
 * @author Slava Yasevich
 */
public class Order {

    /**
     * Unique identifier of an order on a merchant's side. Can be {@code null}.
     *
     * If set then Yandex.Money will control state of previous payments with this {@code clientOrderId}.
     */
    public final String clientOrderId;
    /**
     * Payer's identifier in a shop's system (eg login or any other identifier of payer's account).
     *
     * If set then it will be passed through whole chain of payment process, including daily register of payment's
     * reconcilement.
     */
    public final String customerId;
    /**
     * Payment's amount (commission is not included). Can be {@code null} (there can be articles with price set by a
     * shop).
     */
    public final BigDecimal amount;
    /**
     * {@link #amount}'s currency. Can be {@code null} (there can be articles with price set by a shop).
     */
    public final Currency currency;
    /**
     * Additional order's parameters that will be handled by a shop. Can contain any key-value data according to the
     * shop's specification. Can be empty.
     */
    public final Map<String, String> parameters;

    public Order(Builder builder) {
        clientOrderId = builder.clientOrderId;
        customerId = builder.customerId;
        amount = builder.amount;
        currency = builder.currency;
        parameters = unmodifiableMap(builder.parameters);
    }

    /**
     * Status of order.
     */
    public enum Status implements Enums.WithCode<Status> {

        /**
         * Order is created. Awaiting confirmation from shop.
         */
        CREATED("Created"),
        /**
         * Order is confirmed. Awaiting payment from {@link Payer}.
         */
        APPROVED("Approved"),
        /**
         * Order was rejected by a shop or Yandex.Money. Parameters are invalid or payment authorization rejected.
         */
        REFUSED("Refused"),
        /**
         * Payer confirmed payment order. Payment is in process:
         * <ul>
         *     <li>processing by Yandex.Money</li>
         *     <li>awaiting process completion in external systems</li>
         *     <li>awaiting {@link Payer}'s action, eg confirmation SMS</li>
         * </ul>
         */
        PROCESSING("Processing"),
        /**
         * Order was payed.
         */
        AUTHORIZED("Authorized"),
        /**
         * Payment's notification is delivered successfully to a shop. Article is shipped to a {@link Payer}.
         */
        DELIVERED("Delivered"),
        /**
         * Payment's notification was not delivered to a shop. Article was not shipped to a customer. Automatic refund was
         * performed.
         */
        CANCELED("Canceled"),
        /**
         * Transaction's clearing was performed for a bank card.
         */
        CLEARED("Cleared"),
        /**
         * Payment was refunded to a {@link Payer} for an earlier payed order on the initiative of a shop.
         */
        REFUNDED("Refunded");

        /**
         * Code of a status.
         */
        public final String code;

        Status(String code) {
            this.code = code;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public Status[] getValues() {
            return values();
        }
    }

    public static class Builder {

        private String clientOrderId;
        private String customerId;
        private BigDecimal amount;
        private Currency currency;
        private Map<String, String> parameters = emptyMap();

        public Builder setClientOrderId(String clientOrderId) {
            this.clientOrderId = clientOrderId;
            return this;
        }

        public Builder setCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder setCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Builder setParameters(Map<String, String> parameters) {
            this.parameters = checkNotNull(parameters, "parameters");
            return this;
        }

        public Order create() {
            return new Order(this);
        }
    }
}
