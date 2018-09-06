package com.example.raindi.pumpercontrol.data;

import android.app.Application;

import com.example.raindi.pumpercontrol.entities.PumperDisplayEntity;

/**
 * Created by RD007 on 2018/9/5.
 */

public class InfoEntityData extends Application{
    private PumperDisplayEntity infoEntity;

    public PumperDisplayEntity getInfoEntity() {
        return infoEntity;
    }

    public void setInfoEntity(PumperDisplayEntity infoEntity) {
        this.infoEntity = infoEntity;
    }
}
