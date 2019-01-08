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

/**
 *
 * @author Hades
 */
@Entity
@Table(name = "to")
@NamedQueries({
    @NamedQuery(name = "To.findAll", query = "SELECT t FROM To t")})
public class To implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "mato", nullable = false)
    private Integer mato;
    @Basic(optional = false)
    @Column(name = "soto", nullable = false)
    private int soto;
    @JoinColumn(name = "mato", referencedColumnName = "mato", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Nhanvien nhanvien;

    public To() {
    }

    public To(Integer mato) {
        this.mato = mato;
    }

    public To(Integer mato, int soto) {
        this.mato = mato;
        this.soto = soto;
    }

    public Integer getMato() {
        return mato;
    }

    public void setMato(Integer mato) {
        this.mato = mato;
    }

    public int getSoto() {
        return soto;
    }

    public void setSoto(int soto) {
        this.soto = soto;
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
        hash += (mato != null ? mato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof To)) {
            return false;
        }
        To other = (To) object;
        if ((this.mato == null && other.mato != null) || (this.mato != null && !this.mato.equals(other.mato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ims.dto.To[ mato=" + mato + " ]";
    }
    
}
