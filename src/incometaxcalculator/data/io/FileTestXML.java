package incometaxcalculator.data.io;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import incometaxcalculator.data.management.Company;
import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.ReceiptAlreadyExistsException;
import incometaxcalculator.exceptions.WrongFileEndingException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

class FileTestXML {

  private static XMLFileReader reader;
  private static XMLInfoWriter writer;
  private static TaxpayerManager manager ;
  
  public FileTestXML() throws IOException, WrongReceiptKindException, WrongReceiptDateException, ReceiptAlreadyExistsException, WrongTaxpayerStatusException, NumberFormatException, WrongFileFormatException, WrongFileEndingException {
    manager = new TaxpayerManager();
    manager.createTaxpayer("Bob", 123456789, "Single", 1111111);   
    reader = new XMLFileReader();    
    writer = new XMLInfoWriter(manager);
  }
  
  
  @Test
  public void testCreateReceiptHappyDay() throws WrongReceiptKindException, WrongReceiptDateException, NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException {
    writer.generateFile(123456789);
    Receipt receipt =reader.createReceipt(1, "11/11/1111", 11, "Travel", "Tree", "Greece", "Ioannina", "Labridou", 11, 123456789);
    int id = receipt.getId();  
    assertEquals(id,1);      
  }
  
  @Test
  public void testCreateReceiptWrongDate() throws  IOException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {
    writer.generateFile(123456789);    
    assertThrows(WrongReceiptDateException.class, () -> {  reader.createReceipt(2, "22", 22, "Travel", "Tree", "Greece", "Ioannina", "Labridou", 22, 123456789);});            
  }
  
  @Test
  public void testCreateReceiptWrongKind() throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException  {
    writer.generateFile(123456789);    
    assertThrows(WrongReceiptKindException.class, () -> {  reader.createReceipt(2, "22/22/2222", 22, "Fun", "Tree", "Greece", "Ioannina", "Labridou", 22, 123456789);});            
  }
  
  @Test
  public void testCompanyInfo() throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {
    BufferedReader inputStream = new BufferedReader(new java.io.FileReader("123456789_INFO.xml"));    
    writer.generateFile(123456789);
    reader.createReceipt(2, "22/22/2222", 22, "Travel", "Tree", "Greece", "Ioannina", "Labridou", 22, 123456789);
    Company company = new Company("Tree", "Greece", "Ioannina", "Labridou", 22);
    Receipt receipt = new Receipt(2,"22/22/2222", 22, "Travel", company); 
    reader.readReceipt(inputStream, 123456789);

    assertTrue(writer.getCompanyName(receipt).equals("Tree") && writer.getCompanyCity(receipt).equals("Ioannina") && writer.getCompanyCountry(receipt).equals("Greece") && writer.getCompanyStreet(receipt).equals("Labridou") && writer.getCompanyNumber(receipt)==22);         

  }
  
  @Test
  public void testReceiptInfo() throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {
    BufferedReader inputStream = new BufferedReader(new java.io.FileReader("123456789_INFO.xml"));    
    writer.generateFile(123456789);
    reader.createReceipt(2, "22/22/2222", 22, "Travel", "Tree", "Greece", "Ioannina", "Labridou", 22, 123456789);
    Company company = new Company("Tree", "Greece", "Ioannina", "Labridou", 22);
    Receipt receipt = new Receipt(2,"22/22/2222", 22, "Travel", company); 
    reader.readReceipt(inputStream, 123456789);
    
    assertTrue(writer.getReceiptAmount(receipt)==22 && writer.getReceiptId(receipt)==2 && writer.getReceiptIssueDate(receipt).equals("22/22/2222") && writer.getReceiptKind(receipt).equals("Travel"));   
  }
  
  @Test
  public void testFileNotFound() throws FileNotFoundException, IOException {
    writer.generateFile(123456789);
    assertThrows(FileNotFoundException.class, () -> { BufferedReader inputStream= new BufferedReader(new java.io.FileReader("123456789_INFO"));});            
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
}
