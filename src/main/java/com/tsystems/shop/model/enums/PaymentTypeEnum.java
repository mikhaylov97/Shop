package com.tsystems.shop.model.enums;

/**
 * Class stores payment type state. It influences on all order mechanism
 */
public enum PaymentTypeEnum {
    /**
     * With this state order payment type is cash after delivering product.
     */
    CASH("Cash"),
    /**
     * With this state order payment type is credit card and payment status becomes Paid by default.
     */
    CREDIT("Credit card");

    /**
     * ENUM name
     */
    private final String name;

    /**
     * Simple constructor to set name of the ENUM
     * @param name to set
     */
    PaymentTypeEnum(String name) {
        this.name = name;
    }


    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    @Override
    public String toString() {
        return name;
    }
}

