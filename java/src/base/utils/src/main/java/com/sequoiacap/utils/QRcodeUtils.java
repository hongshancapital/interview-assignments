package com.sequoiacap.utils;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRcodeUtils
{	
	public static byte[] QRcode(String text, byte[] logoImage)
		throws IOException, WriterException
	{
		int width = 900; // 二维码图片宽度
		int height = 900; // 二维码图片高度
		
		Hashtable<EncodeHintType, Object> hints =
			new Hashtable<EncodeHintType, Object>();

		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

		//内容所使用字符集编码
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		
		/** 
         * 读取Logo图片 
         */  
		InputStream sbs = new ByteArrayInputStream(logoImage); 
        BufferedImage logo2 = ImageIO.read(sbs); 
		
		
        BitMatrix bitMatrix =
        	new MultiFormatWriter()
        		.encode(text, BarcodeFormat.QR_CODE,
        			width, height, hints);
        
		// 生成二维码
		BufferedImage bufferedImage =
			MatrixToImageWriter.toBufferedImage(bitMatrix);
		
        /** 
         * 读取二维码图片，并构建绘图对象 
         */  
        BufferedImage image = bufferedImage;  
        Graphics2D g = image.createGraphics();  
        
        BufferedImage logo  = makeRoundedCorner(logo2,200);
        /** 
         * 设置logo的大小,本人设置为二维码图片的20%,因为过大会盖掉二维码 
         */  
        int widthLogo = Math.min(
        	logo.getWidth(null), image.getWidth() * 2 / 10);

        int heightLogo = Math.min(
    		logo.getHeight(null), image.getHeight() * 2 / 10);
        
        LogoConfig logoConfig = new LogoConfig(null, 0);
            
        // 计算图片放置位置  
        /** 
         * logo放在中心 
        */  
        int x = (image.getWidth() - widthLogo) / 2; 
        int y = (image.getHeight() - heightLogo) / 2;
        
        g.drawImage(logo, x, y, widthLogo, heightLogo, null);  
        g.dispose();
           
        logo.flush();  
        image.flush();  
        
        ByteArrayOutputStream byteArrayOutputStream =
        	new ByteArrayOutputStream();
        
        ImageIO.write(image, "png", byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
	}
	    
    public static BufferedImage makeRoundedCorner(
    	BufferedImage image, int cornerRadius)
    {
        int w = image.getWidth();
        int h = image.getHeight();
        
        BufferedImage output2 =
        	new BufferedImage(
        		w, h, BufferedImage.TYPE_INT_ARGB);
 
        Graphics2D g = output2.createGraphics();
 
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(
        	RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        
        g.fill(new RoundRectangle2D.Float(
        	0, 0, w, h, 400, 400));
        g.setComposite(AlphaComposite.SrcAtop);

        g.drawImage(image, 0, 0, null); 
        g.dispose();
        
        image = output2;

        BufferedImage output =
        	new BufferedImage(
        		w, h, BufferedImage.TYPE_INT_ARGB);
 
        Graphics2D g2 = output.createGraphics();
 
        // This is what we want, but it only does hard-clipping, i.e. aliasing
        // g2.setClip(new RoundRectangle2D ...)
 
        // so instead fake soft-clipping by first drawing the desired clip shape
        // in fully opaque white with antialiasing enabled...
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(
        	RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        
        g2.fill(new RoundRectangle2D.Float(
        	0, 0, w, h, cornerRadius, cornerRadius));
 
        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
 
        g2.drawRoundRect( 0, 0, w, h, 40, 40); 
        g2.setStroke(new BasicStroke(70));  
//            g.setColor(logoConfig.getBorderColor());  
        g2.drawRect(0, 0, w, h);  
        g2.dispose();
 
        return output;
    }
}


