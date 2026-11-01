
## 1. Checkout: Overview

```xpath
//span[contains(text(),'Checkout')]
//div[text()='QTY']
//div[text()='Description']
//div[contains(text(),'Backpack')]
//div[@class='cart_list']
//div[contains(text(),'tablet')]
//div[contains(text(),'Backpack')]/ancestor::div[@class='cart_item']/descendant::div[text()='1']
//div[contains(text(),'Backpack')]/ancestor::div[@class='cart_item_label']/descendant::div[@class='inventory_item_price']
//div[contains(text(),'Bike Light')]
//div[contains(text(),'Water-resistant')]
//div[contains(text(),'Bike Light')]/ancestor::div[@class='cart_item']/descendant::div[text()='1']
//div[contains(text(),'Bike Light')]/ancestor::div[@class='cart_item_label']/descendant::div[@class='inventory_item_price']
//div[contains(text(),'Payment')]
//div[contains(text(),'SauceCard')]
//div[contains(text(),'Shipping')]
//div[contains(text(),'Free Pony')]
//div[contains(text(),'Price')]
//div[contains(text(),'Item')]
//div[contains(text(),'Tax')]
//div[contains(text(),'Total:')]
//button[text()='Cancel']
//button[text()='Finish']
```

## 2. Checkout: Complete!

```xpath
//span[contains(text(),'Checkout')]
//h2[contains(text(),'Thank')]
//div[contains(text(),'Your order')]
//button[@id='back-to-products']
//button[contains(@id,'generate')]
```

## 3. Tìm tất cả locator trong đường dẫn sau

**Website:** [Sauce Labs - Request Demo](https://saucelabs.com/request-demo)

```xpath
//div[@class='v2-rd-form-card']
//h3[contains(text(),'demo')]
//input[contains(@name,'Email')]
//input[@id='Company']
//select[@id='Use-Case']
//textarea[@id='Comments']
//button[contains(text(),'request')]
//p[contains(text(),'pick a time')]
```
