package com.dora.service.mapper;

import com.dora.domain.*;
import com.dora.service.dto.RequestItemsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RequestItems and its DTO RequestItemsDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface RequestItemsMapper extends EntityMapper<RequestItemsDTO, RequestItems> {

    @Mapping(source = "product.id", target = "productId")
    RequestItemsDTO toDto(RequestItems requestItems); 

    @Mapping(target = "pedidos", ignore = true)
    @Mapping(source = "productId", target = "product")
    RequestItems toEntity(RequestItemsDTO requestItemsDTO);

    default RequestItems fromId(Long id) {
        if (id == null) {
            return null;
        }
        RequestItems requestItems = new RequestItems();
        requestItems.setId(id);
        return requestItems;
    }
}
