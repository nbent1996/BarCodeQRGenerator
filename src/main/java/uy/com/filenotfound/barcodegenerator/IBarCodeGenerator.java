package uy.com.filenotfound.barcodegenerator;

import net.sourceforge.barbecue.Barcode;

import java.io.File;

public interface IBarCodeGenerator {

	public void genBarCodeImage(String data, BarCodeTypeEnum tipo, File pathImage);
	public void genBarCodeImage(String data, BarCodeTypeEnum tipo, File pathImage, int width, int height, int resolution, boolean drawText);
}
