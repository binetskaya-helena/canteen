package com.example.canteen.service;

import com.example.canteen.service.data.*;

import java.util.Date;
import java.util.Map;

/** The public API of the Canteen service.
 * Making a function call that is not authorized or having not enough priveleges will throw NotAuthrizedException exception.
 */
public interface Facade {
    /** Thrown on an unauthorized action. */
    class NotAuthorizedException extends RuntimeException {}

    /** Thrown on a situation of a positive business error. */
    class DomainError extends RuntimeException {}

    /** Authenticates the given user by checking his password.
     * @param name The name of the user to be authenticated.
     * @param password The user's password.
     * @return The authorization token to be used with all authorized requests.
     * @throws com.example.canteen.service.Facade.NotAuthorizedException on bad name/password.
     */
    AuthToken authenticate(String name, String password);

    static String REGISTER_USER_ACTION = "POST user";
    /** Creates a new user. */
    void registerUser(String name, String password) throws DomainError;

    static String GET_USER_ACTION = "GET user";

    /**
     * @return The profile of the user with the given name.
     */
    User getUser(String name, AuthToken authToken);

    static String GET_CURRENT_MENU_ACTION = "GET menu/published";

    /**
     * @return The current published menu.
     */
    PublishingDetails getCurrentMenu(AuthToken authToken);

    static String SUBMIT_ORDER_ACTION = "POST order";
    /** Creates an order in the system.
     * @return The order object with an ID corresponding to the newly created order.
     */
    Order submitOrder(Order order, AuthToken authToken);

    static String CREATE_MENU_ACTION = "POST menu";
    /** Schedules the given menu to be published.
     * @return The menu object with ID corresponding to the newly created menu object.
     */
    Menu scheduleMenu(Menu menu, AuthToken authToken);

    static String REMOVE_MENU_ACTION = "DELETE menu";
    /** Drops the given menu from the publishing schedule. */
    void cancelMenu(Menu menu, AuthToken authToken);

    static String GET_ORDERED_DISHES_ACTION = "GET orders/aggregated";
    /** Calculates a list of all dishes and quantity ordered in the given period of time. */
    Map<Dish, Integer> getOrderedDishes(Date from, Date to, AuthToken authToken);
}
