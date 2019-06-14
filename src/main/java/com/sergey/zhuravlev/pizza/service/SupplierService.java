package com.sergey.zhuravlev.pizza.service;

import com.sergey.zhuravlev.pizza.entity.Address;
import com.sergey.zhuravlev.pizza.entity.Supplier;
import com.sergey.zhuravlev.pizza.entity.Supply;
import com.sergey.zhuravlev.pizza.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    @Transactional(readOnly = true)
    public Page<Supplier> list(Pageable pageable) {
        return supplierRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Supplier getSupplier(Long id) {
        return supplierRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Supplier createSupplier(String title, Address address) {
        Supplier supplier = new Supplier(null, title, address, new ArrayList<>());
        return supplierRepository.save(supplier);
    }

    @Transactional
    public Supplier updateSupply(Long supplierId, Collection<Supply> supplies) {
        Supplier supplier = supplierRepository
                .findById(supplierId)
                .orElseThrow(EntityNotFoundException::new);
        supplier.setSupplies(supplies);
        return supplierRepository.save(supplier);
    }

    @Transactional
    public Supplier updateSupplier(Long id,
                                          String title,
                                          Address address,
                                          Collection<Supply> supplies) {
        Supplier supplier = supplierRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
        supplier.setTitle(title);
        supplier.setAddress(address);
        supplier.setSupplies(supplies);
        return supplierRepository.save(supplier);
    }

    @Transactional
    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }

}
