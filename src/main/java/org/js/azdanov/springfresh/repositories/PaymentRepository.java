package org.js.azdanov.springfresh.repositories;

import org.js.azdanov.springfresh.models.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {}
