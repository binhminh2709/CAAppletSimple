package com.ecoit.asia;

import java.io.IOException;

import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

import com.ecoit.asia.pdfsigner.SigningModules;

public class PrintTextLocations extends PDFTextStripper {
	public PrintTextLocations() throws IOException {
		super.setSortByPosition(true);
	}
	
	@Override
	protected void processTextPosition(TextPosition text) {
		try {
			if (("#".equals(text.getCharacter())) && (text.getFontSize() == 1.0F)) {
				float x = text.getTextPos().getXPosition();
				float y = text.getTextPos().getYPosition();
				String pathSigned = "";
				if (EcoitApplet.isSigned == 1) {
					pathSigned = SigningModules.signPDF(EcoitApplet.tempFile, EcoitApplet.signatureLayer, x + 20.0F, y - 20.0F, x + 190.0F, y - 70.0F);
				} else {
					pathSigned = SigningModules.signPDF(EcoitApplet.tempFile, EcoitApplet.signatureLayer, x - 25.0F, y, x + 65.0F, y - 90.0F);
				}
				
				if (pathSigned == null) {
					throw new Exception();
				}
				EcoitApplet.urlResult = new MultipartFileUploader().doUpload(EcoitApplet.urlPost, pathSigned);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: com.ecoit.asia.PrintTextLocations Java Class Version: 7
 * (51.0) JD-Core Version: 0.7.1
 */