package com.reroute.tmsfm.mapper;

import com.reroute.tmsfm.dto.OrganizationDto;
import com.reroute.tmsfm.entity.Account;
import com.reroute.tmsfm.entity.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    @Mapping(target = "ownerOrganization", source = "ownerOrganizationDto")
    @Mapping(target = "createAuthor", source = "authorCreatedDto")
    @Mapping(target = "parent", source = "parentDto")
    @Mapping(target = "name", source = "organizationDto.name")
    @Mapping(target = "fullName", source = "organizationDto.fullName")
    @Mapping(target = "group", source = "organizationDto.group")
    @Mapping(target = "createdDate", source = "organizationDto.createdDate")
    @Mapping(target = "changedDate", source = "organizationDto.changedDate")
    @Mapping(target = "id", source = "organizationDto.id")
    @Mapping(target = "changeAuthor", ignore = true)
    @Mapping(target = "markedOnDelete", source = "organizationDto.markedOnDelete")
    @Mapping(target = "inn", source = "organizationDto.inn")
    @Mapping(target = "kpp", source = "organizationDto.kpp")
    Organization toOrganization(OrganizationDto organizationDto,
                                Organization ownerOrganizationDto,
                                Organization parentDto,
                                Account authorCreatedDto);


    @Mapping(target = "ownerOrganization", expression = "java(Utils.getUuidOwnerOrganization(organization))")
    @Mapping(target = "createAuthor", expression = "java(Utils.getUuidCreateAuthor(organization))")
    @Mapping(target = "parent", expression = "java(Utils.getUuidParentOrganization(organization))")
    @Mapping(target = "changeAuthor", expression = "java(Utils.getUuidChangeAuthor(organization))")
    OrganizationDto toOrganizationDto(Organization organization);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "changedDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "ownerOrganization", source = "ownerOrganizationDto")
    @Mapping(target = "createAuthor", source = "authorCreateDto")
    @Mapping(target = "parent", source = "parentOrganizationDto")
    @Mapping(target = "changeAuthor", source = "authorChangeDto")
    @Mapping(target = "name", source = "organizationDto.name")
    @Mapping(target = "fullName", source = "organizationDto.fullName")
    @Mapping(target = "inn", source = "organizationDto.inn")
    @Mapping(target = "kpp", source = "organizationDto.kpp")
    @Mapping(target = "markedOnDelete", source = "organizationDto.markedOnDelete")
    @Mapping(target = "group", source = "organizationDto.group")
    Organization updateOrganization(OrganizationDto organizationDto,
                                    Organization ownerOrganizationDto,
                                    Organization parentOrganizationDto,
                                    Account authorCreateDto,
                                    Account authorChangeDto,
                                    @MappingTarget Organization organization);

    public class Utils {
        private Utils() {

        }

        public static UUID getUuidOwnerOrganization(Organization source) {
            return source.getOwnerOrganization() == null ? null : source.getOwnerOrganization().getId();
        }

        public static UUID getUuidParentOrganization(Organization source) {
            return source.getParent() == null ? null : source.getParent().getId();
        }

        public static UUID getUuidCreateAuthor(Organization source) {
            return source.getCreateAuthor() == null ? null : source.getCreateAuthor().getId();
        }

        public static UUID getUuidChangeAuthor(Organization source) {
            return source.getChangeAuthor() == null ? null : source.getChangeAuthor().getId();
        }
    }
}
