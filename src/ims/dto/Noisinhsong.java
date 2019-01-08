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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Hades
 */
@Entity
@Table(name = "noisinhsong")
@NamedQueries({
    @NamedQuery(name = "Noisinhsong.findAll", query = "SELECT n FROM Noisinhsong n")})
public class Noisinhsong implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idnoisinhsong", nullable = false)
    private Integer idnoisinhsong;
    @Basic(optional = false)
    @Lob
    @Column(name = "noiohientai", nullable = false, length = 65535)
    private String noiohientai;
    @Basic(optional = false)
    @Lob
    @Column(name = "thuongtru", nullable = false, length = 65535)
    private String thuongtru;
    @Basic(optional = false)
    @Lob
    @Column(name = "noisinh", nullable = false, length = 65535)
    private String noisinh;
    @Basic(optional = false)
    @Lob
    @Column(name = "nguyenquan", nullable = false, length = 65535)
    private String nguyenquan;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idnoisinhsong")
    private Collection<Nhanvien> nhanvienCollection;

    public Noisinhsong() {
    }

    public Noisinhsong(Integer idnoisinhsong) {
        this.idnoisinhsong = idnoisinhsong;
    }

    public Noisinhsong(Integer idnoisinhsong, String noiohientai, String thuongtru, String noisinh, String nguyenquan) {
        this.idnoisinhsong = idnoisinhsong;
        this.noiohientai = noiohientai;
        this.thuongtru = thuongtru;
        this.noisinh = noisinh;
        this.nguyenquan = nguyenquan;
    }

    public Integer getIdnoisinhsong() {
        return idnoisinhsong;
    }

    public void setIdnoisinhsong(Integer idnoisinhsong) {
        this.idnoisinhsong = idnoisinhsong;
    }

    public String getNoiohientai() {
        return noiohientai;
    }

    public void setNoiohientai(String noiohientai) {
        this.noiohientai = noiohientai;
    }

    public String getThuongtru() {
        return thuongtru;
    }

    public void setThuongtru(String thuongtru) {
        this.thuongtru = thuongtru;
    }

    public String getNoisinh() {
        return noisinh;
    }

    public void setNoisinh(String noisinh) {
        this.noisinh = noisinh;
    }

    public String getNguyenquan() {
        return nguyenquan;
    }

    public void setNguyenquan(String nguyenquan) {
        this.nguyenquan = nguyenquan;
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
        hash += (idnoisinhsong != null ? idnoisinhsong.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Noisinhsong)) {
            return false;
        }
        Noisinhsong other = (Noisinhsong) object;
        if ((this.idnoisinhsong == null && other.idnoisinhsong != null) || (this.idnoisinhsong != null && !this.idnoisinhsong.equals(other.idnoisinhsong))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ims.dto.Noisinhsong[ idnoisinhsong=" + idnoisinhsong + " ]";
    }
    
}
