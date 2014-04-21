package com.example.canteen.service.data;

public class PublishingDetails {
    public final Menu menu;
    public final boolean isAvailable;
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
