package spider.app.sportsfete19.Sponsors;


public class Sponsor {
    private String company;
    private int resID;

    public Sponsor(String company, int resID){
        this.company=company;
        this.resID=resID;
    }

    public int getResID() {
        return resID;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }
}
