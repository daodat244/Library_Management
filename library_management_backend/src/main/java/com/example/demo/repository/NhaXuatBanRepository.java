package com.example.demo.repository;

import com.example.demo.entity.NhaXuatBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface NhaXuatBanRepository extends JpaRepository<NhaXuatBan, Integer> {

    boolean existsByTennxb(String tennxb);

    boolean existsByTennxbAndManxbNot(String tennxb, Integer manxb);

    boolean existsByEmail(String email);

    boolean existsByEmailAndManxbNot(String email, Integer manxb);

    boolean existsBySdt(String sdt);

    boolean existsBySdtAndManxbNot(String sdt, Integer manxb);

    List<NhaXuatBan> findByTennxbContainingIgnoreCase(String tennxb);

    @Query("SELECT new com.example.demo.dto.NhaXuatBanThongKeDTO("
            + "nxb.manxb, nxb.tennxb, COUNT(s), COALESCE(SUM(s.soluong), 0)) "
            + "FROM NhaXuatBan nxb LEFT JOIN nxb.sachList s "
            + "GROUP BY nxb.manxb, nxb.tennxb")
    List<com.example.demo.dto.NhaXuatBanThongKeDTO> thongKeNXB();
}
