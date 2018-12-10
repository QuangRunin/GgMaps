package com.example.quang.ggmaps;

public class Maps {
    private String _id;
    private long kinhdo;
    private long vido;

    public Maps(String _id, long kinhdo, long vido) {
        this._id = _id;
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

    public long getKinhdo() {
        return kinhdo;
    }

    public void setKinhdo(long kinhdo) {
        this.kinhdo = kinhdo;
    }

    public long getVido() {
        return vido;
    }

    public void setVido(long vido) {
        this.vido = vido;
    }
}
