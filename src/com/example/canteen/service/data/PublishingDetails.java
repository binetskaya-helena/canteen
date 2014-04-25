package com.example.canteen.service.data;

/** Contains information about menu that is published as the current menu. */
public class PublishingDetails {
    /** The menu or null if no menu was published. */
    public final Menu menu;

    /** Does the menu is published to the clients. */
    public final boolean isAvailable;

    /** Do the clients can make orders for dishes in this menu. */
    public final boolean orderingEnabled;

    public PublishingDetails(Menu menu, boolean isAvailable, boolean orderingEnabled) {
        this.menu = menu;
        this.isAvailable = isAvailable;
        this.orderingEnabled = orderingEnabled;
    }

    @Override
    public boolean equals(Object o) {
        PublishingDetails other = (PublishingDetails)o;
        return (other.menu == this.menu && other.isAvailable == this.isAvailable && other.orderingEnabled == this.orderingEnabled);
    }
}
