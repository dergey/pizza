package com.sergey.zhuravlev.pizza.dto.supplier;

import com.sergey.zhuravlev.pizza.entity.Address;
import com.sergey.zhuravlev.pizza.entity.Supply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierUpdateRequestDto {

    private String title;

    private Address address;

    private Collection<SupplyUpdateRequestDto> supplies;

}
