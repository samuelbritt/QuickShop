package com.example.quickshop;

public class DatabaseCategories {
    public static final String breads = "Breads";
    public static final String wineCoolers = "Wine Coolers";
    public static final String wine = "Wine";
    public static final String peanutButter = "Peanut Butter";
    public static final String coldBeer = "Cold Beer";
    public static final String cereals = "Cereals";
    public static final String fruitJuices = "Fruit Juices";
    public static final String cannedMilk = "Canned Milk";
    public static final String cannedFruits = "Canned Fruits";
    public static final String coffeeTea = "Coffee & Tea";
    public static final String cannedVegetables = "Canned Vegetables";
    public static final String condiments = "Condiments";
    public static final String oilShortening = "Oils & Shortening";
    public static final String rice = "Rice";
    public static final String dryBeans = "Dry Beans";
    public static final String potatoChips = "Potato Chips";
    public static final String cookies = "Cookies";
    public static final String crackers = "Crackers";
    public static final String snacks = "Snacks";
    public static final String sugars = "Sugars";
    public static final String candies = "Candies";
    public static final String ethnic = "Ethnic Foods";
    public static final String paperProducts = "Paper Products";
    public static final String softDrinks = "Soft Drinks";
    public static final String water = "Water";
    public static final String paperPlates = "Paper Plates";
    public static final String laundryDetergents = "Laundry Detergents";
    public static final String cleaning = "Cleaning Supplies";
    public static final String petFood = "Pet Foods";
    public static final String insecticides = "Insecticides";
    public static final String medicines = "Medicines";
    public static final String toothpaste = "Toothpaste";
    public static final String vitamins = "Vitamins";
    public static final String deodorant = "Deodorant";
    public static final String haircare = "Haircare";
    public static final String babyFood = "Baby Food";
    public static final String cosmetics = "Cosmetics";
    public static final String greetingCards = "Greeting Cards";
    public static final String coldCuts = "Cold Cuts";
    public static final String milk = "Milk";
    public static final String cheese = "Cheese";
    public static final String bacon = "Bacon";
    public static final String naturalFoods = "Natural Foods";
    public static final String frozenDesserts = "Frozen Deserts";
    public static final String frozenBreakfast = "Frozen Breakfast";
    public static final String frozenEntrees = "Frozen Entrees";
    public static final String frozenPizza = "Frozen Pizzas";
    public static final String frozenIceCream = "Frozen Ice Cream";
	
	public static void createCategories(CategoryDAO catDAO) {
	    catDAO.create(new Category(breads, false));
	    catDAO.create(new Category(wineCoolers, false));
	    catDAO.create(new Category(wine, false));
	    catDAO.create(new Category(peanutButter, false));
	    catDAO.create(new Category(coldBeer, false));
	    catDAO.create(new Category(cereals, false));
	    catDAO.create(new Category(fruitJuices, false));
	    catDAO.create(new Category(cannedMilk, false));
	    catDAO.create(new Category(cannedFruits, false));
	    catDAO.create(new Category(coffeeTea, false));
	    catDAO.create(new Category(cannedVegetables, false));
	    catDAO.create(new Category(condiments, false));
	    catDAO.create(new Category(oilShortening, false));
	    catDAO.create(new Category(rice, false));
	    catDAO.create(new Category(dryBeans, false));
	    catDAO.create(new Category(potatoChips, false));
	    catDAO.create(new Category(cookies, false));
	    catDAO.create(new Category(crackers, false));
	    catDAO.create(new Category(snacks, false));
	    catDAO.create(new Category(sugars, false));
	    catDAO.create(new Category(candies, false));
	    catDAO.create(new Category(ethnic, false));
	    catDAO.create(new Category(paperProducts, false));
	    catDAO.create(new Category(softDrinks, false));
	    catDAO.create(new Category(water, false));
	    catDAO.create(new Category(paperPlates, false));
	    catDAO.create(new Category(laundryDetergents, false));
	    catDAO.create(new Category(cleaning, false));
	    catDAO.create(new Category(petFood, false));
	    catDAO.create(new Category(insecticides, false));
	    catDAO.create(new Category(medicines, false));
	    catDAO.create(new Category(toothpaste, false));
	    catDAO.create(new Category(vitamins, false));
	    catDAO.create(new Category(deodorant, false));
	    catDAO.create(new Category(haircare, false));
	    catDAO.create(new Category(babyFood, false));
	    catDAO.create(new Category(cosmetics, false));
	    catDAO.create(new Category(greetingCards, false));
	    catDAO.create(new Category(coldCuts, false));
	    catDAO.create(new Category(milk, false));
	    catDAO.create(new Category(cheese, false));
	    catDAO.create(new Category(bacon, false));
	    catDAO.create(new Category(naturalFoods, false));
	    catDAO.create(new Category(frozenDesserts, false));
	    catDAO.create(new Category(frozenBreakfast, false));
	    catDAO.create(new Category(frozenEntrees, false));
	    catDAO.create(new Category(frozenPizza, false));
	    catDAO.create(new Category(frozenIceCream, false));
	}
}