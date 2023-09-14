package com.bezkoder.spring.jpa.h2.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.h2.model.Price;
import com.bezkoder.spring.jpa.h2.repository.PriceRepository;

//@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PriceController {

    @Autowired
    PriceRepository priceRepository;

    @GetMapping("/prices")
    public ResponseEntity<List<Price>> getAllPrices(@RequestParam(value = "curr", required = false) String curr) {
        try {
            List<Price> prices = new ArrayList<Price>();

            if (curr == null)
                priceRepository.findAll().forEach(prices::add);
            else
                priceRepository.findByCurrContainingIgnoreCase(curr).forEach(prices::add);

            if (prices.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(prices, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPrice")
    public ResponseEntity<List<Price>> findPricesByParameters(
            @RequestParam(value = "brandId", required = true) long brandId,
            @RequestParam(value = "productId", required = true) long productId,
            @RequestParam(value = "date", required = false) String date) {
        try {
            List<Price> prices = new ArrayList<Price>();

            if (!Optional.ofNullable(brandId).isPresent() || !Optional.ofNullable(productId).isPresent() || date == null) {
                priceRepository.findAll().forEach(prices::add);
            } else {
//                LocalDate applicationDate = LocalDate.parse(date);
                Date applicationDate = java.sql.Date.valueOf(date);
                priceRepository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(brandId, productId, applicationDate, applicationDate).forEach(prices::add);
            }
            if (prices.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(prices, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/prices/{id}")
    public ResponseEntity<Price> getPriceById(@PathVariable("id") long id) {
        Optional<Price> priceData = priceRepository.findById(id);

        return priceData.map(price -> new ResponseEntity<>(price, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/prices")
    public ResponseEntity<Price> createPrice(@RequestBody Price price) {
        try {
            Price _price = priceRepository.save(
                    new Price(price.getBrandId(), price.getStartDate(), price.getEndDate(),
                            price.getPriceList(), price.getProductId(), price.getPriority(),
                            price.getPrice(), price.getCurr()));
            return new ResponseEntity<>(_price, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/prices/{id}")
    public ResponseEntity<Price> updatePrice(@PathVariable("id") long id, @RequestBody Price price) {
        Optional<Price> priceData = priceRepository.findById(id);

        if (priceData.isPresent()) {
            Price _price = priceData.get();
            _price.setBrandId(price.getBrandId());
            _price.setStartDate(price.getStartDate());
            _price.setEndDate(price.getEndDate());
            return new ResponseEntity<>(priceRepository.save(_price), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/prices/{id}")
    public ResponseEntity<HttpStatus> deletePrice(@PathVariable("id") long id) {
        try {
            priceRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/prices")
    public ResponseEntity<HttpStatus> deleteAllPrices() {
        try {
            priceRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/prices/published")
    public ResponseEntity<List<Price>> findByProductId() {
        try {
            List<Price> prices = priceRepository.findByProductId(0);

            if (prices.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(prices, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
