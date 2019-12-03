package com.alastor.vehiclereport.repository.roomdatabase.entity;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.alastor.vehiclereport.R;

import static com.alastor.vehiclereport.repository.roomdatabase.entity.Category.CategoryId.BFC;
import static com.alastor.vehiclereport.repository.roomdatabase.entity.Category.CategoryId.BKS;
import static com.alastor.vehiclereport.repository.roomdatabase.entity.Category.CategoryId.CLS;
import static com.alastor.vehiclereport.repository.roomdatabase.entity.Category.CategoryId.CWH;
import static com.alastor.vehiclereport.repository.roomdatabase.entity.Category.CategoryId.DRS;
import static com.alastor.vehiclereport.repository.roomdatabase.entity.Category.CategoryId.EAA;
import static com.alastor.vehiclereport.repository.roomdatabase.entity.Category.CategoryId.EAE;
import static com.alastor.vehiclereport.repository.roomdatabase.entity.Category.CategoryId.ESL;
import static com.alastor.vehiclereport.repository.roomdatabase.entity.Category.CategoryId.EXS;
import static com.alastor.vehiclereport.repository.roomdatabase.entity.Category.CategoryId.FIL;
import static com.alastor.vehiclereport.repository.roomdatabase.entity.Category.CategoryId.FSP;
import static com.alastor.vehiclereport.repository.roomdatabase.entity.Category.CategoryId.HAC;
import static com.alastor.vehiclereport.repository.roomdatabase.entity.Category.CategoryId.IIS;
import static com.alastor.vehiclereport.repository.roomdatabase.entity.Category.CategoryId.ITS;
import static com.alastor.vehiclereport.repository.roomdatabase.entity.Category.CategoryId.SAW;
import static com.alastor.vehiclereport.repository.roomdatabase.entity.Category.CategoryId.STS;

@Entity
public class Category {

    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "execution_timestamp")
    private long executionTimestamp;

    @ColumnInfo(name = "amount_of_elements")
    private long amountOfElements;

    public Category(String id, long executionTimestamp, long amountOfElements) {
        this.id = id;
        this.executionTimestamp = executionTimestamp;
        this.amountOfElements = amountOfElements;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean equal = false;

        if (obj instanceof Category) {
            Category category = (Category) obj;
            equal = category.getId().equals(this.getId());
        }

        return equal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getExecutionTimestamp() {
        return executionTimestamp;
    }

    public void setExecutionTimestamp(long executionTimestamp) {
        this.executionTimestamp = executionTimestamp;
    }

    public long getAmountOfElements() {
        return amountOfElements;
    }

    public void setAmountOfElements(long amountOfElements) {
        this.amountOfElements = amountOfElements;
    }

    public static Category[] populateData() {
        return new Category[]{
                new Category(BFC.name(), 0, 0),
                new Category(BKS.name(), 0, 0),
                new Category(CWH.name(), 0, 0),
                new Category(CLS.name(), 0, 0),
                new Category(DRS.name(), 0, 0),
                new Category(ESL.name(), 0, 0),
                new Category(EAE.name(), 0, 0),
                new Category(EAA.name(), 0, 0),
                new Category(EXS.name(), 0, 0),
                new Category(FIL.name(), 0, 0),
                new Category(FSP.name(), 0, 0),
                new Category(HAC.name(), 0, 0),
                new Category(IIS.name(), 0, 0),
                new Category(ITS.name(), 0, 0),
                new Category(STS.name(), 0, 0),
                new Category(SAW.name(), 0, 0)
        };
    }

    public enum CategoryId {
        BFC,
        BKS,
        CWH,
        CLS,
        DRS,
        ESL,
        EAE,
        EAA,
        EXS,
        FIL,
        FSP,
        HAC,
        IIS,
        ITS,
        STS,
        SAW;

        public String getTranslation(Context context) {
            switch (this) {
                case BFC:
                    return context.getString(R.string.category_bfc);
                case BKS:
                    return context.getString(R.string.category_bks);
                case CWH:
                    return context.getString(R.string.category_cwh);
                case CLS:
                    return context.getString(R.string.category_cls);
                case DRS:
                    return context.getString(R.string.category_drs);
                case ESL:
                    return context.getString(R.string.category_esl);
                case EAE:
                    return context.getString(R.string.category_eae);
                case EAA:
                    return context.getString(R.string.category_eaa);
                case EXS:
                    return context.getString(R.string.category_exs);
                case FIL:
                    return context.getString(R.string.category_fil);
                case FSP:
                    return context.getString(R.string.category_fsp);
                case HAC:
                    return context.getString(R.string.category_hac);
                case IIS:
                    return context.getString(R.string.category_iis);
                case ITS:
                    return context.getString(R.string.category_its);
                case STS:
                    return context.getString(R.string.category_sts);
                case SAW:
                    return context.getString(R.string.category_saw);
            }
            return "";
        }

        public Drawable getCategoryIcon(Context context) {
            switch (this) {
                case BFC:
                    return ContextCompat.getDrawable(context, R.drawable.ic_car_door);
                case BKS:
                    return ContextCompat.getDrawable(context, R.drawable.ic_break);
                case CWH:
                    return ContextCompat.getDrawable(context, R.drawable.ic_windshield);
                case CLS:
                    return ContextCompat.getDrawable(context, R.drawable.ic_radiator);
                case DRS:
                    return ContextCompat.getDrawable(context, R.drawable.ic_gears);
                case ESL:
                    return ContextCompat.getDrawable(context, R.drawable.ic_headlights);
                case EAE:
                    return ContextCompat.getDrawable(context, R.drawable.ic_engine);
                case EAA:
                    return ContextCompat.getDrawable(context, R.drawable.ic_tools);
                case EXS:
                    return ContextCompat.getDrawable(context, R.drawable.ic_exhaust);
                case FIL:
                    return ContextCompat.getDrawable(context, R.drawable.ic_air_filter);
                case FSP:
                    return ContextCompat.getDrawable(context, R.drawable.ic_gas_station);
                case HAC:
                    return ContextCompat.getDrawable(context, R.drawable.ic_air_conditioner);
                case IIS:
                    return ContextCompat.getDrawable(context, R.drawable.ic_spark_plug);
                case ITS:
                    return ContextCompat.getDrawable(context, R.drawable.ic_starter);
                case STS:
                    return ContextCompat.getDrawable(context, R.drawable.ic_steering_wheel);
                case SAW:
                    return ContextCompat.getDrawable(context, R.drawable.ic_damper);
            }
            return ContextCompat.getDrawable(context, R.drawable.ic_engine);
        }
    }
}
