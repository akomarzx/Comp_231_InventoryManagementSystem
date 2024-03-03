package org.group4.comp231.inventorymanagementservice.mapper.user;

import org.group4.comp231.inventorymanagementservice.dto.user.UserSummaryInfoDto;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserRepresentationMapper {
    UserRepresentation toEntity(UserSummaryInfoDto userSummaryInfoDto);

    UserSummaryInfoDto toDto(UserRepresentation userRepresentation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserRepresentation partialUpdate(UserSummaryInfoDto userSummaryInfoDto, @MappingTarget UserRepresentation userRepresentation);
}