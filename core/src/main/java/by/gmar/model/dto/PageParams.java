package by.gmar.model.dto;

/**
 *
 * @author s.kosik
 */
public class PageParams {
    private int pageNumber;
    private int recordsPerPage;
    private boolean ascDesc;

    public PageParams(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public PageParams() {
    }
    
    public PageParams(int pageNumber, int recordsPerPage, boolean ascDesc) {
        this.pageNumber = pageNumber;
        this.recordsPerPage = recordsPerPage;
        this.ascDesc = ascDesc;
    }
    
    public boolean isAscDesc() {
        return ascDesc;
    }

    public void setAscDesc(boolean ascDesc) {
        this.ascDesc = ascDesc;
    }
    
    public void setRecordsPerPage(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }
    
    public int getRecordsPerPage(){
        return recordsPerPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    
}