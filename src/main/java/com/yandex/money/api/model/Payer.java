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
 * Anything that is known about a payer.
 *
 * @author Slava Yasevich
 */
public class Payer {

    /**
     * Phone number.
     */
    public final String phone;
    /**
     * Account number.
     */
    public final String accountNumber;

    private Payer(String phone, String accountNumber) {
        this.phone = phone;
        this.accountNumber = accountNumber;
    }

    public static Payer fromPhone(String phone) {
        return new Payer(checkNotEmpty(phone, "phone"), null);
    }

    public static Payer fromAccountNumber(String accountNumber) {
        return new Payer(null, checkNotEmpty(accountNumber, "accountNumber"));
    }
}
