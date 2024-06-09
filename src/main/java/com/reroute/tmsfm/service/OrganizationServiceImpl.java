package com.reroute.tmsfm.service;

import com.reroute.tmsfm.dto.OrganizationDto;
import com.reroute.tmsfm.entity.Account;
import com.reroute.tmsfm.entity.Organization;
import com.reroute.tmsfm.mapper.OrganizationMapperImpl;
import com.reroute.tmsfm.repository.AccountRepository;
import com.reroute.tmsfm.repository.OrganizationRepository;
import com.reroute.tmsfm.utility.PageConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationMapperImpl organizationMapper;
    private final OrganizationRepository organizationRepository;
    private final AccountRepository accountRepository;
    private final AccountServiceImpl accountServiceImpl;
    private final OrganizationMapperImpl organizationMapperImpl;

    @Override
    @Transactional
    public OrganizationDto createOrganization(OrganizationDto organizationDto) {
        if (organizationDto != null) {
            log.info("Получен organizationDto в начальном состоянии {}", organizationDto);
            if (organizationDto.getId() == null) {
                organizationDto.setId(UUID.randomUUID());
            }
            Optional<Organization> parentOrganization;
            Optional<Organization> ownerOrganization;
            Optional<Account> createAuthor;
            if (!organizationRepository.existsById(organizationDto.getId())) {
                organizationDto.setCreatedDate(LocalDateTime.now());
                organizationDto.setChangedDate(null);
                parentOrganization = getParentOrganizationByUuid(organizationDto);
                ownerOrganization = getOwnerOrganizationByUuid(organizationDto);
                createAuthor = getCreateAuthorByUuid(organizationDto);
                log.info("Подготовлен organizationDto для передачи в mapper {}", organizationDto);
                Organization organization = organizationMapper.toOrganization(organizationDto,
                        ownerOrganization.orElse(null),
                        parentOrganization.orElse(null),
                        createAuthor.orElse(null));
                Organization createdOrganization = organizationRepository.save(organization);
                log.info("В БД добавлена новая организация {}", createdOrganization);
                return organizationMapper.toOrganizationDto(createdOrganization);
            } else {
                log.info("Организация с идентификатором {} уже существует", organizationDto.getId());
                return null;
            }
        } else {
            log.info("Полученный organizationDto равен null");
            return null;
        }
    }

    @Override
    @Transactional
    public OrganizationDto updateOrganization(OrganizationDto organizationDto) {
        Optional<Organization> organizationForUpdate;
        Optional<Organization> parentOrganization;
        Optional<Organization> ownerOrganization;
        Optional<Account> createAuthor;
        Optional<Account> changeAuthor;
        if (organizationDto != null) {
            log.info("Получен organizationDto для обновления в начальном состоянии {}", organizationDto);
            if (organizationDto.getId() != null) {
                if (organizationRepository.existsById(organizationDto.getId())) {
                    organizationForUpdate = getOrganizationForUpdateByUuid(organizationDto);
                    parentOrganization = getParentOrganizationByUuid(organizationDto);
                    ownerOrganization = getOwnerOrganizationByUuid(organizationDto);
                    createAuthor = getCreateAuthorByUuid(organizationDto);
                    changeAuthor = getChangeAuthorByUuid(organizationDto);
                    log.info("Исходное состояние организации {}", organizationForUpdate.toString());
                    Organization updatedOrganization = organizationMapperImpl.updateOrganization(organizationDto,
                            ownerOrganization.orElse(null),
                            parentOrganization.orElse(null),
                            createAuthor.orElse(null),
                            changeAuthor.orElse(null),
                            organizationForUpdate.orElse(null));
                    log.info("Обновленное состояние организации {}", updatedOrganization.toString());
                    return organizationMapper.toOrganizationDto(updatedOrganization);
                } else {
                    log.info("Организация с указанным идентификатором не существует: {}",
                            organizationDto.getId().toString());
                }
                return null;
            } else {
                log.info("Для обновления экземпляра организации должен быть указан id. Обновление не выполнено!");
                return null;
            }
        }
        log.info("Не указана организация");
        return null;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public OrganizationDto getOrganizationById(UUID organisationId) {
        if (organisationId != null) {
            Organization foundOrganization = organizationRepository.findById(organisationId).orElse(null);
            log.info("По указанному ID={} найдена организация {}", organisationId, foundOrganization);
            if (foundOrganization != null) {
                OrganizationDto foundOrganizationDto = organizationMapper.toOrganizationDto(foundOrganization);
                log.info("Сформирован foundOrganizationDto={}", foundOrganizationDto.toString());
                return foundOrganizationDto;
            } else {
                log.info("По указанному ID организация не найдена!");
                return null;
            }
        } else {
            log.info("organisationId == null. Запрос не выполнен.");
            return null;
        }
    }

    @Override
    public void deleteOrganizationById(UUID organisationId) {
        if (organisationId != null) {
            Organization foundOrganization = organizationRepository.findById(organisationId).orElse(null);
            log.info("По указанному ID={} найдена организация {}", organisationId, foundOrganization);
            if (foundOrganization != null) {
                organizationRepository.delete(foundOrganization);
                log.info("Удалена организация по указанному Id {}", organisationId);
            } else {
                log.info("По указанному ID организация не найдена! Удаление не выполнено");
            }
        } else {
            log.info("organisationId == null. Удаление не выполнено.");
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
                return organizationRepository.findById(organizationDto.getParent());
            }
        }
        return Optional.empty();
    }

    public Optional<Organization> getOwnerOrganizationByUuid(OrganizationDto organizationDto) {
        if (organizationDto != null) {
            if (organizationDto.getOwnerOrganization() == null) {
                return Optional.empty();
            } else {
                return organizationRepository.findById(organizationDto.getOwnerOrganization());
            }
        }
        return Optional.empty();
    }

    public Optional<Account> getChangeAuthorByUuid(OrganizationDto organizationDto) {
        if (organizationDto != null) {
            if (organizationDto.getChangeAuthor() == null) {
                return Optional.empty();
            } else {
                return accountRepository.findById(organizationDto.getChangeAuthor());
            }
        }
        return Optional.empty();
    }

    public Optional<Account> getCreateAuthorByUuid(OrganizationDto organizationDto) {
        if (organizationDto.getCreateAuthor() == null) {
            return accountRepository.findById(ADMIN_ACCOUNT_UUID);
        } else {
            return accountRepository.findById(organizationDto.getCreateAuthor());
        }
    }

    public Optional<Organization> getOrganizationForUpdateByUuid(OrganizationDto organizationDto) {
        if (organizationDto != null) {
            if (organizationDto.getId() == null) {
                return Optional.empty();
            } else {
                return organizationRepository.findById(organizationDto.getId());
            }
        }
        return Optional.empty();
    }
}
