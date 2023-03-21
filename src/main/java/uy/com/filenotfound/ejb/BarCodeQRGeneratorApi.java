package uy.com.filenotfound.ejb;

import uy.com.filenotfound.barcodegenerator.BarCodeTypeEnum;

import javax.ejb.Remote;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

@Remote
public interface BarCodeQRGeneratorApi {
    /*Codigos de barra Barbecue*/
    public void genBarCodeImage(String data, BarCodeTypeEnum tipo, File pathImage, int width, int height, int resolution, boolean drawText);
    public void genBarCodeImage(String data, BarCodeTypeEnum tipo, File pathImage);

    /*Codigos de barra ZXing*/
    public String decodeBarCode_CODE128(Object imageFile); /*InputStream*/
    public void createBarCode_CODE128(Object outputStream, String content, int widthBarCode, int heightBarCode, String imageFormat); /*OutputStream*/

    /*Códigos QR*/
    public void createQrCode(Object outputStream, String content, int qrCodeSize, String imageFormat); /*OutputStream*/
    public String decodeQRCode(File imageFile);

}
