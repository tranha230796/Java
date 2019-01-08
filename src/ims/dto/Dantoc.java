/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dto;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Hades
 */
@Entity
@Table(name = "dantoc")
@NamedQueries({
    @NamedQuery(name = "Dantoc.findAll", query = "SELECT d FROM Dantoc d")})
public class Dantoc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "iddantoc", nullable = false)
    private Integer iddantoc;
    @Basic(optional = false)
    @Column(name = "ten", nullable = false, length = 255)
    private String ten;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iddantoc")
    private Collection<Nhanvien> nhanvienCollection;

    public Dantoc() {
    }

    public Dantoc(Integer iddantoc) {
        this.iddantoc = iddantoc;
    }

    public Dantoc(Integer iddantoc, String ten) {
        this.iddantoc = iddantoc;
        this.ten = ten;
    }

    public Integer getIddantoc() {
        return iddantoc;
    }

    public void setIddantoc(Integer iddantoc) {
        this.iddantoc = iddantoc;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Collection<Nhanvien> getNhanvienCollection() {
        return nhanvienCollection;
    }

    public void setNhanvienCollection(Collection<Nhanvien> nhanvienCollection) {
        this.nhanvienCollection = nhanvienCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddantoc != null ? iddantoc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dantoc)) {
            return false;
        }
        Dantoc other = (Dantoc) object;
        if ((this.iddantoc == null && other.iddantoc != null) || (this.iddantoc != null && !this.iddantoc.equals(other.iddantoc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ten;
    }
    
}
