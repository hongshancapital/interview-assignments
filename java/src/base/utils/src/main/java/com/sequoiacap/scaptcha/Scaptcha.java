package com.sequoiacap.scaptcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.axis.encoding.Base64;

public class Scaptcha
{
    public static final String SCAPTCHA_NAME = "scaptcha-code";

    // 图片的宽度。
    private int width = 200;
    // 图片的高度。
    private int height = 36;
    
    private int fontHeight = 40;
    private int rotate = 20;
    
    private int paddingX = 5;
    private int paddingY = 10;
    
    // 验证码字符个数  
    private int codeCount = 5;  
    // 验证码干扰线数  
    private int lineCount = 30;  
    // 验证码  
    private String code = null;

    // 验证码图片Buffer  
    private BufferedImage buffImg = null;  
  
    private char[] codeSequence = {
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',  
        'I', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',  
        'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9'
    };  
  
    // 生成随机数  
    private Random random = new Random();  
  
    public Scaptcha() {  
        this.createCode();  
    }  
  
    /** 
     *  
     * @param width 
     *            图片宽 
     * @param height 
     *            图片高 
     */  
    public Scaptcha(int width, int height) {  
        this.width = width;  
        this.height = height;  
        this.createCode();  
    }  
  
    /** 
     *  
     * @param width 
     *            图片宽 
     * @param height 
     *            图片高 
     * @param codeCount 
     *            字符个数 
     * @param lineCount 
     *            干扰线条数 
     */  
    public Scaptcha(int width, int height, int codeCount, int lineCount) {  
        this.width = width;  
        this.height = height;  
        this.codeCount = codeCount;  
        this.lineCount = lineCount;  
        this.createCode();  
    }  
  
    public void createCode()
    {
        ImgFontByte imgFont = new ImgFontByte();  

        int codeX = 0;
        codeX = (width - 2 * paddingX) / codeCount;// 每个字符的宽度  

        // 图像buffer  
        buffImg = new BufferedImage(
        	width, height, BufferedImage.TYPE_INT_RGB);  
        Graphics2D g = buffImg.createGraphics();  
  
        // 将图像填充为白色  
        g.setColor(Color.white);  
        g.fillRect(0, 0, width, height);  
  
        // 绘制干扰线  
        for (int i = 0; i < lineCount; i++)
        {  
            int xs = getRandomNumber(width);  
            int ys = getRandomNumber(height);  
            int xe = xs + getRandomNumber(width / 8);  
            int ye = ys + getRandomNumber(height / 8);  
            g.setColor(getRandomColor());  
            g.drawLine(xs, ys, xe, ye);  
        }  
  
        StringBuffer randomCode = new StringBuffer();  
        
        // 随机产生验证码字符  
        for(int i = 0; i < codeCount; i++)
        {  
            String strRand =
            	String.valueOf(codeSequence[
                    random.nextInt(codeSequence.length)]);
            randomCode.append(strRand);

            double angle = getRandomRotate();
            int x = paddingX + i * codeX;
            int y = height - paddingY;
            
            // 设置字体  
            Font font = imgFont.getFont(fontHeight);  
            g.setFont(font);
            
            // 设置字体颜色  
            g.setColor(getRandomColor());

            g.translate(x, y);
            g.rotate(angle);

            // 设置字体位置  
            g.drawString(strRand, 0, 0); 
            
            g.rotate(-angle);
            g.translate(-x, -y);
        }  
        code = randomCode.toString();  
    }  
  
    /** 获取随机颜色 */  
    private Color getRandomColor()
    {  
        int r = getRandomNumber(100);  
        int g = getRandomNumber(100);  
        int b = getRandomNumber(100);  
        return new Color(r, g, b);  
    }  
  
    /** 获取随机旋转角色 */
    private double getRandomRotate()
    {
    	int angle = getRandomNumber(rotate * 2) - rotate;

    	double value = (angle * Math.PI) / 180;
    	
    	return value;
    }
    
    /** 获取随机数 */  
    private int getRandomNumber(int number)
    {  
        return random.nextInt(number);  
    }  
  
    public void write(String path) throws IOException {  
        OutputStream sos = new FileOutputStream(path);  
        this.write(sos);  
    }  
  
    public void write(OutputStream sos) throws IOException {  
        ImageIO.write(buffImg, "png", sos);  
        sos.close();  
    } 
    
    public byte[] writeBytes()
    {
    	try
    	{
    		ByteArrayOutputStream output =
    			new ByteArrayOutputStream();

    		ImageIO.write(buffImg, "png", output);  
    		output.close(); 

    		return output.toByteArray();
    	} catch(Exception e)
    	{ }
    	
    	return null;
    }
  
    public BufferedImage getBuffImg() {  
        return buffImg;  
    }  
  
    public String getCode() {  
        return code;  
    }  
  
    /** 字体样式类 */  
    class ImgFontByte
    {
        public Font getFont(int fontHeight)
        {  
        	Font baseFont = loadFont(getRandomNumber(fontCount()));
        	
        	if (baseFont != null)
        	{
        		return baseFont.deriveFont(Font.PLAIN, fontHeight); 
        	}

        	return new Font("Arial", Font.PLAIN, fontHeight);
        }
    }

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getCodeCount() {
		return codeCount;
	}

	public void setCodeCount(int codeCount) {
		this.codeCount = codeCount;
	}

	public int getLineCount() {
		return lineCount;
	}

	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}

	static private int fontCount()
	{
		return fontRes.length;
	}
	
	static private Font loadFont(int index)
	{
		Font baseFont = fonts[index];

		if (baseFont == null)
		{
    		try
            {  
                baseFont =
                	Font.createFont(Font.TRUETYPE_FONT,  
            			Scaptcha.class.getClassLoader().getResourceAsStream(fontRes[index]));
			
                fonts[index] = baseFont;
            } catch (Exception e)
            { }
		}
		
		return baseFont;
	}
    
	static private String[] fontRes = new String[] {
		"fonts/edosz.ttf",
		"fonts/bs.ttf"
	};
	
	static private Font[] fonts = new Font[fontRes.length];

	public int getFontHeight() {
		return fontHeight;
	}

	public void setFontHeight(int fontHeight) {
		this.fontHeight = fontHeight;
	}

	public int getRotate() {
		return rotate;
	}

	public void setRotate(int rotate) {
		this.rotate = rotate;
	}

	public int getPaddingX() {
		return paddingX;
	}

	public void setPaddingX(int paddingX) {
		this.paddingX = paddingX;
	}

	public int getPaddingY() {
		return paddingY;
	}

	public void setPaddingY(int paddingY) {
		this.paddingY = paddingY;
	}

    public Random getRandom() {
		return random;
	}
}
