/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dto;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hades
 */
@Entity
@Table(name = "quoctich")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Quoctich.findAll", query = "SELECT q FROM Quoctich q")})
public class Quoctich implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idquoctich", nullable = false)
    private Integer idquoctich;
    @Basic(optional = false)
    @Column(name = "ten", nullable = false, length = 255)
    private String ten;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idquoctich")
    private List<Nhanvien> nhanvienList;

    public Quoctich() {
    }

    public Quoctich(Integer idquoctich) {
        this.idquoctich = idquoctich;
    }

    public Quoctich(Integer idquoctich, String ten) {
        this.idquoctich = idquoctich;
        this.ten = ten;
    }

    public Integer getIdquoctich() {
        return idquoctich;
    }

    public void setIdquoctich(Integer idquoctich) {
        this.idquoctich = idquoctich;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    @XmlTransient
    public List<Nhanvien> getNhanvienList() {
        return nhanvienList;
    }

    public void setNhanvienList(List<Nhanvien> nhanvienList) {
        this.nhanvienList = nhanvienList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idquoctich != null ? idquoctich.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Quoctich)) {
            return false;
        }
        Quoctich other = (Quoctich) object;
        if ((this.idquoctich == null && other.idquoctich != null) || (this.idquoctich != null && !this.idquoctich.equals(other.idquoctich))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ims.dto.Quoctich[ idquoctich=" + idquoctich + " ]";
    }
    
}
