package uy.com.filenotfound.barcodegenerator;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.linear.code39.Code39Barcode;
import net.sourceforge.barbecue.output.OutputException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class BarbecueBarCodeGenerator implements IBarCodeGenerator {
	static Logger log = LogManager.getLogger(BarbecueBarCodeGenerator.class);
	private Barcode getTypeCode(String data, BarCodeTypeEnum tipo) {
		Barcode out = null;
		try {
			switch (tipo) {
			case Type_128:
				out = BarcodeFactory.createCode128(data);
				break;
	
			case Type_128A:
				out = BarcodeFactory.createCode128A(data);
				break;
			
			case Type_128B:
				out = BarcodeFactory.createCode128B(data);
				break;
				
			case Type_128C:
				out = BarcodeFactory.createCode128C(data);
				break;
				
			case Type_EAN128:
				out = BarcodeFactory.createEAN128(data);
				break;

			case Type_3OF9:
				out = new Code39Barcode(data, false, true);
				break;
				
			case Type_PDF417:
				out = BarcodeFactory.createPDF417(data);
				break;
				
			case Type_SSCC18:
				out = BarcodeFactory.createSSCC18(data);
				break;
				
			case Type_USPS:
				out = BarcodeFactory.createUSPS(data);
				break;
				
			case Type_GLOBAL:
				out = BarcodeFactory.createGlobalTradeItemNumber(data);
				break;
				
			case Type_SIN:
				out = BarcodeFactory.createShipmentIdentificationNumber(data);
				break;

			case Type_2OF5:
				out = BarcodeFactory.createInt2of5(data, false);
				break;
				
			default:
				log.error("Tipo desconocido: " + tipo);
				break;
			}
		} catch (BarcodeException e) {
			log.error("Tipo desconocido", e);;
		}
		return out;
	}
	private void logicGenBarCodeImage(Barcode barcode, File pathImage, int width, int height, int resolution, boolean drawText) {
		int _width = width > 0 ? width : 1;
		int _height = height > 0 ? height : 30;
		int _resolultion = resolution > 0 ? resolution : 72;
		barcode.setBarWidth(_width);
        barcode.setBarHeight(_height);
        barcode.setResolution(_resolultion);
        barcode.setDrawingText(drawText);
        OutputStream out = null;
        try {                
        	out = new BufferedOutputStream(new FileOutputStream(pathImage));
            BufferedImage bi = new BufferedImage(barcode.getWidth(), barcode.getHeight(), BufferedImage.TYPE_BYTE_INDEXED); //BufferedImage(barcode.getWidth(), barcode.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
            Graphics2D g = bi.createGraphics();
            barcode.draw(g, 0, 0);
            bi.flush();
            ImageIO.setUseCache(false);
            ImageIO.write(bi, "jpeg", out);                   
        } catch(OutputException outputexception) {
            log.error("Hubo un error al generar la imagen del código de barras",outputexception);
        } catch (FileNotFoundException e1) {
        	log.error("Hubo un error al generar la imagen del código de barras",e1);
		} catch (IOException e) {
			log.error("Hubo un error al generar la imagen del código de barras",e);
		} finally {
            try {
            	out.flush();
                out.close();
            } catch (IOException ignored) {
            	log.error("Hubo un error al generar la imagen del código de barras",ignored);
            }
        }    
	}
	public void genBarCodeImage(String data, BarCodeTypeEnum tipo, File pathImage, int width, int height, int resolution, boolean drawText) {
		log.info("Generando el código de barras de tipo: " + tipo + " en la ubicación " + pathImage + " con el String: " + data);
		Barcode barcode = getTypeCode(data, tipo);
		if (barcode != null) {
			logicGenBarCodeImage(barcode, pathImage, width, height, resolution, drawText);
		} else {
			log.error("No se genero el codigo de barras");
		}
		
	}
	public void genBarCodeImage(String data, BarCodeTypeEnum tipo, File pathImage) {
		log.info("Generando el código de barras de tipo: " + tipo + " en la ubicación " + pathImage + " con el String: " + data);
		Barcode barcode = getTypeCode(data, tipo);
		if (barcode != null) {
			logicGenBarCodeImage(barcode, pathImage, 0, 0, 0, false);
		} else {
			log.error("No se genero el codigo de barras.");
		}
	}
}
