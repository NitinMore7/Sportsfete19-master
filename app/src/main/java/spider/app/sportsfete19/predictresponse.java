package spider.app.sportsfete19;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import spider.app.sportsfete19.predictr.dept1;
import spider.app.sportsfete19.predictr.dept2;
import spider.app.sportsfete19.predictr.success;

public class predictresponse {

    @SerializedName("dept1")
    @Expose
    private dept1 dept_1;
    @SerializedName("dept2")
    @Expose
    private dept2 dept_2;
    @SerializedName("success")
    @Expose
    private success success;

    public dept1 getDept1() {
        return dept_1;
    }

    public void setDept1(dept1 dept_1) {
        this.dept_1 = dept_1;
    }

    public dept2 getDept2() {
        return dept_2;
    }

    public void setDept2(dept2 dept_2) {
        this.dept_2 = dept_2;
    }

    public success getSuccess() {
        return success;
    }

    public void setSuccess(success success) {
        this.success = success;
    }

}