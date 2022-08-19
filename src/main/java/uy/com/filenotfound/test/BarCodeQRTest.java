package uy.com.filenotfound.test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uy.com.filenotfound.barcodegenerator.BarbecueBarCodeGenerator;
import uy.com.filenotfound.barcodegenerator.BarCodeTypeEnum;
import uy.com.filenotfound.barcodegenerator.IBarCodeGenerator;
import uy.com.filenotfound.barcodegenerator.ZXingBarCodeGenerator;
import uy.com.filenotfound.qrgenerator.ReportsQRManager;

import java.io.File;
import java.io.FileOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BarCodeQRTest {
    private IBarCodeGenerator ibar;
    private Logger log = LogManager.getLogger(BarCodeQRTest.class);
    Level logInfo;
    @BeforeEach
    public void setUp() {
        ibar = new BarbecueBarCodeGenerator();
        logInfo = Level.forName("INFO", 550);
    }

    @Test
    public void createBarCodes(){
        boolean salida = true;
        String[] l = {"ABC-abc-1234","ABC-abc-1234","ABC-abc-1234","ABC-abc-1234","0101234567890128TEC-IT","Aa-1234","This is a PDF417 by NBR","00001234560000000018","123456789","0123456789012","0123456789012", "1234567890"};
        try{
         /*Precarga de barcodes de ejemplo, adecuados para cada formato*/
         /*
            Type_128 = ABC-abc-1234
	        Type_128A = ABC-abc-1234
	        Type_128B = ABC-abc-1234
	        Type_128C = ABC-abc-1234
	        Type_EAN128 = 0101234567890128TEC-IT
	        Type_3OF9 = Aa-1234
	        Type_PDF417 = This is a PDF417 by NBR
	        Type_SSCC18 = 00001234560000000018
	        Type_USPS = 123456789
	        Type_GLOBAL = 0123456789012 //No lo ubiqué en internet
	        Type_SIN = 0123456789012 //No lo ubiqué en internet
	        Type_2OF5 = 1234567890
         */
         /*Testing de la libreria Barbecue*/
        /*Generando barcodes indicando tamaño y resolucion*/
        BarCodeTypeEnum[] enums = BarCodeTypeEnum.values();
        for(int i = 0; i<=enums.length-1; i ++){
            File f = new File("src/main/resources/barcodesTest/barcode_"+enums[i].toString()+"_A.jpg");
            f.delete(); /*Borramos el fichero si fue generado en un test anterior*/
            ibar.genBarCodeImage(l[i], enums[i], f, 2, 80, 80 , true);
        }

        /*Generando barcodes sin indicar tamaño y resolucion*/
        for(int j = 0; j<=enums.length-1; j ++){
            File f = new File("src/main/resources/barcodesTest/barcode_"+enums[j].toString()+"_B.jpg");
            f.delete(); /*Borramos el fichero si fue generado en un test anterior*/
            ibar.genBarCodeImage(l[j], enums[j], f);
        }
        }catch (Exception ex){
            salida = false;
            log.log(logInfo,"ERROR generando codigos de barra con Barbecue\n" + ex.getLocalizedMessage());
        }
        assertTrue(salida);
        log.log(logInfo,"Código de salida: " + salida +" /target/*.jpg");

        /*Testing de la libreria ZXing*/
        BitMatrix bitMatrix;
        ZXingBarCodeGenerator zxbar = new ZXingBarCodeGenerator();
        try {
            int width = 440;
            int height = 48;
            bitMatrix = new Code128Writer().encode("Hello World !!!", BarcodeFormat.CODE_128,width,height,null);
            File f = new File("src/main/resources/barcodesTest/barcode_zxing_1.png");
            f.delete(); /*Borramos el fichero si fue generado en un test anterior*/
            MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(f));
            File f2 = new File("src/main/resources/barcodesTest/barcode_zxing_2.png");
            f2.delete(); /*Borramos el fichero si fue generado en un test anterior*/
            zxbar.createBarCode_CODE128(new FileOutputStream(f2), l[0], 0, 0, "png");
        } catch (Exception e) {
            log.log(logInfo,"ERROR generando codigos de barra con ZXing\n" + e.getLocalizedMessage());
            salida = false;
        }
        assertTrue(salida);
        log.log(logInfo,"Código de salida: " + salida +" /target/*.jpg");
    }
    @Test
    public void createDecodeQRCodes(){
        boolean salida = true;
        try{
        ReportsQRManager qrManager=new ReportsQRManager();
        File f=new File("src/main/resources/QRTest/qr_test.png");
        FileOutputStream outputStream= new FileOutputStream(f);
        qrManager.createQrCode(outputStream, "Nico", 200, "png");
        log.log(logInfo,"QR Generado sin errores");
        assertEquals(qrManager.decodeQRCode(f), "Nico");
        }catch(Exception ex){
            salida = false;
            log.log(logInfo,"Error en generacion de QR: " + ex.getLocalizedMessage());
        }
        assertTrue(salida);
        }


}