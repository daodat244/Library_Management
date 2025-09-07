package com.example.demo.service;

import com.example.demo.dto.TheLoaiDTO;
import com.example.demo.dto.ThongKeTheLoaiDTO;
import com.example.demo.entity.TheLoai;
import com.example.demo.repository.TheLoaiRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TheLoaiService {

    @Autowired
    private TheLoaiRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<TheLoaiDTO> getAll() {
        return repository.findAll().stream()
                .map(theLoai -> modelMapper.map(theLoai, TheLoaiDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public TheLoaiDTO add(TheLoaiDTO theLoaiDTO) {
        TheLoai theLoai = modelMapper.map(theLoaiDTO, TheLoai.class);
        validateTheLoai(theLoai);
        if (theLoai.getTentheloai() != null && repository.existsByTentheloai(theLoai.getTentheloai().trim())) {
            throw new IllegalArgumentException("Tên thể loại đã tồn tại");
        }
        TheLoai savedTheLoai = repository.save(theLoai);
        return modelMapper.map(savedTheLoai, TheLoaiDTO.class);
    }

    @Transactional
    public TheLoaiDTO update(int matheloai, TheLoaiDTO theLoaiDTO) {
        if (!repository.existsById(matheloai)) {
            throw new IllegalArgumentException("Thể loại không tồn tại");
        }
        TheLoai theLoai = modelMapper.map(theLoaiDTO, TheLoai.class);
        validateTheLoai(theLoai);
        if (theLoai.getTentheloai() != null && repository.existsByTentheloaiAndMatheloaiNot(theLoai.getTentheloai().trim(), matheloai)) {
            throw new IllegalArgumentException("Tên thể loại đã tồn tại");
        }
        theLoai.setMatheloai(matheloai);
        TheLoai updatedTheLoai = repository.save(theLoai);
        return modelMapper.map(updatedTheLoai, TheLoaiDTO.class);
    }

    @Transactional
    public void delete(int matheloai) {
        if (!repository.existsById(matheloai)) {
            throw new IllegalArgumentException("Thể loại không tồn tại");
        }
        repository.deleteById(matheloai);
    }

    public List<TheLoaiDTO> search(String searchText, String criteria) {
        List<TheLoai> theLoaiList;
        if (searchText.isEmpty()) {
            theLoaiList = repository.findAll();
        } else if (criteria.equals("Mã thể loại")) {
            try {
                int id = Integer.parseInt(searchText);
                Optional<TheLoai> theLoai = repository.findById(id);
                theLoaiList = theLoai.map(List::of).orElse(List.of());
            } catch (NumberFormatException e) {
                theLoaiList = List.of();
            }
        } else {
            theLoaiList = repository.findByTentheloaiContainingIgnoreCase(searchText);
        }
        return theLoaiList.stream()
                .map(theLoai -> modelMapper.map(theLoai, TheLoaiDTO.class))
                .collect(Collectors.toList());
    }

    public List<ThongKeTheLoaiDTO> thongKeTheLoai() {
        return repository.thongKeTheLoai();
    }

    private void validateTheLoai(TheLoai theLoai) {
        if (theLoai.getTentheloai() == null || theLoai.getTentheloai().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên thể loại không được để trống");
        }
        if (!theLoai.getTentheloai().matches("^[a-zA-Z\\s-]+$")) {
            throw new IllegalArgumentException("Tên thể loại chỉ được chứa chữ cái, khoảng trắng và dấu gạch ngang");
        }
    
        if (theLoai.getMota() != null && theLoai.getMota().length() > 1000) {
            throw new IllegalArgumentException("Mô tả không được vượt quá 1000 ký tự");
        }
    }
}
