package com.dugu.acc.dev.reactive.prog.ex;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * @author basanta hota
 *
 */
public class ReactiveExample {

	private static List<Employee> employees = new ArrayList<>();

	static {
		for (int i = 1; i <= 5; i++) {
			employees.add(new Employee(i, "name" + i));
		}
	}

	public Observable<Employee> getEmployee() {
		return Observable.create(subscriber -> {
			if (!subscriber.isUnsubscribed()) {
				employees.stream().forEach(employee -> {
					subscriber.onNext(employee);
					// forcefully raising exception
					subscriber.onError(new RuntimeException("Exception Raised"));

				});
			}
			subscriber.onCompleted();
		});
	}

	public static void main(String[] args) {

		Observable<Employee> observable = new ReactiveExample().getEmployee();
		observable.subscribe(System.out::println, throwable -> System.out.println(throwable.getMessage()),
				() -> System.out.println("completed"));
	}
}
