package spider.app.sportsfete19.API;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AVINASH on 3/1/2018.
 */

public class Leaderboard implements Parcelable {

    @DatabaseField(generatedId = true)
    long localId;
    @DatabaseField
    @SerializedName("dept")
    @Expose
    private String dept;
    @DatabaseField
    @SerializedName("total")
    @Expose
    private float total;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    @SerializedName("splitup")
    @Expose
    private ArrayList<String> splitup;

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public List<String> getSplitup() {
        return splitup;
    }

    public void setSplitup(ArrayList<String> splitup) {
        this.splitup = splitup;
    }


    protected Leaderboard(Parcel in) {
        localId = in.readLong();
        dept = in.readString();
        total = in.readFloat();
        splitup = in.createStringArrayList();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(localId);
        parcel.writeString(dept);
        parcel.writeFloat(total);
        parcel.writeList(splitup);
    }

    public static final Creator<Leaderboard> CREATOR = new Creator<Leaderboard>() {
        @Override
        public Leaderboard createFromParcel(Parcel in) {
            return new Leaderboard(in);
        }

        @Override
        public Leaderboard[] newArray(int size) {
            return new Leaderboard[size];
        }
    };
}
