package com.bootcamp.reactive.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.bootcamp.reactive.common.CustomerPersonal;
import com.bootcamp.reactive.dto.AccountMovementDto;
import com.bootcamp.reactive.dto.BankAccountDto;
import com.bootcamp.reactive.dto.CustomerDto;
import com.bootcamp.reactive.dto.OperationDto;
import com.bootcamp.reactive.dto.PersonalBankAccountDto;
import com.bootcamp.reactive.entity.BankAccountAhorro;
import com.bootcamp.reactive.entity.BankAccountCorriente;
import com.bootcamp.reactive.entity.BankAccountPlazoFijo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest(BankAccountService.class)
public class BankAccount {
	
	@MockBean
	BankAccountService service;
	
	@Test
    public void getPersonalBankAccounts_ok(){
		Mono<PersonalBankAccountDto> customer = Mono.just(new PersonalBankAccountDto(null, null, null, 456, null, null, null, null));

		when(service.getPersonalBankAccounts(456)).thenReturn(customer);
		
		StepVerifier.create(customer)
		.expectSubscription()
		.expectNext(new PersonalBankAccountDto(null, null, null, 456, null, null, null, null))
		.verifyComplete();
		
		
    }
	
	@Test
	public void getMovementsBankAccounts_ok() {
		Mono<AccountMovementDto> customer =Mono.just(new AccountMovementDto(0, null));

		when(service.getMovementsBankAccounts(null)).thenReturn(customer);
		
		StepVerifier.create(customer)
		.expectSubscription()
		.expectNext(new AccountMovementDto(0, null))
		.verifyComplete();
				
	}
	
	@Test
	public void saveBankAccountAhorro_ok() {
		Mono<CustomerPersonal> customer =Mono.just(new CustomerPersonal(null, null, null, 0, 0, null, null, null));
		
		Mono<BankAccountDto> bankAccount= Mono.just(new BankAccountDto(null, null, null, null));

		when(service.saveBankAccountAhorro(bankAccount)).thenReturn(customer);
		
		StepVerifier.create(customer)
		.expectSubscription()
		.expectNext(new CustomerPersonal(null, null, null, 0, 0, null, null, null))
		.verifyComplete();
	}
	
	@Test
	public void saveBankAccountCorriente_ok() {
		Mono<BankAccountDto> bankAccount= Mono.just(new BankAccountDto(null, null, null, null));
		when(service.saveBankAccountCorriente(bankAccount)).thenReturn(null);
		
		StepVerifier.create(bankAccount)
		.expectSubscription()
		.expectNext(new BankAccountDto(null, null, null, null))
		.verifyComplete();
	}
	
	@Test
	public void saveBankAccountPlazoFijo_ok() {
	
	CustomerDto customer =  new CustomerDto(null, null, null, 0, 0, null);
	List<BankAccountCorriente> bankcorriente = new ArrayList<>();
	bankcorriente.add(new BankAccountCorriente(null, 0, 0, 0, null));
	BankAccountAhorro ahorro = new BankAccountAhorro(null, 0, 0, 0, null);
	BankAccountPlazoFijo fijo = new BankAccountPlazoFijo(null, 0, 0, 0, null);
	
	Mono<BankAccountDto> custmer = Mono.just( new BankAccountDto(customer,bankcorriente,ahorro,fijo));
	
	Mono<CustomerPersonal> cliente= Mono.just(new CustomerPersonal(null, null, null, 0, 0, null, null, null));
	
	when(service.saveBankAccountPlazoFijo(custmer)).thenReturn(cliente);
		
		StepVerifier.create(cliente)
		.expectSubscription()
		.expectNext(new CustomerPersonal(null, null, null, 0, 0, null, null, null))
		.verifyComplete();
		
	}
	
	@Test
	public void doOperationAhorro() {
		
		Mono<OperationDto> operation = Mono.just(new OperationDto(null, 0, 0));
		
		Mono<BankAccountAhorro> bankAhorro = Mono.just(new BankAccountAhorro(null, 0, 0, 0, null));
		
		when(service.doOperationAhorro(operation)).thenReturn(bankAhorro);
		
		StepVerifier.create(bankAhorro)
		.expectSubscription()
		.expectNext(new BankAccountAhorro(null, 0, 0, 0, null))
		.verifyComplete();
	}
	
	@Test
	public void doOperationCorriente() {
		Mono<BankAccountCorriente> corrient= Mono.just(new BankAccountCorriente(null, 0, 0, 0, null));
		
		Mono<OperationDto> operation = Mono.just(new OperationDto(null, 0, 0));
		
		when(service.doOperationCorriente(operation)).thenReturn(corrient);
		
		StepVerifier.create(corrient)
		.expectSubscription()
		.expectNext(new BankAccountCorriente(null, 0, 0, 0, null))
		.verifyComplete();
	}
	
	@Test
	public void doOperationPlazoFijo(){
		Mono<BankAccountPlazoFijo> plazoFijo= Mono.just(new BankAccountPlazoFijo(null, 0, 0, 0, null));
		
		Mono<OperationDto> operation = Mono.just(new OperationDto(null, 0, 0));
		
		when(service.doOperationPlazoFijo(operation)).thenReturn(plazoFijo);
		
		StepVerifier.create(plazoFijo)
		.expectSubscription()
		.expectNext(new BankAccountPlazoFijo(null, 0, 0, 0, null))
		.verifyComplete();
	}
	

}
