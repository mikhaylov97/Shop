package com.tsystems.shop.model.enums;

/**
 * Class stores order state. It influences on all order mechanism
 */
public enum OrderStatusEnum {
    /**
     * With this state order is awaiting payment.
     */
    AWAITING_PAYMENT ("Awaiting payment"),
    /**
     * With this state order is awaiting shipment
     */
    AWAITING_SHIPMENT ("Awaiting shipment"),
    /**
     * With this state order is awaiting delivering
     */
    SHIPPED ("Shipped"),
    /**
     * With this state order is done if it is awaiting payment. If no, it becomes done.
     */
    DELIVERED ("Delivered"),
    /**
     * With this state order moves to the archive.
     */
    DONE ("Done");

    /**
     * Enum name
     */
    private final String name;

    /**
     * Simple constructor to set name of the ENUM.
     * @param name to set
     */
    OrderStatusEnum(String name) {
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
