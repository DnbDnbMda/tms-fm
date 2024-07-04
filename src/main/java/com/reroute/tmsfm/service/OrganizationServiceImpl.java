package com.reroute.tmsfm.service;

import com.reroute.tmsfm.dto.OrganizationDto;
import com.reroute.tmsfm.entity.Account;
import com.reroute.tmsfm.entity.Organization;
import com.reroute.tmsfm.exception.OrganizationDtoBodyIsNull;
import com.reroute.tmsfm.exception.OrganizationNotFoundException;
import com.reroute.tmsfm.exception.OrganizationWithSpecifiedIdAlreadyExists;
import com.reroute.tmsfm.mapper.OrganizationMapperImpl;
import com.reroute.tmsfm.repository.AccountRepository;
import com.reroute.tmsfm.repository.OrganizationRepository;
import com.reroute.tmsfm.utility.Constant;
import com.reroute.tmsfm.utility.PageConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.reroute.tmsfm.utility.Constant.ADMIN_ACCOUNT_UUID;

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

        Optional<Organization> parentOrganization;
        Optional<Organization> ownerOrganization;
        Optional<Account> createAuthor;

        organizationDto.setCreatedDate(LocalDateTime.now());
        organizationDto.setChangedDate(null);
        parentOrganization = getParentOrganizationByUuid(organizationDto);
        ownerOrganization = getOwnerOrganizationByUuid(organizationDto);
        createAuthor = getCreateAuthorByUuid(organizationDto);
        log.debug("Подготовлен organizationDto для передачи в mapper {}", organizationDto);
        Organization organization = organizationMapper.toOrganization(organizationDto,
                ownerOrganization.orElse(null),
                parentOrganization.orElse(null),
                createAuthor.orElse(null));
        Organization createdOrganization = organizationRepository.save(organization);
        log.info("В БД добавлена новая организация {}", createdOrganization);
        return Optional.of(organizationMapper.toOrganizationDto(createdOrganization));
    }

    @Override
    @Transactional
    public Optional<OrganizationDto> updateOrganization(OrganizationDto organizationDto) {
        Optional<Organization> organizationForUpdate;
        Optional<Organization> parentOrganization;
        Optional<Organization> ownerOrganization;
        Optional<Account> createAuthor;
        Optional<Account> changeAuthor;

        if (checkOrganizationDtoNull(organizationDto)
                && organizationDto.getId() != null
                && !organizationRepository.existsById(UUID.fromString(organizationDto.getId()))) {
            log.error("Организация с идентификатором {} не существует", organizationDto.getId());
            throw new OrganizationNotFoundException(HttpStatus.BAD_REQUEST,
                    "Организация с идентификатором " + organizationDto.getId() + "не существует");
        }

        if (organizationDto.getId() != null && organizationRepository.existsById(UUID
                .fromString(organizationDto.getId()))) {
            organizationForUpdate = getOrganizationForUpdateByUuid(organizationDto);
            parentOrganization = getParentOrganizationByUuid(organizationDto);
            ownerOrganization = getOwnerOrganizationByUuid(organizationDto);
            createAuthor = getCreateAuthorByUuid(organizationDto);
            changeAuthor = getChangeAuthorByUuid(organizationDto);
            log.debug("Исходное состояние организации {}", organizationForUpdate.toString());
            Organization updatedOrganization = organizationMapperImpl.updateOrganization(organizationDto,
                    ownerOrganization.orElse(null),
                    parentOrganization.orElse(null),
                    createAuthor.orElse(null),
                    changeAuthor.orElse(null),
                    organizationForUpdate.orElse(null));
            log.info("Обновленное состояние организации {}", updatedOrganization.toString());
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
    public List<OrganizationDto> getAllOrganizations(Integer from, Integer size) {
        PageRequest pageRequest = new PageConfig(from, size, Sort.unsorted());
        Page<Organization> searchedOrganization = organizationRepository.findAll(pageRequest);
        List<OrganizationDto> organizationDtoList = searchedOrganization
                .stream()
                .map(organizationMapper::toOrganizationDto)
                .collect(Collectors.toList());
        log.info("Список полученных организаций {}", organizationDtoList);
        return organizationDtoList;
    }

    public Optional<Organization> getParentOrganizationByUuid(OrganizationDto organizationDto) {
        if (organizationDto != null) {
            if (organizationDto.getParent() == null) {
                return Optional.empty();
            } else {
                return organizationRepository.findById(UUID.fromString(organizationDto.getParent()));
            }
        }
        return Optional.empty();
    }

    public Optional<Organization> getOwnerOrganizationByUuid(OrganizationDto organizationDto) {
        if (organizationDto == null) {
            log.error("Не получен DTO организации владельца");
            throw new OrganizationNotFoundException(HttpStatus.NOT_FOUND, "Не получен DTO организации владельца");
        } else {
            if (organizationDto.getOwnerOrganization() == null) {
                organizationDto.setOwnerOrganization(Constant.ADMIN_ORGANIZATION_UUID.toString());
            }
            Optional<Organization> findOrganization = organizationRepository
                    .findById(UUID.fromString(organizationDto.getOwnerOrganization()));
            if (findOrganization.isPresent()) {
                return findOrganization;
            } else {
                log.error("По указанному идентификатору не найдена организация владелец");
                throw new OrganizationNotFoundException(HttpStatus.NOT_FOUND,
                        "По указанному идентификатору не найдена организация владелец");
            }
        }
    }

    public Optional<Account> getChangeAuthorByUuid(OrganizationDto organizationDto) {
        if (organizationDto != null) {
            if (organizationDto.getChangeAuthor() == null) {
                return Optional.empty();
            } else {
                return accountRepository.findById(UUID.fromString(organizationDto.getChangeAuthor()));
            }
        }
        return Optional.empty();
    }

    public Optional<Account> getCreateAuthorByUuid(OrganizationDto organizationDto) {
        if (organizationDto.getCreateAuthor() == null) {
            return accountRepository.findById(ADMIN_ACCOUNT_UUID);
        } else {
            return accountRepository.findById(UUID.fromString(organizationDto.getCreateAuthor()));
        }
    }

    public Optional<Organization> getOrganizationForUpdateByUuid(OrganizationDto organizationDto) {
        if (organizationDto != null) {
            if (organizationDto.getId() == null) {
                return Optional.empty();
            } else {
                return organizationRepository.findById(UUID.fromString(organizationDto.getId()));
            }
        }
        return Optional.empty();
    }

    public boolean checkOrganizationDtoNull(OrganizationDto organizationDto) {
        if (organizationDto == null) {
            log.info("Полученный organizationDto равен null");
            throw new OrganizationDtoBodyIsNull(HttpStatus.BAD_REQUEST, "Полученный organizationDto равен null");
        } else return true;
    }
}
