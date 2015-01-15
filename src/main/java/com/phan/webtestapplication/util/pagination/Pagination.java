package com.phan.webtestapplication.util.pagination;

import com.phan.webtestapplication.Constants;

public class Pagination {

    private Double currentPage;

    private Double linkPages;

    private Double linkRange;

    private Double totalPage;

    public Pagination(Integer currentPage, Long totalItems) {
        this.currentPage = (double) (currentPage == null ? 1 : currentPage);
        this.totalPage = Math.ceil(totalItems / (double) Constants.PAGINATION_MAX_PER_PAGE);
        this.linkPages = (double) (totalPage < Constants.PAGINATION_MAX_DISPLAY_PAGES ? totalPage
                : Constants.PAGINATION_MAX_DISPLAY_PAGES);
        this.linkRange = (double) linkPages / 2;
    }

    public Double getBegin() {
        return (((currentPage - linkRange) > 0 ? ((currentPage - linkRange) < (totalPage - linkPages + 1) ? (currentPage - linkRange)
                : (totalPage - linkPages))
                : 0) + 1);
    }

    public Double getEnd() {
        return (currentPage + linkRange) < totalPage ? ((currentPage + linkRange) > linkPages ? (currentPage + linkRange)
                : linkPages)
                : totalPage;
    }

    public Integer getLast() {
        return totalPage.intValue();
    }

}
