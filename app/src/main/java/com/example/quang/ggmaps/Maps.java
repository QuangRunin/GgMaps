package com.example.quang.ggmaps;

public class Maps {
    private String _id;
    private String kinhdo;
    private String vido;

    public Maps( String kinhdo, String vido) {
        this.kinhdo = kinhdo;
        this.vido = vido;
    }

    public Maps() {

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getKinhdo() {
        return kinhdo;
    }

    public void setKinhdo(String kinhdo) {
        this.kinhdo = kinhdo;
    }

    public String getVido() {
        return vido;
    }

    public void setVido(String vido) {
        this.vido = vido;
    }
}
