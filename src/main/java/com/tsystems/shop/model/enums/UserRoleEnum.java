package com.tsystems.shop.model.enums;

/**
 * Class stores user roles. It influences on all security mechanism.
 * By default all quests have ANONYMOUS role. They can:
 * 1)See title page.
 * 2)Browse catalogs.
 * 3)Use bag.
 * When user have entered his correct credentials or sign up, he gets USER role.
 * All users have rights to all default actions(ANONYMOUS). In addition they can:
 * 1)Visit account page.
 * 2)Manage account info.
 * 3)Logout.
 * 4)Checkout orders.
 * When user have entered his correct credentials with administrative right, he gets ADMIN role.
 * These users(admins) have rights to all default actions(ANONYMOUS) and User actions except
 * using bag and ordering. Instead of this, can:
 * 1)Manage categories.
 * 2)Check shop statistics.
 * 3)Add new product.
 * 4)Edit any product.
 * 5)Make product hidden or active.
 * 6)Manage orders (they can change order state/status).
 * When user have entered his correct credentials with super-administrative rights, he gets SUPER_ADMIN role.
 * Super-admin have ADMIN right with one feature - managing admins list. So, he can add new admins or delete them.
 */
public enum UserRoleEnum {
    /**
     * With this state every guest gets User authority.
     */
    ROLE_USER,
    /**
     * With this state every guest gets Admin authority.
     */
    ROLE_ADMIN,
    /**
     * With this state every quest gets Super-admin authority.
     */
    ROLE_SUPER_ADMIN
}
