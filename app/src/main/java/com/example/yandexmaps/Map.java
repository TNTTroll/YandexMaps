package com.example.yandexmaps;

import static com.example.yandexmaps.MainActivity.objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.mapkit.directions.DirectionsFactory;
import com.yandex.mapkit.directions.driving.DrivingOptions;
import com.yandex.mapkit.directions.driving.DrivingRoute;
import com.yandex.mapkit.directions.driving.DrivingRouter;
import com.yandex.mapkit.directions.driving.DrivingRouterType;
import com.yandex.mapkit.directions.driving.DrivingSession;
import com.yandex.mapkit.directions.driving.VehicleOptions;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.geometry.Point;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;

import java.util.ArrayList;
import java.util.List;

public class Map extends AppCompatActivity implements DrivingSession.DrivingRouteListener {

    MapView mapView;

    private MapObjectCollection mapObjects;
    DrivingRouter drivingRouter;

    public static ArrayList<Point> points = new ArrayList<>();
    public static boolean separate = true;

    ArrayList<Point> draw = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.mapView);

        draw.addAll(points);
        points.clear();

        mapView.getMap().move(
                new CameraPosition(new Point(55.753544,37.621202), 10f, .0f, .0f),
                new Animation(Animation.Type.SMOOTH, 2), null );


        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter(DrivingRouterType.COMBINED);
        mapObjects = mapView.getMapWindow().getMap().getMapObjects().addCollection();
        drawPoints();

        Button back = findViewById(R.id.mapBack);
        back.setOnClickListener(v -> startActivity( new Intent(this, App.class) ) );

        MainActivity.inputListener = new InputListener() {
            @Override
            public void onMapTap(@NonNull com.yandex.mapkit.map.Map map, @NonNull Point point) {}

            @Override
            public void onMapLongTap(@NonNull com.yandex.mapkit.map.Map map, @NonNull Point point) {
                draw.add(point);
                drawPoints();
            }
        };
        mapView.getMap().addInputListener(MainActivity.inputListener);

    }

    @Override
    public void onStart() {  // Функция вызывается при открытие карты на экране
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {  // Функция вызывается при закрытие карты на экране
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    public void drawPoints() {
        // Удалить все старые точки
        for (MapObject object : objects)
            object.getParent().remove(object);
        objects.clear();

        // Показать точки на карте
        for (Point point : draw) {
            MapObject object = mapView.getMap().getMapObjects().addPlacemark(point, ImageProvider.fromAsset(this, "point.png"));
            objects.add(object);
        }

        // Создать маршрут между всеми существующими точками
        if (draw.size() > 1) {
            List<RequestPoint> requestPoints = new ArrayList<>();
            for (Point point : draw) {
                RequestPoint request = new RequestPoint(point, RequestPointType.WAYPOINT, null, null);
                requestPoints.add(request);
            }
            DrivingSession drivingSession = drivingRouter.requestRoutes(requestPoints, new DrivingOptions(null, 1, null, null, null, null, null), new VehicleOptions(), this);
        }
    }

    @Override
    public void onDrivingRoutes(@NonNull List<DrivingRoute> list) {
        for (DrivingRoute route : list)
            mapObjects.addPolyline(route.getGeometry());
    }

    @Override
    public void onDrivingRoutesError(@NonNull Error error) {}
}