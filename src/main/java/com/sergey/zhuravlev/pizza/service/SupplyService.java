package com.sergey.zhuravlev.pizza.service;

import com.sergey.zhuravlev.pizza.dto.supplier.SupplyRequestDto;
import com.sergey.zhuravlev.pizza.dto.supplier.SupplyUpdateRequestDto;
import com.sergey.zhuravlev.pizza.entity.Ingredient;
import com.sergey.zhuravlev.pizza.entity.Supplier;
import com.sergey.zhuravlev.pizza.entity.Supply;
import com.sergey.zhuravlev.pizza.repository.IngredientRepository;
import com.sergey.zhuravlev.pizza.repository.SupplierRepository;
import com.sergey.zhuravlev.pizza.repository.SupplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplyService {

    private final SupplyRepository supplyRepository;
    private final SupplierRepository supplierRepository;
    private final IngredientRepository ingredientRepository;

    @Transactional
    public Supply createSupply(Supplier supplier, SupplyRequestDto supplyRequestDto) {
        Ingredient ingredient = ingredientRepository
                .findById(supplyRequestDto.getIngredientId())
                .orElseThrow(EntityNotFoundException::new);
        Supply supply = new Supply(null,
                supplier,
                ingredient,
                supplyRequestDto.getSupplyTime(),
                supplyRequestDto.getPrice(),
                supplyRequestDto.getCurrency());
        return supplyRepository.save(supply);
    }

    @Transactional
    public Collection<Supply> createSupplies(Supplier supplier, Collection<SupplyRequestDto> suppliesRequestDto) {
        Collection<Supply> result = new ArrayList<>();
        for (SupplyRequestDto supplyRequestDto : suppliesRequestDto) {
            result.add(createSupply(supplier, supplyRequestDto));
        }
        return result;
    }

    @Transactional
    public Collection<Supply> updateSupply(Supplier supplier, Collection<SupplyUpdateRequestDto> suppliesDto) {
        Collection<Supply> result = new ArrayList<>();
        supplier = supplierRepository.findById(supplier.getId()).orElseThrow(EntityNotFoundException::new);
        Map<Long, Supply> oldSupply = supplier.getSupplies().stream().collect(Collectors.toMap(Supply::getId, s -> s));
        for (SupplyUpdateRequestDto supplyDto : suppliesDto) {
            Ingredient ingredient = ingredientRepository.findById(supplyDto.getIngredientId())
                    .orElseThrow(EntityNotFoundException::new);
            Supply supply;
            if (supplyDto.getId() != null) {
                supply = oldSupply.remove(supplyDto.getId());
                if (supply == null) {
                    throw new EntityNotFoundException();
                }
                supply.setSupplier(supplier);
                supply.setIngredient(ingredient);
                supply.setSupplyTime(supplyDto.getSupplyTime());
                supply.setPrice(supplyDto.getPrice());
                supply.setCurrency(supplyDto.getCurrency());
            } else {
                supply = new Supply(null,
                        supplier,
                        ingredient,
                        supplyDto.getSupplyTime(),
                        supplyDto.getPrice(),
                        supplyDto.getCurrency());
            }
            supply = supplyRepository.save(supply);
            result.add(supply);
        }

        for (Supply value : oldSupply.values()) {
            supplier.getSupplies().remove(value);
            supplyRepository.deleteById(value.getId());
        }

        return result;
    }

    @Transactional
    public void deleteSupply(Long id) {
        supplyRepository.deleteById(id);
    }


}
