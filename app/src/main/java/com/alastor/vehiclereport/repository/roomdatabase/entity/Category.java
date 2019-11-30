package com.alastor.vehiclereport.repository.roomdatabase.entity;

import android.content.Context;

import androidx.annotation.NonNull;
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
                new Category(BFC.name(), System.currentTimeMillis(), 0),
                new Category(BKS.name(), System.currentTimeMillis(), 0),
                new Category(CWH.name(), System.currentTimeMillis(), 0),
                new Category(CLS.name(), System.currentTimeMillis(), 0),
                new Category(DRS.name(), System.currentTimeMillis(), 0),
                new Category(ESL.name(), System.currentTimeMillis(), 0),
                new Category(EAE.name(), System.currentTimeMillis(), 0),
                new Category(EAA.name(), System.currentTimeMillis(), 0),
                new Category(EXS.name(), System.currentTimeMillis(), 0),
                new Category(FIL.name(), System.currentTimeMillis(), 0),
                new Category(FSP.name(), System.currentTimeMillis(), 0),
                new Category(HAC.name(), System.currentTimeMillis(), 0),
                new Category(IIS.name(), System.currentTimeMillis(), 0),
                new Category(ITS.name(), System.currentTimeMillis(), 0),
                new Category(STS.name(), System.currentTimeMillis(), 0),
                new Category(SAW.name(), System.currentTimeMillis(), 0)
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
                    return "BFCC";
                case BKS:
                    return "BKSS";
                case CWH:
                    return "CWHS";
                case CLS:
                    return "CLSS";
                case DRS:
                    return "DRSS";
                case ESL:
                    return "ESLS";
                case EAE:
                    return "EAES";
                case EAA:
                    return "EAAS";
                case EXS:
                    return "EXSS";
                case FIL:
                    return "FILS";
                case FSP:
                    return "FSPS";
                case HAC:
                    return "HACS";
                case IIS:
                    return "IISS";
                case ITS:
                    return "ITSS";
                case STS:
                    return "STSS";
                case SAW:
                    return "SAWS";
            }
            return "";
        }
    }
}
