package uy.com.filenotfound.ejb;

import uy.com.filenotfound.barcodegenerator.BarCodeTypeEnum;
import uy.com.filenotfound.barcodegenerator.BarbecueBarCodeGenerator;
import uy.com.filenotfound.barcodegenerator.ZXingBarCodeGenerator;
import uy.com.filenotfound.qrgenerator.ReportsQRManager;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

@Stateless
public class BarCodeQRGeneratorApiImpl implements BarCodeQRGeneratorApi{

    private BarbecueBarCodeGenerator barbecuebarcode;
    private ZXingBarCodeGenerator ZXingBarCode;
    private ReportsQRManager qrGenerator;

    @PostConstruct
    private void BarCodeQRGeneratorApiImpl(){
        barbecuebarcode = new BarbecueBarCodeGenerator();
        ZXingBarCode = new ZXingBarCodeGenerator();
        qrGenerator = new ReportsQRManager();
    }

    @Override
    public void genBarCodeImage(String data, BarCodeTypeEnum tipo, File pathImage, int width, int height, int resolution, boolean drawText) {
        barbecuebarcode.genBarCodeImage(data, tipo, pathImage, width, height, resolution, drawText);
    }

    @Override
    public void genBarCodeImage(String data, BarCodeTypeEnum tipo, File pathImage) {
        barbecuebarcode.genBarCodeImage(data, tipo, pathImage);
    }

    @Override
    public String decodeBarCode_CODE128(Object imageFile) {
        InputStream is = (InputStream) imageFile;
        return ZXingBarCode.decodeBarCode_CODE128(is);
    }

    @Override
    public void createBarCode_CODE128(Object outputStream, String content, int widthBarCode, int heightBarCode, String imageFormat) {
        OutputStream os = (OutputStream) outputStream;
        ZXingBarCode.createBarCode_CODE128(os, content, widthBarCode, heightBarCode, imageFormat);
    }

    @Override
    public void createQrCode(Object outputStream, String content, int qrCodeSize, String imageFormat) {
        OutputStream os = (OutputStream) outputStream;
        qrGenerator.createQrCode(os, content, qrCodeSize, imageFormat);
    }

    @Override
    public String decodeQRCode(File imageFile) {
        return qrGenerator.decodeQRCode(imageFile);
    }
}
