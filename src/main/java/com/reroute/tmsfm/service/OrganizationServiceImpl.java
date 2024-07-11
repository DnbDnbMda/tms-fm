package com.reroute.tmsfm.service;

import com.reroute.tmsfm.dto.OrganizationDto;
import com.reroute.tmsfm.entity.Organization;
import com.reroute.tmsfm.exception.OrganizationDtoBodyIsNull;
import com.reroute.tmsfm.exception.OrganizationNotFoundException;
import com.reroute.tmsfm.exception.OrganizationWithSpecifiedIdAlreadyExists;
import com.reroute.tmsfm.mapper.OrganizationMapperImpl;
import com.reroute.tmsfm.repository.AccountRepository;
import com.reroute.tmsfm.repository.OrganizationRepository;
import com.reroute.tmsfm.utility.Constant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Data
@Component
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationMapperImpl organizationMapper;
    private final OrganizationRepository organizationRepository;
    private final AccountRepository accountRepository;
    private final AccountServiceImpl accountServiceImpl;
    private final OrganizationMapperImpl organizationMapperImpl;

    @Override
    @Transactional
    public Optional<OrganizationDto> createOrganization(OrganizationDto organizationDto) {
        if (checkOrganizationDtoNull(organizationDto)) {
            if (organizationDto.getId() != null && organizationRepository.existsById(UUID.fromString(organizationDto.getId()))) {
                log.error("Организация с идентификатором {} уже существует", organizationDto.getId());
                throw new OrganizationWithSpecifiedIdAlreadyExists(HttpStatus.BAD_REQUEST,
                        "Организация с идентификатором: " + organizationDto.getId() + " уже существует");
            } else if (organizationDto.getId() == null) {
                organizationDto.setId(UUID.randomUUID().toString());
            }
        }

        organizationDto.setCreatedDate(LocalDateTime.now());
        organizationDto.setChangedDate(null);

        log.debug("Подготовлен organizationDto для передачи в mapper {}", organizationDto);
        Organization organization = organizationMapper.toOrganization(organizationDto);
        Organization createdOrganization = organizationRepository.save(organization);
        log.info("id новой организации: {}", createdOrganization.getId());
        log.debug("В БД добавлена новая организация {}", createdOrganization);
        return Optional.of(organizationMapper.toOrganizationDto(createdOrganization));
    }

    @Override
    @Transactional
    public Optional<OrganizationDto> updateOrganization(OrganizationDto organizationDto) {
        Optional<Organization> organizationForUpdate;

        if (checkOrganizationDtoNull(organizationDto)
                && organizationDto.getId() != null
                && !organizationRepository.existsById(UUID.fromString(organizationDto.getId()))) {
            log.error("Организация с идентификатором {} не существует", organizationDto.getId());
            throw new OrganizationNotFoundException(HttpStatus.BAD_REQUEST,
                    "Организация с идентификатором " + organizationDto.getId() + "не существует");
        }

        if (organizationDto.getId() != null && organizationRepository.existsById(UUID
                .fromString(organizationDto.getId()))) {

            organizationForUpdate = getOrganizationByUuid(
                    organizationDto.getId() != null ?
                            UUID.fromString(organizationDto.getId()) : null);

            log.debug("Исходное состояние организации {}", organizationForUpdate);
            Organization updatedOrganization = organizationMapperImpl.updateOrganization(organizationDto,
                    organizationForUpdate.orElse(null));
            log.info("Организация id: {} обновлена", updatedOrganization.getId());
            log.debug("Обновленное состояние организации {}", updatedOrganization);
            return Optional.of(organizationMapper.toOrganizationDto(updatedOrganization));
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Optional<OrganizationDto> getOrganizationById(UUID organisationId) {
        if (organisationId != null) {
            Organization foundOrganization = organizationRepository.findById(organisationId).orElse(null);
            log.debug("По указанному ID={} найдена организация {}", organisationId, foundOrganization);
            if (foundOrganization != null) {
                OrganizationDto foundOrganizationDto = organizationMapper.toOrganizationDto(foundOrganization);
                log.debug("Сформирован foundOrganizationDto={}", foundOrganizationDto.toString());
                return Optional.of(foundOrganizationDto);
            } else {
                log.error("По указанному идентификатору {} организация не найдена!", organisationId);
                throw new OrganizationNotFoundException(HttpStatus.NOT_FOUND, "По указанному идентификатору = "
                        + organisationId + " организация не найдена");
            }
        } else {
            log.error("Идентификатор организации = null. Запрос не выполнен.");
            throw new OrganizationNotFoundException(HttpStatus.NOT_FOUND,
                    "Идентификатор организации = null. Запрос не выполнен.");
        }
    }

    @Override
    public void deleteOrganizationById(UUID organisationId) {
        if (organisationId != null) {
            Optional<Organization> foundOrganization = organizationRepository.findById(organisationId);
            if (foundOrganization.isPresent()) {
                log.debug("По указанному ID={} найдена организация {} для удаления", organisationId,
                        foundOrganization.get());
                organizationRepository.delete(foundOrganization.get());
                log.info("Удалена организация по указанному Id {}", organisationId);
            } else {
                log.error("По указанному ID организация не найдена! Удаление не выполнено");
                throw new OrganizationNotFoundException(HttpStatus.NOT_FOUND,
                        "По указанному ID организация не найдена! Удаление не выполнено");
            }
        } else {
            log.error("organisationId == null. Удаление не выполнено.");
            throw new OrganizationNotFoundException(HttpStatus.NOT_FOUND,
                    "organisationId == null. Удаление не выполнено.");
        }
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Optional<List<OrganizationDto>> getAllOrganizations() {
        List<Organization> foundOrganizations = organizationRepository.findAll();
        List<OrganizationDto> organizationDtoList = foundOrganizations.stream()
                .map(organizationMapper::toOrganizationDto)
                .collect(Collectors.toList());
        return Optional.of(organizationDtoList);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Optional<List<OrganizationDto>> getAllOrganizationsPages(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Organization> searchedOrganization = organizationRepository.findAll(pageRequest);
        List<OrganizationDto> organizationDtoList = searchedOrganization
                .stream()
                .map(organizationMapper::toOrganizationDto)
                .collect(Collectors.toList());
        log.info("Список полученных организаций {}", organizationDtoList);
        return Optional.of(organizationDtoList);
    }

    public Optional<Organization> getParentOrganizationByUuid(UUID parentOrganizationId) {
        if (parentOrganizationId == null) {
            return Optional.empty();
        }
        Optional<Organization> parentOrganization = organizationRepository.findById(parentOrganizationId);
        if (parentOrganization.isPresent()) {
            return parentOrganization;
        } else {
            log.error("По указанному идентификатору не найдена организация родитель");
            throw new OrganizationNotFoundException(HttpStatus.NOT_FOUND,
                    "По указанному идентификатору не найдена организация родитель");
        }
    }

    public Optional<Organization> getOwnerOrganizationByUuid(UUID ownerOrganizationUuid) {
        UUID organizationUuid = Objects.requireNonNullElse(ownerOrganizationUuid, Constant.ADMIN_ORGANIZATION_UUID);
        Optional<Organization> ownerOrganization = organizationRepository.findById(organizationUuid);
        if (ownerOrganization.isPresent()) {
            return ownerOrganization;
        } else {
            log.error("По указанному идентификатору не найдена организация владелец");
            throw new OrganizationNotFoundException(HttpStatus.NOT_FOUND,
                    "По указанному идентификатору не найдена организация владелец");
        }
    }

    public Optional<Organization> getOrganizationByUuid(UUID organizationId) {
        UUID organizationIdForSearch = Objects.requireNonNullElse(organizationId, Constant.ADMIN_ORGANIZATION_UUID);
        return organizationRepository.findById(organizationIdForSearch);
    }

    public boolean checkOrganizationDtoNull(OrganizationDto organizationDto) {
        if (organizationDto == null) {
            log.info("Полученный organizationDto равен null");
            throw new OrganizationDtoBodyIsNull(HttpStatus.BAD_REQUEST, "Полученный organizationDto равен null");
        } else return true;
    }
}
