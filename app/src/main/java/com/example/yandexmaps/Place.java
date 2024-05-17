package com.example.yandexmaps;

import com.yandex.mapkit.geometry.Point;

public class Place {
    String name;
    String address;
    Point point;

    Place (String _name, String _address, Point _point) {
        name = _name;
        address = _address;
        point = _point;
    }

    public String getName() { return name; }

    public String getAddress() { return address; }

    public Point getPoint() { return point; }
}
