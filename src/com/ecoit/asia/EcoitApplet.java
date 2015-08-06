package com.ecoit.asia;

import com.ecoit.asia.pdfsigner.SigningModules;
import java.applet.Applet;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import vn.ptit.project.encrypt.FormEncrypt;
import vn.ptit.project.exception.SignatureException;
import vn.ptit.project.signature.FormSigner;
import vn.ptit.project.token.TokenModule;
import vn.ptit.project.token.TokenModules;

public class EcoitApplet extends Applet {
	private static final long serialVersionUID = 1L;
	public static String tempFile;
	public static File signatureLayer;
	
	public EcoitApplet() {
		try {
			/* 69 */UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			/* 71 */e.printStackTrace();
		}
	}
	
	public String signForm(final String original)
  {
/*  83 */     (String)AccessController.doPrivileged(new PrivilegedAction()
    {
      public String run()
      {
        try {
/*  88 */           return FormSigner.signForm(original);
        } catch (SignatureException e) {
/*  90 */           e.printStackTrace(); }
/*  91 */         return null;
      }
    });
  }
	
	public boolean verifyForm(final String original, final String signature, final String base64Cetificate) {
		/* 109 */((Boolean) AccessController.doPrivileged(new PrivilegedAction() {
			
			public Boolean run() {
				
				try {
					
					/* 119 */byte[] b = new BASE64Decoder()
					/* 120 */.decodeBuffer(base64Cetificate);
					/* 121 */InputStream _in = new ByteArrayInputStream(b);
					/* 122 */CertificateFactory certFactory =
					/* 123 */CertificateFactory.getInstance("X.509");
					/* 124 */X509Certificate cert = (X509Certificate) certFactory
					/* 125 */.generateCertificate(_in);
					/* 126 */return Boolean.valueOf(FormSigner.verifyForm(signature, original,
					/* 127 */cert.getPublicKey()));
				} catch (Exception ex) {
					/* 129 */ex.printStackTrace();
				}
				/* 131 */return Boolean.valueOf(false);
			}
		})).booleanValue();
	}
	
	public String getCertificate()
  {
/* 141 */     (String)AccessController.doPrivileged(new PrivilegedAction()
    {


      public String run()
      {

        try
        {

/* 151 */           TokenModule token = TokenModules.newDefaultTokenModule();
/* 152 */           X509Certificate cer = (X509Certificate)token
/* 153 */             .getEncryptCertificate();
/* 154 */           return new BASE64Encoder().encode(cer.getEncoded());
        } catch (Exception e) {
/* 156 */           e.printStackTrace();
        }
/* 158 */         return null;
      }
    });
  }
	
	public String encryptData(final String original, String base64Certificate)
  {
/* 172 */     (String)AccessController.doPrivileged(new PrivilegedAction()
    {


      public String run()
      {

        try
        {

/* 182 */           TokenModule token = TokenModules.newDefaultTokenModule();
/* 183 */           X509Certificate cer = (X509Certificate)token
/* 184 */             .getEncryptCertificate();
/* 185 */           return FormEncrypt.encrypt(original, cer.getPublicKey());
        } catch (Exception e) {
/* 187 */           e.printStackTrace();
        }
/* 189 */         return null;
      }
    });
  }
	
	public String decryptData(final String encryptData)
  {
/* 201 */     (String)AccessController.doPrivileged(new PrivilegedAction()
    {


      public String run()
      {

        try
        {

/* 211 */           TokenModule token = TokenModules.newDefaultTokenModule();
/* 212 */           return FormEncrypt.decrypt(encryptData, 
/* 213 */             token.getEncryptPrivateKey());
        } catch (Exception e) {
/* 215 */           e.printStackTrace();
        }
/* 217 */         return null;
      }
    });
  }
	
	public String response(final String challenge)
  {
/* 231 */     (String)AccessController.doPrivileged(new PrivilegedAction()
    {


      public String run()
      {

        try
        {

/* 241 */           TokenModule token = TokenModules.newDefaultTokenModule();
/* 242 */           return FormEncrypt.authenticate(challenge, 
/* 243 */             token.getEncryptPrivateKey());
        } catch (Exception e) {
/* 245 */           e.printStackTrace();
        }
/* 247 */         return null;
      }
    });
  }
	
	public String signPDF(final String url, final float minX, final float minY, final float maxX, final float maxY)
  {
/* 254 */     (String)AccessController.doPrivileged(new PrivilegedAction()
    {



      public String run()
      {



/* 264 */         System.out.println("================ signPDF ==================");
        try {
/* 266 */           String property = "java.io.tmpdir";
/* 267 */           String tempDir = System.getProperty(property);
          
/* 269 */           JFileChooser signatureFileChooser = new JFileChooser();
/* 270 */           signatureFileChooser
/* 271 */             .setDialogTitle("Ch?n hình ?nh cho ch? ký");
/* 272 */           signatureFileChooser
/* 273 */             .setFileSelectionMode(0);
/* 274 */           int signatureFileChooserReturnVal = signatureFileChooser
/* 275 */             .showOpenDialog(null);
/* 276 */           if (signatureFileChooserReturnVal == 0) {
/* 277 */             File signatureFile = signatureFileChooser
/* 278 */               .getSelectedFile();
            
/* 280 */             File f = new File(tempDir + File.separator + 
/* 281 */               "originalfile.pdf");
/* 282 */             FileUtils.copyURLToFile(new URL(url), f);
            
/* 284 */             String pathSigned = SigningModules.signPDF(f.getPath(), 
/* 285 */               signatureFile, minX, minY, maxX, maxY);
/* 286 */             if (pathSigned == null)
/* 287 */               throw new Exception();
/* 288 */             Path path = Paths.get(pathSigned, new String[0]);
/* 289 */             byte[] data = Files.readAllBytes(path);
/* 290 */             return new BASE64Encoder().encode(data);
          }
          

















/* 310 */           JOptionPane.showMessageDialog(null, 
/* 311 */             "Ký s? ???c h?y b? b?i ng??i s? d?ng!");
        }
        catch (MalformedURLException e) {
/* 314 */           e.printStackTrace();
        } catch (IOException e) {
/* 316 */           e.printStackTrace();
        } catch (Exception e) {
/* 318 */           e.printStackTrace();
        }
        

/* 322 */         System.out.println("================ End signPDF ==================");
/* 323 */         return "";
      }
    });
  }
	
