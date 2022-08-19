package uy.com.filenotfound.barcodegenerator;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Reader;
import com.google.zxing.oned.Code128Writer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ZXingBarCodeGenerator {
    /**
     * Dada una imagen con un codigo de barras en formato CODE128,
     * se devuelve un String con el
     * contenido, producto de la decodificacion
     * del codigo de barras

     * @param imageFile
     * @return
     */
    public String decodeBarCode_CODE128(InputStream imageFile) {
        BufferedImage image;
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e1) {
            return "io outch";
        }

        // creating luminance source
        LuminanceSource lumSource = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(lumSource));

        // barcode decoding
        //Reader reader = new QRCodeReader();
        Reader reader = new Code128Reader();

        Result result = null;
        try {
            result = reader.decode(bitmap);
        } catch (ReaderException e) {
            return "reader error";
        }

        return result.getText();

    }


    /**
     * Dado el outputStream destino de la imagen, se crea
     * codigo de barras en formato CODE128.
     *
     * @param outputStream
     * 			Destino de la imagen
     * @param content
     * 			String con el contenido a codificar en el codigo de barras
     * @param widthBarCode
     * 			Ancho del codigo de barras. Si se pasa 0, se toma por defecto 440
     * @param heightBarCode
     * 			Altura del codigo de barras. Si se pasa 0, se toma por defecto 48
     * @param imageFormat
     * 			Formato de la imagen destino. Por ejemlo "jpg" o "png"
     */
    public void createBarCode_CODE128(OutputStream outputStream, String content, int widthBarCode, int heightBarCode, String imageFormat)  {
        int width = widthBarCode == 0 ? 440 : widthBarCode;
        int height = heightBarCode == 0 ? 48 : heightBarCode;
        BitMatrix bitMatrix;
        try {
            bitMatrix = new Code128Writer().encode(content,BarcodeFormat.CODE_128,width,height,null);
            MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, outputStream);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
