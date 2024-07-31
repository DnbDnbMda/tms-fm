package com.reroute.tmsfm.service;

import com.reroute.tmsfm.dto.OrganizationDto;
import com.reroute.tmsfm.entity.Organization;
import com.reroute.tmsfm.exception.OrganizationDtoBodyIsNull;
import com.reroute.tmsfm.exception.OrganizationNotFound;
import com.reroute.tmsfm.exception.OrganizationWithSpecifiedIdAlreadyExists;
import com.reroute.tmsfm.mapper.OrganizationMapper;
import com.reroute.tmsfm.repository.AccountRepository;
import com.reroute.tmsfm.repository.OrganizationRepository;
import com.reroute.tmsfm.utility.Constant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Data
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final OrganizationMapper organizationMapper;

    @Override
    @Transactional
    public OrganizationDto createOrganization(OrganizationDto organizationDto) {
        if (checkOrganizationDtoNull(organizationDto)) {
            if (organizationDto.getId() != null && organizationRepository.existsById(organizationDto.getId())) {
                log.info("Организация с идентификатором {} уже существует", organizationDto.getId());
                throw new OrganizationWithSpecifiedIdAlreadyExists("Организация с идентификатором: "
                        + organizationDto.getId() + " уже существует");
            }
            Organization organization = organizationMapper.toOrganization(organizationDto);
            Organization createdOrganization = organizationRepository.save(organization);
            log.info("id новой организации: {}", createdOrganization.getId());
            log.debug("В БД добавлена новая организация {}", createdOrganization);
            return organizationMapper.toOrganizationDto(createdOrganization);
        }
        throw new OrganizationDtoBodyIsNull("Некорректный OrganizationDto");
    }

    @Override
    @Transactional
    public OrganizationDto updateOrganization(OrganizationDto organizationDto) {
        Optional<Organization> organizationForUpdate;
        if (checkOrganizationDtoNull(organizationDto)
                && organizationDto.getId() != null
                && !organizationRepository.existsById(organizationDto.getId())) {
            log.info("Организация с идентификатором {} не существует", organizationDto.getId());
            throw new OrganizationNotFound("Организация с идентификатором " + organizationDto.getId() +
                    "не существует");
        }

        if (organizationDto.getId() != null
                && organizationRepository.existsById(organizationDto.getId())) {
            organizationForUpdate = getOrganizationByUuid(organizationDto.getId());
            Organization updatedOrganization = organizationMapper.updateOrganization(organizationDto,
                    organizationForUpdate
                            .orElseThrow(() -> new OrganizationNotFound("Организация не найдена")));
            log.info("Организация id: {} обновлена", updatedOrganization.getId());
            log.debug("Обновленное состояние организации {}", updatedOrganization);
            return organizationMapper.toOrganizationDto(updatedOrganization);
        }
        throw new OrganizationDtoBodyIsNull("Некорректный OrganizationDto для обновления");
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public OrganizationDto getOrganizationById(UUID organisationId) {
        if (organisationId != null) {
            Organization foundOrganization = organizationRepository
                    .findById(organisationId).orElseThrow(() -> new OrganizationNotFound("Организация не найдена"));
            log.debug("По указанному ID={} найдена организация {}", organisationId, foundOrganization);
            if (foundOrganization != null) {
                OrganizationDto foundOrganizationDto = organizationMapper.toOrganizationDto(foundOrganization);
                log.debug("Сформирован foundOrganizationDto={}", foundOrganizationDto.toString());
                return foundOrganizationDto;
            } else {
                log.info("По указанному идентификатору {} организация не найдена!", organisationId);
                throw new OrganizationNotFound("По указанному идентификатору = "
                        + organisationId + " организация не найдена");
            }
        } else {
            log.info("Идентификатор организации = null. Запрос не выполнен.");
            throw new OrganizationNotFound("Идентификатор организации = null. Запрос не выполнен.");
        }
    }

    @Override
    @Transactional
    public void deleteOrganizationById(UUID organisationId) {
        if (organisationId != null) {
            if (organizationRepository.existsById(organisationId)) {
                organizationRepository.deleteById(organisationId);
                log.info("Удалена организация по указанному Id {}", organisationId);
            } else {
                log.info("По указанному ID организация не найдена! Удаление не выполнено");
                throw new OrganizationNotFound("По указанному ID организация не найдена! Удаление не выполнено");
            }
        } else {
            log.error("organisationId == null. Удаление не выполнено.");
            throw new OrganizationNotFound("organisationId == null. Удаление не выполнено.");
        }
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public List<OrganizationDto> getAllOrganizations() {
        List<Organization> foundOrganizations = organizationRepository.findAll();
        return foundOrganizations.stream()
                .map(organizationMapper::toOrganizationDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public List<OrganizationDto> getAllOrganizationsPages(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Organization> searchedOrganization = organizationRepository.findAll(pageRequest);
        List<OrganizationDto> organizationDtoList = searchedOrganization
                .stream()
                .map(organizationMapper::toOrganizationDto)
                .collect(Collectors.toList());
        log.debug("Список полученных организаций {}", organizationDtoList);
        return organizationDtoList;
    }

    public Optional<Organization> getOrganizationByUuid(UUID organizationId) {
        UUID organizationIdForSearch = Objects.requireNonNullElse(organizationId, Constant.ADMIN_ORGANIZATION_UUID);
        return organizationRepository.findById(organizationIdForSearch);
    }

    public boolean checkOrganizationDtoNull(OrganizationDto organizationDto) {
        if (organizationDto == null) {
            log.info("Полученный organizationDto равен null");
            throw new OrganizationDtoBodyIsNull("Полученный organizationDto равен null");
        } else return true;
    }
}
