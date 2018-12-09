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
    private List<Maps>mapsList;
    private MapsDao mapsDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        button = findViewById(R.id.btn);
        mapsList = new ArrayList<>();
        database = new DatabaseManager(this);
        mapsDao = new MapsDao(database);
        for (int i = 0;i<20;i++){
            Maps map =  new Maps("22","33");
            mapsDao.insertMaps(map);
            mapsList.add(map);
        }
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
        final  LatLng a = new LatLng(22,22);
        mMap.addMarker(new MarkerOptions().position(a).title("DeMo"));
        for (int d = 0; d < mapsList.size(); d++) {
            double la = Double.parseDouble(mapsList.get(d).getKinhdo());
            double tu = Double.parseDouble(mapsList.get(d).getVido());
            final LatLng sydney = new LatLng(la, tu);
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
//                        dialog.findViewById(R.id.them).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.findViewById(R.id.layout).setVisibility(View.VISIBLE);
//                                dialog.findViewById(R.id.layout1).setVisibility(View.GONE);
//                                dialog.findViewById(R.id.thaydoi).setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        EditText editText= dialog.findViewById(R.id.latitude);
//                                        EditText editText1= dialog.findViewById(R.id.longitude);
//                                        String latitude=editText.getText().toString();
//                                        String longitude=editText1.getText().toString();
//                                        if (!latitude.isEmpty() && !longitude.isEmpty()) {
//                                            Maps maps = new Maps(latitude, longitude);
//                                            mapsDao.insertMaps(maps);
//                                            Toast.makeText(MapsActivity.this, "Theem Thanh Cong", Toast.LENGTH_SHORT).show();
//                                            mapsList.add(maps);
//                                            dialog.dismiss();
//                                            for (int d = 0; d < mapsList.size(); d++) {
//                                                double la = Double.parseDouble(mapsList.get(d).getKinhdo());
//                                                double tu = Double.parseDouble(mapsList.get(d).getVido());
//                                                final LatLng sydney = new LatLng(la, tu);
//                                                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in FPT Poly"));
//                                                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//                                            }
//                                        }
//
//                                    }
//                                });
//                            }
//                        });
                        dialog.findViewById(R.id.sua).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final EditText editText= dialog.findViewById(R.id.latitude);
                                final EditText editText1= dialog.findViewById(R.id.longitude);
                                dialog.findViewById(R.id.layout).setVisibility(View.VISIBLE);
                                dialog.findViewById(R.id.layout1).setVisibility(View.GONE);
                                final LatLng latLng = marker.getPosition();
                                    editText.setText(latLng.latitude+"");
                                    editText1.setText(latLng.longitude+"");
                                dialog.findViewById(R.id.thaydoi).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String latitude = editText.getText().toString().trim();
                                        String longitude = editText1.getText().toString().trim();
                                        if (latitude.equals("") || longitude.equals("")) {
                                            Toast.makeText(MapsActivity.this, "Nhap Day du?", Toast.LENGTH_SHORT).show();
                                        } else {
                                            for (int i = 0; i < mapsList.size(); i++) {
                                                Maps maps = mapsList.get(i);
                                                maps.setKinhdo(latitude);
                                                maps.setVido(longitude);
                                                mapsDao.updateMaps(maps);
                                                mapsList.set(i,maps);
                                                mapsList.add(i,maps);
                                                Toast.makeText(MapsActivity.this, "Doi Thanh Cong", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }

                                        }
                                    }
                                });
                            }
                        });
                        dialog.show();
                        return true;
                    }

                });

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
                                        Maps maps = new Maps(kinhdo,vido);
                                        mapsDao.insertMaps(maps);
                                        Toast.makeText(MapsActivity.this, "Theem Thanh Cong", Toast.LENGTH_SHORT).show();
                                        mapsList.add(maps);
                                        for (int d = 0; d < mapsList.size(); d++) {
                                            double la = Double.parseDouble(mapsList.get(d).getKinhdo());
                                            double tu = Double.parseDouble(mapsList.get(d).getVido());
                                            final LatLng sydney = new LatLng(la,tu);
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
