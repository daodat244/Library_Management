package com.example.demo.repository;

import com.example.demo.entity.Sach;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SachRepository extends JpaRepository<Sach, String> {
    boolean existsByMasach(String masach); 
    List<Sach> findByTensachContainingIgnoreCase(String tensach);

    @EntityGraph(attributePaths = {"tacGia", "nhaXuatBan", "theLoai"})
    @Override
    List<Sach> findAll();

    @EntityGraph(attributePaths = {"tacGia", "nhaXuatBan", "theLoai"})
    @Override
    Optional<Sach> findById(String masach);
    
    @Query("SELECT s FROM Sach s WHERE LOWER(s.tacGia.tentacgia) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<Sach> findByTacGia_TentacgiaIgnoreCaseContaining(@Param("searchText") String searchText);

    @Query("SELECT s FROM Sach s WHERE LOWER(s.nhaXuatBan.tennxb) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<Sach> findByNhaXuatBan_TennxbContainingIgnoreCase(@Param("searchText") String searchText);

    @Query("SELECT s FROM Sach s WHERE LOWER(s.theLoai.tentheloai) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<Sach> findByTheLoai_TentheloaiContainingIgnoreCase(@Param("searchText") String searchText);
    
    // Đếm số lượng đầu sách
    @Query("SELECT COUNT(s) FROM Sach s")
    Long countSoLuongDauSach();

    // Tính tổng số lượng sách
    @Query("SELECT SUM(s.soluong) FROM Sach s")
    Long sumTongSoLuongSach();

    // Lấy danh sách sách được mượn nhiều nhất
    @Query("SELECT s, COUNT(ct) as soLanMuon FROM Sach s JOIN ChiTietPhieuMuon ct ON s.masach = ct.masach GROUP BY s ORDER BY soLanMuon DESC")
    List<Sach> findSachDuocMuonNhieuNhat();
   

}
