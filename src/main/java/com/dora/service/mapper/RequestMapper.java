package com.dora.service.mapper;

import com.dora.domain.*;
import com.dora.service.dto.RequestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Request and its DTO RequestDTO.
 */
@Mapper(componentModel = "spring", uses = {UserExtraMapper.class, RequestItemsMapper.class})
public interface RequestMapper extends EntityMapper<RequestDTO, Request> {

    @Mapping(source = "userExtra.id", target = "userExtraId")
    @Mapping(source = "requestItems.id", target = "requestItemsId")
    RequestDTO toDto(Request request); 

    @Mapping(source = "userExtraId", target = "userExtra")
    @Mapping(source = "requestItemsId", target = "requestItems")
    Request toEntity(RequestDTO requestDTO);

    default Request fromId(Long id) {
        if (id == null) {
            return null;
        }
        Request request = new Request();
        request.setId(id);
        return request;
    }
}
