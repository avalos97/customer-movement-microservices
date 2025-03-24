package com.devsu.res.movement_service.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.res.movement_service.adapter.outbound.client.CustomerServiceClient;
import com.devsu.res.movement_service.application.dto.MovementReportDTO;
import com.devsu.res.movement_service.application.dto.MovementResponseDTO;
import com.devsu.res.movement_service.application.dto.AccountStateReportDTO;
import com.devsu.res.movement_service.application.dto.client.CustomerResponseDTO;
import com.devsu.res.movement_service.application.usecase.ReportUseCase;
import com.devsu.res.movement_service.common.constant.ErrorCode;
import com.devsu.res.movement_service.common.mapper.BaseMapper;
import com.devsu.res.movement_service.domain.exception.MovementNotFoundException;
import com.devsu.res.movement_service.domain.model.Account;
import com.devsu.res.movement_service.domain.model.Movement;
import com.devsu.res.movement_service.domain.port.outbound.AccountRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl extends BaseMapper<Movement, MovementResponseDTO> implements ReportUseCase {

    private final AccountRepositoryPort accountRepositoryPort;
    private final CustomerServiceClient customerServiceClient;

@Override
public AccountStateReportDTO generateAccountStateReport(LocalDate initDate, LocalDate endDate, UUID clientId) {
    LocalDateTime init = initDate.atStartOfDay();
    LocalDateTime end = endDate.atTime(23, 59, 59);
    
    List<Account> accounts = accountRepositoryPort.findAccountsWithMovementsByClientAndDate(clientId, init, end);
    List<AccountStateReportDTO.ReportAccountDTO> cuentasReporte = accounts.stream().map(account -> {
        List<MovementReportDTO> movimientosDto = account.getMovimientos().stream()
            .sorted(Comparator.comparing(Movement::getFecha))
            .map(mov -> modelMapper.map(mov, MovementReportDTO.class))
            .collect(Collectors.toList());
        AccountStateReportDTO.ReportAccountDTO accountReport = new AccountStateReportDTO.ReportAccountDTO();
        accountReport.setCuentaId(account.getId());
        accountReport.setNumeroCuenta(account.getNumeroCuenta());
        accountReport.setTipoCuenta(account.getTipoCuenta());
        accountReport.setSaldoInicial(account.getSaldoInicial());
        accountReport.setMovimientos(movimientosDto);
        return accountReport;
    }).collect(Collectors.toList());

    if (accounts.isEmpty()) {
        throw new MovementNotFoundException(ErrorCode.MOVEMENT_NOT_FOUND, "No se encontraron movimientos para el cliente en el rango de fechas especificado");
    }

    CustomerResponseDTO customer = customerServiceClient.getClientById(clientId);

    AccountStateReportDTO report = new AccountStateReportDTO();
    report.setClienteId(clientId);
    report.setClienteNombre(customer.getNombre());
    report.setCuentas(cuentasReporte);
    return report;
}


    @Override
    protected Class<Movement> getEntityClass() {
        return Movement.class;
    }

    @Override
    protected Class<MovementResponseDTO> getDtoClass() {
        return MovementResponseDTO.class;
    }
}