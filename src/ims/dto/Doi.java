/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hades
 */
@Entity
@Table(name = "doi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Doi.findAll", query = "SELECT d FROM Doi d")})
public class Doi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "madoi", nullable = false)
    private Integer madoi;
    @Basic(optional = false)
    @Column(name = "sodoi", nullable = false)
    private int sodoi;
    @JoinColumn(name = "madoi", referencedColumnName = "madoi", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Nhanvien nhanvien;

    public Doi() {
    }

    public Doi(Integer madoi) {
        this.madoi = madoi;
    }

    public Doi(Integer madoi, int sodoi) {
        this.madoi = madoi;
        this.sodoi = sodoi;
    }

    public Integer getMadoi() {
        return madoi;
    }

    public void setMadoi(Integer madoi) {
        this.madoi = madoi;
    }

    public int getSodoi() {
        return sodoi;
    }

    public void setSodoi(int sodoi) {
        this.sodoi = sodoi;
    }

    public Nhanvien getNhanvien() {
        return nhanvien;
    }

    public void setNhanvien(Nhanvien nhanvien) {
        this.nhanvien = nhanvien;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (madoi != null ? madoi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Doi)) {
            return false;
        }
        Doi other = (Doi) object;
        if ((this.madoi == null && other.madoi != null) || (this.madoi != null && !this.madoi.equals(other.madoi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return Integer.toString(sodoi);
    }
    
}
