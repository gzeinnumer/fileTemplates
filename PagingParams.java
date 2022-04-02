package ${PACKAGE_NAME};

public class PagingParams {
    private int totalPage;
    private int currentPage = 1;
    private int totalData = 0;
    private int perPage = 10;

    public PagingParams() {
    }

    public void setTotalData(int totalData) {
        this.totalPage = (int) Math.ceil((double) totalData/perPage);
        this.totalData = totalData;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void addCurrentPage() {
        currentPage++;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    @Override
    public String toString() {
        return "PagingParams{" +
                "totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", totalData=" + totalData +
                ", perPage=" + perPage +
                '}';
    }
}
