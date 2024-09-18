package io.BookingSystem.BusBookingSystem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BusBookingSystemApplicationTests {

	Calculator calculatorTest=new Calculator();

	@Test
	public void test_add() {
		//givevn
		int a=10,b=20,expected=30;

		//when
		int sum= calculatorTest.add(a,b);
		//then
		Assertions.assertEquals(expected,sum);
		System.out.println("Hello anil kumar Billupati");
	}

	@Test
	public void should_returnNotEquals_when_addTwoNumber(){
		// given
		int firstNumber = 10;
		int secondNumber = 20;
		int expected = 40;

		// when
		int actual = calculatorTest.add(firstNumber, secondNumber);

		// then
		Assertions.assertNotEquals(expected, actual);
	}




	class Calculator{
		int add(int a, int b){
			return  a+b;
		}
	}
}
