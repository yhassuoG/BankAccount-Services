package com.bootcamp.reactive.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.bootcamp.reactive.common.CustomerPersonal;
import com.bootcamp.reactive.dto.AccountMovementDto;
import com.bootcamp.reactive.dto.BankAccountDto;
import com.bootcamp.reactive.dto.CustomerDto;
import com.bootcamp.reactive.dto.OperationDto;
import com.bootcamp.reactive.dto.PersonalBankAccountDto;
import com.bootcamp.reactive.entity.BankAccountAhorro;
import com.bootcamp.reactive.entity.BankAccountCorriente;
import com.bootcamp.reactive.entity.BankAccountPlazoFijo;
import com.bootcamp.reactive.service.BankAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest(BankAccountController.class)
public class BankAccountControllerTest {
	
	@MockBean
	BankAccountService service;
	
	@Autowired
	WebTestClient webClient;
	
	@Test
    public void getPersonalBankAccounts_ok(){
		Mono<PersonalBankAccountDto> customer = Mono.just(new PersonalBankAccountDto(null, null, null, 456, null, null, null, null));

		when(service.getPersonalBankAccounts(456)).thenReturn(customer);
		
		Flux<PersonalBankAccountDto> responseBody = webClient.get().uri("/bankaccount/personal/456")
		.exchange()
		.expectStatus().isOk()
		.returnResult(PersonalBankAccountDto.class)
		.getResponseBody();
		
		StepVerifier.create(responseBody)
		.expectSubscription()
		.expectNext(new PersonalBankAccountDto(null, null, null, 456, null, null, null, null))
		.verifyComplete();
		
		
    }
	
	@Test
	public void getMovementsBankAccounts_ok() {
		Mono<AccountMovementDto> customer =Mono.just(new AccountMovementDto(0, null));

		when(service.getMovementsBankAccounts(null)).thenReturn(customer);
		webClient.post().uri("/bankaccount/movimientos")
				.exchange()
				.expectStatus().isOk()
				.returnResult(AccountMovementDto.class)
				.getResponseBody();
				
	}
	
	@Test
	public void saveBankAccountAhorro_ok() {
		Mono<CustomerPersonal> customer =Mono.just(new CustomerPersonal(null, null, null, 0, 0, null, null, null));
		
		Mono<BankAccountDto> bankAccount= Mono.just(new BankAccountDto(null, null, null, null));

		when(service.saveBankAccountAhorro(bankAccount)).thenReturn(customer);
		webClient.post().uri("/bankaccount/ahorro")
				.exchange()
				.expectStatus().isOk()
				.returnResult(CustomerPersonal.class)
				.getResponseBody();
				
	}
	
	@Test
	public void saveBankAccountCorriente_ok() {
		Mono<BankAccountDto> bankAccount= Mono.just(new BankAccountDto(null, null, null, null));
		when(service.saveBankAccountCorriente(bankAccount)).thenReturn(null);
		webClient.post().uri("/bankaccount/corriente")
		.exchange()
		.expectStatus().isOk()
		.returnResult(BankAccountDto.class)
		.getResponseBody();
	}
	
	@Test
	public void saveBankAccountPlazoFijo_ok() {
		CustomerDto customer =  new CustomerDto(null, null, null, 0, 0, null);
		List<BankAccountCorriente> bankcorriente = new ArrayList<>();
		bankcorriente.add(new BankAccountCorriente(null, 0, 0, 0, null));
		BankAccountAhorro ahorro = new BankAccountAhorro(null, 0, 0, 0, null);
		BankAccountPlazoFijo fijo = new BankAccountPlazoFijo(null, 0, 0, 0, null);
		
		Mono<BankAccountDto> bankAccount = Mono.just( new BankAccountDto(customer,bankcorriente,ahorro,fijo));
		
		Mono<CustomerPersonal> cliente= Mono.just(new CustomerPersonal(null, null, null, 0, 0, null, null, null));
		
		when(service.saveBankAccountPlazoFijo(bankAccount)).thenReturn(cliente);
		webClient.post().uri("/bankaccount/plazofijo")
		.exchange()
		.expectStatus().isOk()
		.returnResult(BankAccountDto.class)
		.getResponseBody();
		
			StepVerifier.create(cliente)
			.expectSubscription()
			.expectNext(new CustomerPersonal(null, null, null, 0, 0, null, null, null))
			.verifyComplete();
	}
	
	@Test
	 public void doOperationAhorro_ok(){
		Mono<OperationDto> operation = Mono.just(new OperationDto(null, 0, 0));
		
		Mono<BankAccountAhorro> bankAhorro = Mono.just(new BankAccountAhorro(null, 0, 0, 0, null));
		
		when(service.doOperationAhorro(operation)).thenReturn(bankAhorro);
		
		webClient.post().uri("/bankaccount/ahorro/operacion")
		.exchange()
		.expectStatus().isOk()
		.returnResult(BankAccountAhorro.class)
		.getResponseBody();
		
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
		
		webClient.post().uri("/bankaccount/corriente/operacion")
		.exchange()
		.expectStatus().isOk()
		.returnResult(BankAccountCorriente.class)
		.getResponseBody();
		
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
		
		webClient.post().uri("/bankaccount/plazofijo/operacion")
		.exchange()
		.expectStatus().isOk()
		.returnResult(BankAccountPlazoFijo.class)
		.getResponseBody();
		
		StepVerifier.create(plazoFijo)
		.expectSubscription()
		.expectNext(new BankAccountPlazoFijo(null, 0, 0, 0, null))
		.verifyComplete();
	}
	
	
}






