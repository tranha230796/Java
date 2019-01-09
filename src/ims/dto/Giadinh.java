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
@Table(name = "giadinh")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Giadinh.findAll", query = "SELECT g FROM Giadinh g")})
public class Giadinh implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idgiadinh", nullable = false)
    private Integer idgiadinh;
    @Basic(optional = false)
    @Column(name = "tengiadinh", nullable = false, length = 255)
    private String tengiadinh;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idgiadinh")
    private List<Nhanvien> nhanvienList;

    public Giadinh() {
    }

    public Giadinh(Integer idgiadinh) {
        this.idgiadinh = idgiadinh;
    }

    public Giadinh(Integer idgiadinh, String tengiadinh) {
        this.idgiadinh = idgiadinh;
        this.tengiadinh = tengiadinh;
    }

    public Integer getIdgiadinh() {
        return idgiadinh;
    }

    public void setIdgiadinh(Integer idgiadinh) {
        this.idgiadinh = idgiadinh;
    }

    public String getTengiadinh() {
        return tengiadinh;
    }

    public void setTengiadinh(String tengiadinh) {
        this.tengiadinh = tengiadinh;
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
        hash += (idgiadinh != null ? idgiadinh.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Giadinh)) {
            return false;
        }
        Giadinh other = (Giadinh) object;
        if ((this.idgiadinh == null && other.idgiadinh != null) || (this.idgiadinh != null && !this.idgiadinh.equals(other.idgiadinh))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tengiadinh;
    }
    
}
