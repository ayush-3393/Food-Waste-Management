package com.foodwastemanagement.configs;

import com.foodwastemanagement.models.FoodCategory;
import com.foodwastemanagement.models.QuantityUnit;
import com.foodwastemanagement.repositories.FoodCategoryRepository;
import com.foodwastemanagement.repositories.QuantityUnitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final FoodCategoryRepository foodCategoryRepository;
    private final QuantityUnitRepository quantityUnitRepository;

    public DataSeeder(
            FoodCategoryRepository foodCategoryRepository,
            QuantityUnitRepository quantityUnitRepository) {
        this.foodCategoryRepository = foodCategoryRepository;
        this.quantityUnitRepository = quantityUnitRepository;
    }

    private void createFoodCategory(String name, String description) {
        if (!this.foodCategoryRepository.existsByName(name)) {
            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setName(name);
            foodCategory.setDescription(description);
            foodCategoryRepository.save(foodCategory);
        }
    }

    private void createQuantityUnit(String name, String symbol, String description) {
        if (!quantityUnitRepository.existsByName(name)) {
            QuantityUnit quantityUnit = new QuantityUnit();
            quantityUnit.setName(name);
            quantityUnit.setSymbol(symbol);
            quantityUnit.setDescription(description);
            quantityUnitRepository.save(quantityUnit);
        }
    }

    private void seedFoodCategories(){
        createFoodCategory("Cooked Food", "Ready-to-eat cooked food");
        createFoodCategory("Packaged Food", "Sealed and packaged food items");
        createFoodCategory("Fruits", "Fresh fruits");
        createFoodCategory("Vegetables", "Fresh vegetables");
        createFoodCategory("Bakery", "Bread, cakes, pastries and other bakery items");
        createFoodCategory("Dairy", "Milk and dairy products");
        createFoodCategory("Grains", "Rice, wheat, cereals and other grains");
    }
    private void seedQuantityUnits() {
        createQuantityUnit("Milligram", "mg", "Weight in milligrams");
        createQuantityUnit("Gram", "g", "Weight in grams");
        createQuantityUnit("Kilogram", "kg", "Weight in kilograms");
        createQuantityUnit("Quintal", "q", "Weight in quintals");
        createQuantityUnit("Millilitre", "ml", "Volume in millilitres");
        createQuantityUnit("Litre", "L", "Volume in litres");
        createQuantityUnit("Piece", "pc", "Individual food items");
        createQuantityUnit("Plate", "plate", "Food measured by plate");
        createQuantityUnit("Box", "box", "Food measured by box");
        createQuantityUnit("Packet", "pkt", "Food measured by packet");
    }

    @Override
    public void run(String... args) throws Exception {
        seedFoodCategories();
        seedQuantityUnits();
    }

}
