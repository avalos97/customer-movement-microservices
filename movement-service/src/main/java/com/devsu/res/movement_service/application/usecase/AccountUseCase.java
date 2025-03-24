package com.devsu.res.movement_service.application.usecase;

import java.util.List;
import java.util.UUID;

import com.devsu.res.movement_service.application.dto.AccountRequestDTO;
import com.devsu.res.movement_service.application.dto.AccountResponseDTO;
import com.devsu.res.movement_service.application.dto.MovementRequestDTO;

public interface AccountUseCase {
    AccountResponseDTO createAccount(AccountRequestDTO requestDTO);
    AccountResponseDTO updateAccount(UUID id, AccountRequestDTO requestDTO);
    void updateAccountStatusByClientId(UUID id, Boolean status);
    AccountResponseDTO getAccountById(UUID id);
    void deleteAccount(UUID id);
    List<AccountResponseDTO> getAllAccount();
    AccountResponseDTO registerMovement(UUID cuentaId, MovementRequestDTO requestDTO);
    void deleteAccountByClientId(UUID clienteId);


}