package com.example.demo.service;

import com.example.demo.dto.NhanVienDTO;
import com.example.demo.dto.NhanVienThongKeDTO;
import com.example.demo.entity.NhanVien;
import com.example.demo.repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

@Service
public class NhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<NhanVienDTO> getAll() {
        return nhanVienRepository.findAll().stream()
                .map(nv -> modelMapper.map(nv, NhanVienDTO.class))
                .collect(Collectors.toList());
    }

    public NhanVienDTO getById(int id) {
        Optional<NhanVien> nv = nhanVienRepository.findById(id);
        return nv.map(nvEntity -> modelMapper.map(nvEntity, NhanVienDTO.class)).orElse(null);
    }

    public NhanVienDTO create(NhanVienDTO nvDTO) {
        NhanVien nv = modelMapper.map(nvDTO, NhanVien.class);
        String hashedPassword = BCrypt.hashpw(nv.getPassword(), BCrypt.gensalt());
        nv.setPassword(hashedPassword);
        NhanVien savedNv = nhanVienRepository.save(nv);
        return modelMapper.map(savedNv, NhanVienDTO.class);
    }

    public NhanVienDTO update(int id, NhanVienDTO nvDTO) {
        Optional<NhanVien> optional = nhanVienRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        NhanVien nv = optional.get();
        modelMapper.map(nvDTO, nv);
        if (nvDTO.getPassword() != null && !nvDTO.getPassword().isBlank()) {
            nv.setPassword(BCrypt.hashpw(nvDTO.getPassword(), BCrypt.gensalt()));
        }
        NhanVien updatedNv = nhanVienRepository.save(nv);
        return modelMapper.map(updatedNv, NhanVienDTO.class);
    }

    public void delete(int id) {
        if (nhanVienRepository.existsById(id)) {
            nhanVienRepository.deleteById(id);
        }
    }

    public List<NhanVienDTO> searchByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAll();
        }
        return nhanVienRepository.findByTennvContainingIgnoreCase(keyword).stream()
                .map(nv -> modelMapper.map(nv, NhanVienDTO.class))
                .collect(Collectors.toList());
    }

    public List<NhanVienThongKeDTO> thongKeNhanVien() {
        return nhanVienRepository.thongKeNhanVien();
    }

    public NhanVienDTO convertToDTO(NhanVien get) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}