package com.wh2.foss.people.model.persistance.database.tables.base;

import com.raizlabs.android.dbflow.structure.BaseModel;

public abstract class DefaultBaseModel<R> extends BaseModel {

    public abstract R convertToModel();

}
