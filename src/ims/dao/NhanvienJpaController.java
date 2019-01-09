/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dao;

import ims.dao.exceptions.IllegalOrphanException;
import ims.dao.exceptions.NonexistentEntityException;
import ims.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ims.dto.Quoctich;
import ims.dto.Giadinh;
import ims.dto.Dantoc;
import ims.dto.Phongban;
import ims.dto.Tongiao;
import ims.dto.Noisinhsong;
import ims.dto.Xahoi;
import ims.dto.Tophong;
import ims.dto.Doi;
import ims.dto.Nhanvien;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hades
 */
public class NhanvienJpaController implements Serializable {

    public NhanvienJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Nhanvien nhanvien) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Quoctich idquoctich = nhanvien.getIdquoctich();
            if (idquoctich != null) {
                idquoctich = em.getReference(idquoctich.getClass(), idquoctich.getIdquoctich());
                nhanvien.setIdquoctich(idquoctich);
            }
            Giadinh idgiadinh = nhanvien.getIdgiadinh();
            if (idgiadinh != null) {
                idgiadinh = em.getReference(idgiadinh.getClass(), idgiadinh.getIdgiadinh());
                nhanvien.setIdgiadinh(idgiadinh);
            }
            Dantoc iddantoc = nhanvien.getIddantoc();
            if (iddantoc != null) {
                iddantoc = em.getReference(iddantoc.getClass(), iddantoc.getIddantoc());
                nhanvien.setIddantoc(iddantoc);
            }
            Phongban idphongban = nhanvien.getIdphongban();
            if (idphongban != null) {
                idphongban = em.getReference(idphongban.getClass(), idphongban.getIdphongban());
                nhanvien.setIdphongban(idphongban);
            }
            Tongiao idtongiao = nhanvien.getIdtongiao();
            if (idtongiao != null) {
                idtongiao = em.getReference(idtongiao.getClass(), idtongiao.getIdtongiao());
                nhanvien.setIdtongiao(idtongiao);
            }
            Noisinhsong idnoisinhsong = nhanvien.getIdnoisinhsong();
            if (idnoisinhsong != null) {
                idnoisinhsong = em.getReference(idnoisinhsong.getClass(), idnoisinhsong.getIdnoisinhsong());
                nhanvien.setIdnoisinhsong(idnoisinhsong);
            }
            Xahoi idxahoi = nhanvien.getIdxahoi();
            if (idxahoi != null) {
                idxahoi = em.getReference(idxahoi.getClass(), idxahoi.getIdxahoi());
                nhanvien.setIdxahoi(idxahoi);
            }
            Tophong tophong = nhanvien.getTophong();
            if (tophong != null) {
                tophong = em.getReference(tophong.getClass(), tophong.getMato());
                nhanvien.setTophong(tophong);
            }
            Doi doi = nhanvien.getDoi();
            if (doi != null) {
                doi = em.getReference(doi.getClass(), doi.getMadoi());
                nhanvien.setDoi(doi);
            }
            em.persist(nhanvien);
            if (idquoctich != null) {
                idquoctich.getNhanvienList().add(nhanvien);
                idquoctich = em.merge(idquoctich);
            }
            if (idgiadinh != null) {
                idgiadinh.getNhanvienList().add(nhanvien);
                idgiadinh = em.merge(idgiadinh);
            }
            if (iddantoc != null) {
                iddantoc.getNhanvienList().add(nhanvien);
                iddantoc = em.merge(iddantoc);
            }
            if (idphongban != null) {
                idphongban.getNhanvienList().add(nhanvien);
                idphongban = em.merge(idphongban);
            }
            if (idtongiao != null) {
                idtongiao.getNhanvienList().add(nhanvien);
                idtongiao = em.merge(idtongiao);
            }
            if (idnoisinhsong != null) {
                idnoisinhsong.getNhanvienList().add(nhanvien);
                idnoisinhsong = em.merge(idnoisinhsong);
            }
            if (idxahoi != null) {
                idxahoi.getNhanvienList().add(nhanvien);
                idxahoi = em.merge(idxahoi);
            }
            if (tophong != null) {
                Nhanvien oldNhanvienOfTophong = tophong.getNhanvien();
                if (oldNhanvienOfTophong != null) {
                    oldNhanvienOfTophong.setTophong(null);
                    oldNhanvienOfTophong = em.merge(oldNhanvienOfTophong);
                }
                tophong.setNhanvien(nhanvien);
                tophong = em.merge(tophong);
            }
            if (doi != null) {
                Nhanvien oldNhanvienOfDoi = doi.getNhanvien();
                if (oldNhanvienOfDoi != null) {
                    oldNhanvienOfDoi.setDoi(null);
                    oldNhanvienOfDoi = em.merge(oldNhanvienOfDoi);
                }
                doi.setNhanvien(nhanvien);
                doi = em.merge(doi);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNhanvien(nhanvien.getIdnhanvien()) != null) {
                throw new PreexistingEntityException("Nhanvien " + nhanvien + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Nhanvien nhanvien) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nhanvien persistentNhanvien = em.find(Nhanvien.class, nhanvien.getIdnhanvien());
            Quoctich idquoctichOld = persistentNhanvien.getIdquoctich();
            Quoctich idquoctichNew = nhanvien.getIdquoctich();
            Giadinh idgiadinhOld = persistentNhanvien.getIdgiadinh();
            Giadinh idgiadinhNew = nhanvien.getIdgiadinh();
            Dantoc iddantocOld = persistentNhanvien.getIddantoc();
            Dantoc iddantocNew = nhanvien.getIddantoc();
            Phongban idphongbanOld = persistentNhanvien.getIdphongban();
            Phongban idphongbanNew = nhanvien.getIdphongban();
            Tongiao idtongiaoOld = persistentNhanvien.getIdtongiao();
            Tongiao idtongiaoNew = nhanvien.getIdtongiao();
            Noisinhsong idnoisinhsongOld = persistentNhanvien.getIdnoisinhsong();
            Noisinhsong idnoisinhsongNew = nhanvien.getIdnoisinhsong();
            Xahoi idxahoiOld = persistentNhanvien.getIdxahoi();
            Xahoi idxahoiNew = nhanvien.getIdxahoi();
            Tophong tophongOld = persistentNhanvien.getTophong();
            Tophong tophongNew = nhanvien.getTophong();
            Doi doiOld = persistentNhanvien.getDoi();
            Doi doiNew = nhanvien.getDoi();
            List<String> illegalOrphanMessages = null;
            if (tophongOld != null && !tophongOld.equals(tophongNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Tophong " + tophongOld + " since its nhanvien field is not nullable.");
            }
            if (doiOld != null && !doiOld.equals(doiNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Doi " + doiOld + " since its nhanvien field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idquoctichNew != null) {
                idquoctichNew = em.getReference(idquoctichNew.getClass(), idquoctichNew.getIdquoctich());
                nhanvien.setIdquoctich(idquoctichNew);
            }
            if (idgiadinhNew != null) {
                idgiadinhNew = em.getReference(idgiadinhNew.getClass(), idgiadinhNew.getIdgiadinh());
                nhanvien.setIdgiadinh(idgiadinhNew);
            }
            if (iddantocNew != null) {
                iddantocNew = em.getReference(iddantocNew.getClass(), iddantocNew.getIddantoc());
                nhanvien.setIddantoc(iddantocNew);
            }
            if (idphongbanNew != null) {
                idphongbanNew = em.getReference(idphongbanNew.getClass(), idphongbanNew.getIdphongban());
                nhanvien.setIdphongban(idphongbanNew);
            }
            if (idtongiaoNew != null) {
                idtongiaoNew = em.getReference(idtongiaoNew.getClass(), idtongiaoNew.getIdtongiao());
                nhanvien.setIdtongiao(idtongiaoNew);
            }
            if (idnoisinhsongNew != null) {
                idnoisinhsongNew = em.getReference(idnoisinhsongNew.getClass(), idnoisinhsongNew.getIdnoisinhsong());
                nhanvien.setIdnoisinhsong(idnoisinhsongNew);
            }
            if (idxahoiNew != null) {
                idxahoiNew = em.getReference(idxahoiNew.getClass(), idxahoiNew.getIdxahoi());
                nhanvien.setIdxahoi(idxahoiNew);
            }
            if (tophongNew != null) {
                tophongNew = em.getReference(tophongNew.getClass(), tophongNew.getMato());
                nhanvien.setTophong(tophongNew);
            }
            if (doiNew != null) {
                doiNew = em.getReference(doiNew.getClass(), doiNew.getMadoi());
                nhanvien.setDoi(doiNew);
            }
            nhanvien = em.merge(nhanvien);
            if (idquoctichOld != null && !idquoctichOld.equals(idquoctichNew)) {
                idquoctichOld.getNhanvienList().remove(nhanvien);
                idquoctichOld = em.merge(idquoctichOld);
            }
            if (idquoctichNew != null && !idquoctichNew.equals(idquoctichOld)) {
                idquoctichNew.getNhanvienList().add(nhanvien);
                idquoctichNew = em.merge(idquoctichNew);
            }
            if (idgiadinhOld != null && !idgiadinhOld.equals(idgiadinhNew)) {
                idgiadinhOld.getNhanvienList().remove(nhanvien);
                idgiadinhOld = em.merge(idgiadinhOld);
            }
            if (idgiadinhNew != null && !idgiadinhNew.equals(idgiadinhOld)) {
                idgiadinhNew.getNhanvienList().add(nhanvien);
                idgiadinhNew = em.merge(idgiadinhNew);
            }
            if (iddantocOld != null && !iddantocOld.equals(iddantocNew)) {
                iddantocOld.getNhanvienList().remove(nhanvien);
                iddantocOld = em.merge(iddantocOld);
            }
            if (iddantocNew != null && !iddantocNew.equals(iddantocOld)) {
                iddantocNew.getNhanvienList().add(nhanvien);
                iddantocNew = em.merge(iddantocNew);
            }
            if (idphongbanOld != null && !idphongbanOld.equals(idphongbanNew)) {
                idphongbanOld.getNhanvienList().remove(nhanvien);
                idphongbanOld = em.merge(idphongbanOld);
            }
            if (idphongbanNew != null && !idphongbanNew.equals(idphongbanOld)) {
                idphongbanNew.getNhanvienList().add(nhanvien);
                idphongbanNew = em.merge(idphongbanNew);
            }
            if (idtongiaoOld != null && !idtongiaoOld.equals(idtongiaoNew)) {
                idtongiaoOld.getNhanvienList().remove(nhanvien);
                idtongiaoOld = em.merge(idtongiaoOld);
            }
            if (idtongiaoNew != null && !idtongiaoNew.equals(idtongiaoOld)) {
                idtongiaoNew.getNhanvienList().add(nhanvien);
                idtongiaoNew = em.merge(idtongiaoNew);
            }
            if (idnoisinhsongOld != null && !idnoisinhsongOld.equals(idnoisinhsongNew)) {
                idnoisinhsongOld.getNhanvienList().remove(nhanvien);
                idnoisinhsongOld = em.merge(idnoisinhsongOld);
            }
            if (idnoisinhsongNew != null && !idnoisinhsongNew.equals(idnoisinhsongOld)) {
                idnoisinhsongNew.getNhanvienList().add(nhanvien);
                idnoisinhsongNew = em.merge(idnoisinhsongNew);
            }
            if (idxahoiOld != null && !idxahoiOld.equals(idxahoiNew)) {
                idxahoiOld.getNhanvienList().remove(nhanvien);
                idxahoiOld = em.merge(idxahoiOld);
            }
            if (idxahoiNew != null && !idxahoiNew.equals(idxahoiOld)) {
                idxahoiNew.getNhanvienList().add(nhanvien);
                idxahoiNew = em.merge(idxahoiNew);
            }
            if (tophongNew != null && !tophongNew.equals(tophongOld)) {
                Nhanvien oldNhanvienOfTophong = tophongNew.getNhanvien();
                if (oldNhanvienOfTophong != null) {
                    oldNhanvienOfTophong.setTophong(null);
                    oldNhanvienOfTophong = em.merge(oldNhanvienOfTophong);
                }
                tophongNew.setNhanvien(nhanvien);
                tophongNew = em.merge(tophongNew);
            }
            if (doiNew != null && !doiNew.equals(doiOld)) {
                Nhanvien oldNhanvienOfDoi = doiNew.getNhanvien();
                if (oldNhanvienOfDoi != null) {
                    oldNhanvienOfDoi.setDoi(null);
                    oldNhanvienOfDoi = em.merge(oldNhanvienOfDoi);
                }
                doiNew.setNhanvien(nhanvien);
                doiNew = em.merge(doiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = nhanvien.getIdnhanvien();
                if (findNhanvien(id) == null) {
                    throw new NonexistentEntityException("The nhanvien with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nhanvien nhanvien;
            try {
                nhanvien = em.getReference(Nhanvien.class, id);
                nhanvien.getIdnhanvien();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nhanvien with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Tophong tophongOrphanCheck = nhanvien.getTophong();
            if (tophongOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Nhanvien (" + nhanvien + ") cannot be destroyed since the Tophong " + tophongOrphanCheck + " in its tophong field has a non-nullable nhanvien field.");
            }
            Doi doiOrphanCheck = nhanvien.getDoi();
            if (doiOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Nhanvien (" + nhanvien + ") cannot be destroyed since the Doi " + doiOrphanCheck + " in its doi field has a non-nullable nhanvien field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Quoctich idquoctich = nhanvien.getIdquoctich();
            if (idquoctich != null) {
                idquoctich.getNhanvienList().remove(nhanvien);
                idquoctich = em.merge(idquoctich);
            }
            Giadinh idgiadinh = nhanvien.getIdgiadinh();
            if (idgiadinh != null) {
                idgiadinh.getNhanvienList().remove(nhanvien);
                idgiadinh = em.merge(idgiadinh);
            }
            Dantoc iddantoc = nhanvien.getIddantoc();
            if (iddantoc != null) {
                iddantoc.getNhanvienList().remove(nhanvien);
                iddantoc = em.merge(iddantoc);
            }
            Phongban idphongban = nhanvien.getIdphongban();
            if (idphongban != null) {
                idphongban.getNhanvienList().remove(nhanvien);
                idphongban = em.merge(idphongban);
            }
            Tongiao idtongiao = nhanvien.getIdtongiao();
            if (idtongiao != null) {
                idtongiao.getNhanvienList().remove(nhanvien);
                idtongiao = em.merge(idtongiao);
            }
            Noisinhsong idnoisinhsong = nhanvien.getIdnoisinhsong();
            if (idnoisinhsong != null) {
                idnoisinhsong.getNhanvienList().remove(nhanvien);
                idnoisinhsong = em.merge(idnoisinhsong);
            }
            Xahoi idxahoi = nhanvien.getIdxahoi();
            if (idxahoi != null) {
                idxahoi.getNhanvienList().remove(nhanvien);
                idxahoi = em.merge(idxahoi);
            }
            em.remove(nhanvien);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Nhanvien> findNhanvienEntities() {
        return findNhanvienEntities(true, -1, -1);
    }

    public List<Nhanvien> findNhanvienEntities(int maxResults, int firstResult) {
        return findNhanvienEntities(false, maxResults, firstResult);
    }

    private List<Nhanvien> findNhanvienEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Nhanvien.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Nhanvien findNhanvien(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Nhanvien.class, id);
        } finally {
            em.close();
        }
    }

    public int getNhanvienCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Nhanvien> rt = cq.from(Nhanvien.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
