package incometaxcalculator.data.management;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import incometaxcalculator.exceptions.ReceiptAlreadyExistsException;
import incometaxcalculator.exceptions.WrongFileEndingException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;


class TaxpayerManagerTest {

  private static TaxpayerManager manager ;   
  
  public TaxpayerManagerTest() throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException, WrongTaxpayerStatusException {
    manager = new TaxpayerManager();
    manager.createTaxpayer("Bob", 123456789, "Single", 1111111);
   
  }
  
  @Test
  public void containsReceiptTestAddHappyDay() throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException {  
    manager.addReceipt(1, "11/11/1111", 11, "Travel", "Tree", "Greece", "Ioannina", "Labridou", 11, 123456789);
    boolean contains;
    contains = manager.containsReceipt(1);
    assertTrue(contains);
  }
  
  @Test
  public void containsReceiptTestNotExisting()  {   
    boolean contains;
    contains = manager.containsReceipt(3);
    assertTrue(!contains);
  }
  
  @Test
  public void containsReceiptTestRemoveHappyDay() throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException {  
    manager.addReceipt(2, "22/22/2222", 22, "Travel", "Tree", "Greece", "Ioannina", "Labridou", 22, 123456789);
    manager.removeReceipt(2);
    boolean contains;
    contains = manager.containsReceipt(2);
    assertTrue(!contains);
  }
  
  @Test
  public void containsReceiptTestRemoveWrong() throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException {  
    manager.addReceipt(2, "22/22/2222", 22, "Travel", "Tree", "Greece", "Ioannina", "Labridou", 22, 123456789);
    manager.removeReceipt(2);
    boolean contains;
    contains = manager.containsReceipt(2);
    assertFalse(contains);
  }
  
  @Test
  public void addReceiptTestAlreadExists() throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException {  
    manager.addReceipt(2, "22/22/2222", 22, "Travel", "Tree", "Greece", "Ioannina", "Labridou", 22, 123456789);
    
    assertThrows(ReceiptAlreadyExistsException.class, () -> {manager.addReceipt(2, "22/22/2222", 22, "Travel", "Tree", "Greece", "Ioannina", "Labridou", 22, 123456789);});
  }
  
  @Test
  public void loadTaxpayerTestIOException() throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {     
    assertThrows(IOException.class, () -> {  manager.loadTaxpayer("223456789.txt");});
  }
  
  @Test
  public void containsTaxpayerTestHappyDay() {
    assertTrue(manager.containsTaxpayer(123456789));
  }
  
  @Test
  public void containsTaxpayerTestNotFound() {
    assertFalse(!manager.containsTaxpayer(123456789));    
  }

  @Test
  public void containsTaxpayerTestNotExisting() {
    assertTrue(!manager.containsTaxpayer(223456789));    
  }
  
  @Test
  public void removeTaxpayerTestHappyDay() {
    manager.removeTaxpayer(123456789);
    assertTrue(!manager.containsTaxpayer(123456789));    
  }
  
  @Test
  public void removeTaxpayerTestWrong() {
    manager.removeTaxpayer(123456789);
    assertFalse(manager.containsTaxpayer(123456789));    
  }
  
  @Test
  public void createTaxpayerTestWrongStatus() throws WrongTaxpayerStatusException {
    assertThrows(WrongTaxpayerStatusException.class, () -> {  manager.createTaxpayer("Bob", 123456789, "Alone4Ever", 1111111);});   
  }
     
}


