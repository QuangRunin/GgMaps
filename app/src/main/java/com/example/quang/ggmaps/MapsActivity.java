package com.example.quang.ggmaps;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    Marker marker_1;
    private DatabaseManager database;
    private Button button;
    private List<Maps>mapsList ;
    private MapsDao mapsDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        button = findViewById(R.id.btn);
        mapsList = new ArrayList<>();
        database = new DatabaseManager(this);
        mapsDao = new MapsDao(database);
        mapsList =  mapsDao.getAllMasps();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final LatLng latLng = new LatLng(99,99);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in FPT Poly"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        for(Maps mapss:mapsList){
//            double la = Double.parseDouble(mapss.getKinhdo());
//            double tu = Double.parseDouble(mapss.getVido());
            final LatLng sydney = new LatLng(mapss.getKinhdo(), mapss.getVido());
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in FPT Poly"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if (marker.equals(marker_1)) {

                        return true;
                    }
                    return false;
                }
            });
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(final Marker marker) {
                        final Dialog dialog=new Dialog(MapsActivity.this);
                        dialog.setContentView(R.layout.dialog);
                        dialog.findViewById(R.id.sua).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Maps map = mapsDao.getViTriByMaps(String.valueOf(marker.getTitle()));
                                edit(map);

                            }
                        });
                        dialog.show();
                        return true;
                    }

                });

    }
public void edit(final Maps map){
    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MapsActivity.this);
    builder.setTitle("Sửa !");
    LayoutInflater inflater = (LayoutInflater) builder.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    final View viewDialog = inflater.inflate(R.layout.dialog_editmap, null);
    final EditText edtedkinhdo = viewDialog.findViewById(R.id.edteditkd);
    final EditText edtedvido = viewDialog.findViewById(R.id.edteditvd);
    builder.setView(viewDialog);
        edtedkinhdo.setText(String.valueOf(map.getKinhdo()));
        edtedvido.setText(String.valueOf(map.getVido()));
    builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            String edkinhdo = edtedkinhdo.getText().toString();
            String edvido = edtedvido.getText().toString();
//                            maps.setKinhdo(edkinhdo);
//                            maps.setVido(edvido);
                            mapsDao.updateMaps(map);

        }
    });
    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    });
    builder.setCancelable(true);
    builder.show();
}
    public void add(){
              final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MapsActivity.this);
                        builder.setTitle("Lưu kết quả !");
                        LayoutInflater inflater = (LayoutInflater) builder.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View viewDialog = inflater.inflate(R.layout.dialog_maps, null);
                        builder.setView(viewDialog);
                        final EditText edtkinhdo = viewDialog.findViewById(R.id.edtkinhdo);
                        final EditText edtvido = viewDialog.findViewById(R.id.edtvido);
                        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String kinhdo = edtkinhdo.getText().toString();
                                String vido = edtvido.getText().toString();
                                if (kinhdo.equals("") || vido.equals("")) {
                                    Toast.makeText(MapsActivity.this, "Nhap du!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Maps mapscheck = mapsDao.getMapsId(kinhdo);
                                    if (mapscheck==null){
                                        Maps maps = new Maps("",Long.parseLong(kinhdo),Long.parseLong(vido));
                                        mapsDao.insertMaps(maps);
                                        Toast.makeText(MapsActivity.this, "Theem Thanh Cong", Toast.LENGTH_SHORT).show();
                                        mapsList.add(maps);
                                        for(Maps mapss:mapsList){
//            double la = Double.parseDouble(mapss.getKinhdo());
//            double tu = Double.parseDouble(mapss.getVido());
                                            final LatLng sydney = new LatLng(mapss.getKinhdo(), mapss.getVido());
                                            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in FPT Poly"));
                                            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                                        }
                                    }
                                    else {
                                        Toast.makeText(MapsActivity.this, "That Bai !", Toast.LENGTH_SHORT).show();
                                    }
                                    dialogInterface.dismiss();
                                }
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.setCancelable(true);
                        builder.show();
    }

}
