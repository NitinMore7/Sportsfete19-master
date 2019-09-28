package org.spider.sportsfete;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.spider.sportsfete.predictr.dept1;
import org.spider.sportsfete.predictr.dept2;

public class predictresponse {

    @SerializedName("dept1")
    @Expose
    private dept1 dept_1;
    @SerializedName("dept2")
    @Expose
    private dept2 dept_2;
    @SerializedName("success")
    @Expose
    private org.spider.sportsfete.predictr.success success;

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

    public org.spider.sportsfete.predictr.success getSuccess() {
        return success;
    }

    public void setSuccess(org.spider.sportsfete.predictr.success success) {
        this.success = success;
    }

}