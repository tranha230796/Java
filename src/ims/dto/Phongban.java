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
@Table(name = "phongban")
@XmlRootElement
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
    private List<Nhanvien> nhanvienList;

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
        return ten;
    }
    
}
