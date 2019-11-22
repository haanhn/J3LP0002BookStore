/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haanh.book;

import java.io.Serializable;

/**
 *
 * @author HaAnh
 */
public class BookError implements Serializable {
    
    private String idErr;
    private String titleErr;
    private String descriptionErr;
    private String quantityErr;
    private String priceErr;
    private String imageErr;
    private String dateImportedErr;

    public BookError() {
    }

    public String getIdErr() {
        return idErr;
    }

    public void setIdErr(String idErr) {
        this.idErr = idErr;
    }

    public String getTitleErr() {
        return titleErr;
    }

    public void setTitleErr(String titleErr) {
        this.titleErr = titleErr;
    }

    public String getDescriptionErr() {
        return descriptionErr;
    }

    public void setDescriptionErr(String descriptionErr) {
        this.descriptionErr = descriptionErr;
    }

    public String getQuantityErr() {
        return quantityErr;
    }

    public void setQuantityErr(String quantityErr) {
        this.quantityErr = quantityErr;
    }

    public String getPriceErr() {
        return priceErr;
    }

    public void setPriceErr(String priceErr) {
        this.priceErr = priceErr;
    }

    public String getImageErr() {
        return imageErr;
    }

    public void setImageErr(String imageErr) {
        this.imageErr = imageErr;
    }

    public String getDateImportedErr() {
        return dateImportedErr;
    }

    public void setDateImportedErr(String dateImportedErr) {
        this.dateImportedErr = dateImportedErr;
    }
}