	public String signPDF(final String url, final float minX, final float minY, final float maxX, final float maxY, final String layer1)
  {
/* 331 */     (String)AccessController.doPrivileged(new PrivilegedAction()
    {



      public String run()
      {



/* 341 */         System.out.println("================ signPDF ==================");
        try {
/* 343 */           String property = "java.io.tmpdir";
/* 344 */           String tempDir = System.getProperty(property);
          
/* 346 */           File f = new File(tempDir + File.separator + 
/* 347 */             "originalfile.pdf");
/* 348 */           FileUtils.copyURLToFile(new URL(url), f);
          
/* 350 */           File signatureLayer1 = new File(tempDir + File.separator + 
/* 351 */             "layer1.png");
/* 352 */           FileUtils.copyURLToFile(new URL(layer1), signatureLayer1);
          
/* 354 */           String pathSigned = SigningModules.signPDF(f.getPath(), 
/* 355 */             signatureLayer1, minX, minY, maxX, maxY);
/* 356 */           if (pathSigned == null)
/* 357 */             throw new Exception();
/* 358 */           Path path = Paths.get(pathSigned, new String[0]);
          
/* 360 */           byte[] data = Files.readAllBytes(path);
/* 361 */           return new BASE64Encoder().encode(data);
        } catch (MalformedURLException e) {
/* 363 */           e.printStackTrace();
        } catch (IOException e) {
/* 365 */           e.printStackTrace();
        } catch (Exception e) {
/* 367 */           e.printStackTrace();
        }
        

/* 371 */         System.out.println("================ End signPDF ==================");
/* 372 */         return "";
      }
    });
  }
	
	public String signPDF(final String postURL, final String url, final String layer1, final int isSigned)
  {
/* 379 */     (String)AccessController.doPrivileged(new PrivilegedAction()
    {



      public String run()
      {


/* 388 */         System.out.println("================ signPDF ==================");
        try {
/* 390 */           String property = "java.io.tmpdir";
/* 391 */           String tempDir = System.getProperty(property);
          
/* 393 */           File f = new File(tempDir + File.separator + 
/* 394 */             Calendar.getInstance().getTimeInMillis() + FilenameUtils.getBaseName(url) + "." + FilenameUtils.getExtension(url));
/* 395 */           FileUtils.copyURLToFile(new URL(url), f);
          
/* 397 */           File signatureLayer1 = new File(tempDir + File.separator + 
/* 398 */             "layer1.png");
/* 399 */           FileUtils.copyURLToFile(new URL(layer1), signatureLayer1);
          

/* 402 */           EcoitApplet.tempFile = f.getAbsolutePath();
/* 403 */           EcoitApplet.signatureLayer = signatureLayer1;
/* 404 */           EcoitApplet.isSigned = isSigned;
/* 405 */           EcoitApplet.urlPost = postURL;
          
/* 407 */           PDDocument document = null;
/* 408 */           File input = new File(EcoitApplet.tempFile);
/* 409 */           document = PDDocument.load(input);
/* 410 */           PrintTextLocations printer = new PrintTextLocations();
/* 411 */           List allPages = document.getDocumentCatalog().getAllPages();
          
/* 413 */           for (int i = 0; i < allPages.size(); i++) {
/* 414 */             EcoitApplet.page = i;
/* 415 */             PDPage page = (PDPage)allPages.get(i);
/* 416 */             PDStream contents = page.getContents();
/* 417 */             if (contents != null) {
/* 418 */               printer.processStream(page, page.findResources(), page.getContents().getStream());
            }
          }
        }
        catch (MalformedURLException e) {
/* 423 */           e.printStackTrace();
        } catch (IOException e) {
/* 425 */           e.printStackTrace();
        } catch (Exception e) {
/* 427 */           e.printStackTrace();
        }
        
/* 430 */         System.out.println("================ End signPDF ==================");
/* 431 */         return EcoitApplet.urlResult;
      }
    });
  }
	
	/* 437 */public static String base64Data = "";
	/* 438 */public static int isSigned = 1;
	/* 439 */public static int page = 0;
	/* 440 */public static String urlPost = "";
	/* 441 */public static String urlResult = "";
	
	public void sign(String fileName) {
		try {
			/* 445 */EcoitApplet ecoitApplet = new EcoitApplet();
			/* 446 */String url = ecoitApplet.signPDF("http://hanoi.dtt.vn/moh_cchc_2013-portlet/applet/uploadfile.jsp",
			/* 447 */"http://hanoi.dtt.vn/documents/10180/103822/moh_tailieu_1430967319570.pdf",
			        "http://hanoi.dtt.vn/moh_cchc_2013-portlet/applet/nguyenthanhphong@vfa.gov.vn.png", 1);
			/* 448 */System.out.println(url);
		} catch (Exception ex) {
			/* 450 */ex.printStackTrace();
		}
	}
	
	public static void main(String[] agrs) {
		/* 480 */new EcoitApplet().sign("test");
	}
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: com.ecoit.asia.EcoitApplet Java Class Version: 7 (51.0)
 * JD-Core Version: 0.7.1
 */