package ru.yandex.praktikum.api_yandex_scooter.model.order;

import java.util.List;

public class ListOrders {

    private int courierId;
    private List<String> nearestStation;
    private int limit;
    private int page;

    public ListOrders(int courierId, List<String> nearestStation, int limit, int page) {
        this.courierId = courierId;
        this.nearestStation = nearestStation;
        this.limit = limit;
        this.page = page;
    }


    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }

    public List<String> getNearestStation() {
        return nearestStation;
    }

    public void setNearestStation(List<String> nearestStation) {
        this.nearestStation = nearestStation;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
