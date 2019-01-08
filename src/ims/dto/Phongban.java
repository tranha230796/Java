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
@Table(name = "phongban")
@NamedQueries({
    @NamedQuery(name = "Phongban.findAll", query = "SELECT p FROM Phongban p")})
public class Phongban implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idphongban", nullable = false)
    private Integer idphongban;
    @Basic(optional = false)
    @Column(name = "ten", nullable = false, length = 255)
    private String ten;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idphongban")
    private Collection<Nhanvien> nhanvienCollection;

    public Phongban() {
    }

    public Phongban(Integer idphongban) {
        this.idphongban = idphongban;
    }

    public Phongban(Integer idphongban, String ten) {
        this.idphongban = idphongban;
        this.ten = ten;
    }

    public Integer getIdphongban() {
        return idphongban;
    }

    public void setIdphongban(Integer idphongban) {
        this.idphongban = idphongban;
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
        hash += (idphongban != null ? idphongban.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Phongban)) {
            return false;
        }
        Phongban other = (Phongban) object;
        if ((this.idphongban == null && other.idphongban != null) || (this.idphongban != null && !this.idphongban.equals(other.idphongban))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ims.dto.Phongban[ idphongban=" + idphongban + " ]";
    }
    
}
