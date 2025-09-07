package com.example.demo.service;

import com.example.demo.dto.DocGiaThongKeDTO;
import com.example.demo.entity.DocGia;
import com.example.demo.repository.DocGiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocGiaService {

    @Autowired
    private DocGiaRepository repository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^0\\d{9}$");

    public List<DocGia> getAll() {
        return repository.findAll();
    }

    public Optional<DocGia> getById(int madocgia) {
        return repository.findById(madocgia);
    }

    @Transactional
    public DocGia add(DocGia dg) {
        validateDocGia(dg);
        if (dg.getTendocgia() != null && repository.existsByTendocgia(dg.getTendocgia().trim())) {
            throw new IllegalArgumentException("Tên độc giả đã tồn tại");
        }
        return repository.save(dg);
    }

    @Transactional
    public DocGia update(int madocgia, DocGia dg) {
        if (!repository.existsById(madocgia)) {
            throw new IllegalArgumentException("Độc giả không tồn tại");
        }
        validateDocGia(dg);
        if (dg.getTendocgia() != null && repository.existsByTendocgiaAndMadocgiaNot(dg.getTendocgia().trim(), madocgia)) {
            throw new IllegalArgumentException("Tên độc giả đã tồn tại");
        }
        dg.setMadocgia(madocgia);
        return repository.save(dg);
    }

    @Transactional
    public void delete(int madocgia) {
        if (!repository.existsById(madocgia)) {
            throw new IllegalArgumentException("Độc giả không tồn tại");
        }
        repository.deleteById(madocgia);
    }

    public List<DocGia> search(String searchText, String criteria) {
        if (searchText.isEmpty()) {
            return repository.findAll();
        }
        if (criteria.equals("Mã Độc Giả")) {
            try {
                int id = Integer.parseInt(searchText);
                Optional<DocGia> dg = repository.findById(id);
                return dg.map(List::of).orElse(List.of());
            } catch (NumberFormatException e) {
                return List.of();
            }
        } else {
            return repository.findByTendocgiaContainingIgnoreCase(searchText);
        }
    }

    public List<DocGiaThongKeDTO> getThongKeDocGia() {
        return repository.thongKeDocGia("Đã trả", "Chưa trả");
    }

    private void validateDocGia(DocGia dg) {
        if (dg.getTendocgia() == null || dg.getTendocgia().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên độc giả không được để trống");
        }
        if (dg.getEmail() == null || !EMAIL_PATTERN.matcher(dg.getEmail()).matches()) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }
        if (dg.getSdt() != null && !PHONE_PATTERN.matcher(dg.getSdt()).matches()) {
            throw new IllegalArgumentException("Số điện thoại phải có 10 chữ số và bắt đầu bằng 0");
        }
        if (dg.getDiachi() != null && dg.getDiachi().length() > 255) {
            throw new IllegalArgumentException("Địa chỉ không được vượt quá 255 ký tự");
        }
    }
}
