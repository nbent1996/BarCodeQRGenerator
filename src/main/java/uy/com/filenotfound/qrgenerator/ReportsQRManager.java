package uy.com.filenotfound.qrgenerator;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Reader;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;


public class ReportsQRManager {
	public ReportsQRManager() {
	
	}
	/**
	 * Call this method to create a QR-code image. You must provide the
	 * OutputStream where the image data can be written.
	 * 
	 * @param outputStream
	 *            The OutputStream where the QR-code image data is written.
	 * @param content
	 *            The string that should be encoded with the QR-code.
	 * @param qrCodeSize
	 *            The QR-code must be quadratic. So this is the number of pixel
	 *            in width and height.
	 * @param imageFormat
	 *            The image format in which the image should be rendered. As
	 *            Example 'png' or 'jpg'. See @javax.imageio.ImageIO for more
	 *            information which image formats are supported.
	 * @throws Exception
	 *             If an Exception occur during the create of the QR-code or
	 *             while writing the data into the OutputStream.
	 */
	public void createQrCode(OutputStream outputStream, String content, int qrCodeSize, String imageFormat)  {
		try {
			// Create the ByteMatrix for the QR-Code that encodes the given
			// String.
			Hashtable hints = new Hashtable();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hints);
			
			// Make the BufferedImage that are to hold the QRCode
			int matrixWidth = bitMatrix.getWidth();

			BufferedImage image = new BufferedImage(matrixWidth, matrixWidth,BufferedImage.TYPE_INT_RGB);
			image.createGraphics();

			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, matrixWidth, matrixWidth);

			// Paint and save the image using the bitMatrix
			graphics.setColor(Color.BLACK);
			for (int i = 0; i < matrixWidth; i++) {
				for (int j = 0; j < matrixWidth; j++) {
					// true es black
					if (bitMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			ImageIO.write(image, imageFormat, outputStream);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * Dado un archivo de imagen con un QR,
	 * se devuelve un String con el
	 * contenido, luego de decodificar el QR
	 */
	public String decodeQRCode(File imageFile) {
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
		Reader reader = new QRCodeReader();
		Result result = null;
		try {
			result = reader.decode(bitmap);
		} catch (ReaderException e) {
			return "reader error";
		}
		return result.getText();

	}
}
