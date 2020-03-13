package server.commom.utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

/**
 * The type Images util.
 *
 * @author tieminPan
 * @Desc: 图片处理工具类
 * @Date 2018 /10/22 10:50
 */
public class ImagesUtil {
    /**
     * 字体名字
     */
    public static final String FONT_NAME = "微软雅黑";


    /**
     * 添加多个文字水印
     *
     * @param primitiveImg 背景图片
     * @param markText     水印文字
     * @param spacing      文字间隔
     * @param angle        旋转角度
     * @param alpha        透明度
     * @param fontSize     字体大小
     * @param fontStyle    字体样式 Font.BOLD
     * @param fontColor    字体颜色
     * @return buffered image
     */
    public static BufferedImage watermarkText(BufferedImage primitiveImg, String markText, Integer spacing, Integer angle, Float alpha, Integer fontSize, Integer fontStyle, Color fontColor) {
        /**
         * 原始图片文件
         */
        Image artworkItem = null;
        /**
         * 合成后图片
         */
        BufferedImage bufferedImage = null;

        /**
         * 1.读取原始文件
         */
        artworkItem = primitiveImg;
        int width = artworkItem.getWidth(null);
        int height = artworkItem.getHeight(null);
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        /**
         * 2.创建Java绘图工具对象
         */
        Graphics2D g = bufferedImage.createGraphics();

        /**
         * 3.使用绘图工具对象将原图绘制到缓存图片对象
         */
        g.drawImage(artworkItem, 0, 0, width, height, null);


        /**
         * 4.使用绘图工具对象将水印（文字/图片）绘制到缓存图片
         */
        g.setFont(new Font(FONT_NAME, fontStyle, fontSize));
        g.setColor(fontColor);
        /**
         * 5.文字水印宽度
         */
        int width1 = fontSize * getTextLength(markText);

        /**
         * 6.文字水印高度
         */
        int height1 = fontSize;

        /**
         * 7.透明度的设置
         */
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        /**
         * 8.旋转图片
         */
        g.rotate(Math.toRadians(angle), bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);

        /**
         * 9.设置水印的坐标
         */
        int x = -width / 2;
        int y;

        /**
         * 10.循环设置水印文字
         *
         */

        while (x < width * 1.5) {
            y = -height / 2;
            while (y < height * 1.5) {
                g.drawString(markText, x, y);
                y += height1 + spacing;
            }
            x += width1 + spacing;
        }

        /**
         * 11.释放工具
         */
        g.dispose();
        return bufferedImage;
    }

    /**
     * 添加单个文字水印
     *
     * @param primitiveImg 背景图
     * @param markText     文字
     * @param angle        倾斜度
     * @param alpha        透明度
     * @param fontSize     字体大小
     * @param fontStyle    字体样式
     * @param x            背景图横坐标
     * @param y            背景图纵坐标
     * @param fontColor    字体颜色
     * @return buffered image
     */
    public static BufferedImage watermarkText(BufferedImage primitiveImg, String markText, Integer angle, Float alpha, Integer fontSize, Integer fontStyle, Integer x, Integer y, Color fontColor) {
        /**
         * 原始图片文件
         */
        Image artworkItem = null;
        /**
         * 合成后图片
         */
        BufferedImage bufferedImage = null;

        /**
         * 1.读取原始文件
         */
        artworkItem = primitiveImg;
        int width = artworkItem.getWidth(null);
        int height = artworkItem.getHeight(null);
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        /**
         * 2.创建Java绘图工具对象
         */
        Graphics2D g = bufferedImage.createGraphics();

        /**
         * 3.使用绘图工具对象将原图绘制到缓存图片对象
         */
        g.drawImage(artworkItem, 0, 0, width, height, null);


        /**
         * 4.使用绘图工具对象将水印（文字/图片）绘制到缓存图片
         */
        g.setFont(new Font(FONT_NAME, fontStyle, fontSize));
        g.setColor(fontColor);
        /**
         * 获取文字水印的宽度和高度值
         */
        int width1 = fontSize * getTextLength(markText);
        int height1 = fontSize;


        /**
         * 计算原图和文字水印的宽度和高度之差
         * 宽度之差
         */
        int widthDiff = width - width1;
        /**
         * 高度之差
         */
        int heightDiff = height - height1;

        /**
         * 保证文字水印在图片内显示显示
         */
        if (x > widthDiff) {
            x = widthDiff;
        }
        if (y > heightDiff) {
            y = heightDiff;
        }

        /**
         * 水印透明度的设置
         */
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        /**
         * 旋转图片 文字倾斜
         */
        g.rotate(Math.toRadians(angle), bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);

        //绘制文字
        g.drawString(markText, x, y + fontSize);
        /**
         * 释放工具
         */
        g.dispose();
        return bufferedImage;
    }

    /**
     * 添加图片水印
     *
     * @param primitiveImg   原始图片
     * @param artworkItemImg 水印图片
     * @param x              水印图片在原始图片横坐标位置
     * @param y              水印图片在原始图片纵坐标位置
     * @param alpha          水印图片透明度
     * @return buffered image
     */
    public static BufferedImage watermark(BufferedImage primitiveImg, BufferedImage artworkItemImg, Integer x, Integer y, Float alpha) {
        /**
         * 原始图片文件
         */
        Image artworkItem = null;
        /**
         * 水印图片文件
         */
        Image artworkItemImgItem = null;
        /**
         * 合成后图片
         */
        BufferedImage bufferedImage = null;

        /**
         * 1.读取原始文件
         */
        artworkItem = primitiveImg;
        int width = artworkItem.getWidth(null);
        int height = artworkItem.getHeight(null);
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        /**
         * 2.创建Java绘图工具对象
         */
        Graphics2D g = bufferedImage.createGraphics();

        /**
         * 3.使用绘图工具对象将原图绘制到缓存图片对象
         */
        g.drawImage(artworkItem, 0, 0, width, height, null);

        /**
         * 4.创建图片水印文件,使用ImageIO流来读取文件
         */
        artworkItemImgItem = artworkItemImg;

        /**
         * 5.分析图片水印图片文件的高度和宽度
         */
        int width1 = artworkItemImgItem.getWidth(null);
        int height1 = artworkItemImgItem.getHeight(null);

        /**
         * 6.计算原图与水印图片的宽度、高度之差
         */
        int widthDiff = width - width1;
        int heightDiff = height - height1;
        /**
         * 7.保证图片水印在图片可视范围内
         */
        if (x > widthDiff) {
            x = widthDiff;
        }
        if (y > heightDiff) {
            y = heightDiff;
        }

        /**
         * 8.水印透明度的设置
         */
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        /**
         * 9.绘制图片水印
         */
        g.drawImage(artworkItemImgItem, x, y, null);
        /**
         * 10.释放工具
         */
        g.dispose();
        return bufferedImage;
    }


    /**
     * 处理文字水印的中英文字符的宽度转换
     *
     * @param text 字符串
     * @return text length
     */
    public static int getTextLength(String text) {
        int length = text.length();
        for (int i = 0; i < text.length(); i++) {
            String s = String.valueOf(text.charAt(i));
            if (s.getBytes().length > 1) {
                /**
                 * 中文字符
                 */
                length++;
            }
        }
        /**
         * 中文和英文字符的转换
         */
        length = length % 2 == 0 ? length / 2 : length / 2 + 1;
        return length;
    }


    /**
     * 图像裁剪
     *
     * @param file   原图片
     * @param x      坐标
     * @param y      坐标
     * @param width  宽度
     * @param height 高度
     * @return buffered image
     */
    public static BufferedImage cutImage(File file, int x, int y, int width, int height) {
        /**
         * 读取图片文件，建立文件输入流
         */
        FileInputStream fis = null;
        ImageInputStream iis = null;
        BufferedImage bi = null;
        try {
            fis = new FileInputStream(file);

            /**
             * 创建图片文件流的迭代器
             */
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");
            ImageReader reader = it.next();
            /**
             * 获取图片流，建立图片文件输入流
             */
            iis = ImageIO.createImageInputStream(fis);
            reader.setInput(iis, true);
            ImageReadParam irp = reader.getDefaultReadParam();
            /**
             * 定义裁剪区域
             */
            Rectangle rect = new Rectangle(x, y, width, height);
            irp.setSourceRegion(rect);
            /**
             * 转入文件图片流
             */
            bi = reader.read(0, irp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (iis != null) {
                try {
                    iis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return bi;
        }
    }


    /**
     * 导入本地图片到缓冲区
     *
     * @param imgLocalPath the img local path
     * @return buffered image
     */
    public static BufferedImage loadImageLocal(String imgLocalPath) {
        try {
            return ImageIO.read(new File(imgLocalPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导入网络图片到缓冲区
     *
     * @param imgUrl the img url
     * @return buffered image
     */
    public static BufferedImage loadImageUrl(String imgUrl) {
        try {
            URL url = new URL(imgUrl);
            return ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 写入图片到本地
     *
     * @param imgPath     文件地址+文件名+后缀
     * @param img         图片流
     * @param imageSuffix 图片后缀
     */
    public static void writeImageLocal(String imgPath, BufferedImage img, String imageSuffix) {
        if (imgPath != null && img != null) {
            try {
                File outPutFile = new File(imgPath);
                ImageIO.write(img, imageSuffix, outPutFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 合成图片
     *
     * @param originalImage 顶层图片
     * @param originalX     新图在背景图横坐标位置
     * @param originalY     新图在背景图纵坐标位置
     * @param newImage      背景图片
     * @param newWidth      顶层片宽度
     * @param newHeight     顶层图片高度
     * @return buffered image
     */
    public static BufferedImage synthesisImages(BufferedImage originalImage, Integer originalX, Integer originalY, BufferedImage newImage, Integer newWidth, Integer newHeight) {
        Graphics2D g;
        try {
            g = newImage.createGraphics();
            g.drawImage(originalImage, originalX, originalY, newWidth, newHeight, null);
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newImage;
    }

    /**
     * 对图片进行放大
     *
     * @param originalImage 原始图片
     * @param multiple      放大倍数
     * @return buffered image
     */
    public static BufferedImage zoomInImage(BufferedImage originalImage, Integer multiple) {
        int width = originalImage.getWidth() * multiple;

        int height = originalImage.getHeight() * multiple;

        return drawImage(originalImage, width, height);

    }

    /**
     * 对图片进行缩小
     *
     * @param originalImage 原始图片
     * @param multiple      缩小倍数
     * @param reduceWidth   缩放后减小宽度 (当times=-1时此参数及是图片宽度)
     * @param reduceHeight  缩放后减小高度(同上)
     * @return 缩小后的Image buffered image
     */
    public static BufferedImage zoomOutImage(BufferedImage originalImage, Integer multiple, int reduceWidth, int reduceHeight) {

        int width = originalImage.getWidth() / multiple - reduceWidth;
        if (width < 0) {
            width = originalImage.getWidth();
        }
        int height = originalImage.getHeight() / multiple - reduceHeight;
        if (height < 0) {
            height = originalImage.getHeight();
        }
        if (multiple == -1) {
            width = reduceWidth;
            height = reduceHeight;
        }
        return drawImage(originalImage, width, height);

    }

    /**
     * desc: 截取圆形
     *
     * @param srcFilePath         源图片文件路径
     * @param circularImgSavePath 新生成的图片的保存路径，需要带有保存的文件名和后缀
     * @param targetSize          文件的边长，单位：像素，最终得到的是一张正方形的图，所以要求targetSize<=源文件的最小边长
     * @param cornerRadius        圆角半径，单位：像素。如果=targetSize那么得到的是圆形图
     * @param startX              从x坐标开始截取
     * @param startY              从y坐标截取
     * @param fileSuffixType      文件保存类型
     * @return 文件的保存路径 string
     * @throws IOException
     */
    public static String makeCircularImg(String srcFilePath, String circularImgSavePath, int targetSize, int cornerRadius, float startX, float startY, String fileSuffixType) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File(srcFilePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        BufferedImage circularBufferImage = roundImage(bufferedImage, targetSize, cornerRadius, startX, startY);
        try {
            ImageIO.write(circularBufferImage, fileSuffixType, new File(circularImgSavePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return circularImgSavePath;
    }


    /**
     * 截取图片圆形
     *
     * @param image        the image
     * @param targetSize   文件的边长，单位：像素，最终得到的是一张正方形的图，所以要求targetSize<=源文件的最小边长
     * @param cornerRadius 圆角半径，单位：像素。如果=targetSize那么得到的是圆形图
     * @param startX       从x坐标开始截取
     * @param startY       从y坐标截取
     * @return the buffered image
     */
    public static BufferedImage roundImage(BufferedImage image, int targetSize, int cornerRadius, float startX, float startY) {
        BufferedImage outputImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = outputImage.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(startX, startY, targetSize, targetSize, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return outputImage;
    }


    /**
     * 将BufferedImage对象写入文件
     *
     * @param bufImg          BufferedImage对象
     * @param format          图片格式，可选[png,jpg,bmp]
     * @param saveImgFilePath 存储图片的完整位置，包含文件名
     * @return {boolean}
     */
    public static boolean writeToFile(BufferedImage bufImg, String format, String saveImgFilePath) {
        Boolean bool = false;
        try {
            bool = ImageIO.write(bufImg, format, new File(saveImgFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return bool;
        }
    }

    /**
     * 将byte转换为buff
     *
     * @param bytes
     * @return
     */
    public static BufferedImage byteToBufferedImage(byte[] bytes) {
        //将b作为输入流；
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        //将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
        try {
            BufferedImage image = ImageIO.read(in);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 绘画图片
     *
     * @param originalImage
     * @param width
     * @param height
     * @return
     */
    private static BufferedImage drawImage(BufferedImage originalImage, Integer width, Integer height) {
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }


    /**
     * 生成用户分享二维码
     *
     * @param weChatHeadImgUrl 用户头像URL
     * @param weChatNickname   用户昵称
     * @param circleTitle      圈子标题
     * @param circleLogo       圈子logoUrl
     * @param shareUserCode    分享用户推荐码
     * @param circleId         圈子ID
     * @param shareBgImg       背景图片地址 获取方法(由于静态方法不能获取路径,所以直接在外部获取后传入该值即可):  GetResource.class.getClassLoader().getResource(PropertiesUtil.getProperty("SHARE_QR_BG")).getPath()
     * @return
     */
    public static BufferedImage generateShareQrImg(String shareBgImg, String weChatHeadImgUrl, String weChatNickname, String circleTitle, String circleLogo, String shareUserCode, Long circleId) {
        // 加载背景图片 静态方法获取不到路径
        String share_qr_bg = shareBgImg;
        BufferedImage share_bg = loadImageLocal(share_qr_bg);
        // 加载用户头像
        BufferedImage user_heard = loadImageUrl(weChatHeadImgUrl);
        // 加载圈子头像 正方形 200X200
        BufferedImage circle_heard = loadImageUrl(PropertiesUtil.getProperty("SERER_IMG_HOST") + circleLogo + "-zfx.v01");
        // 构建分享链接
        String contentUrl = PropertiesUtil.getProperty("SHARE_HOST_URL") + "?shareUserCode=" + shareUserCode + "&circleId=" + circleId;
        // 生成二维码
        BufferedImage qrCode = QRCodeUtil.generateQrCode(contentUrl, 350, 350, 2);
        // 截取圆形
        user_heard = roundImage(user_heard, 132, 132, 0, 0);
        // 截取圈子圆形图片
        circle_heard = roundImage(circle_heard, 200, 200, 0, 0);

        // 合成二维码图片
        BufferedImage synthesisImages = synthesisImages(qrCode, 185, 35, share_bg, 318, 318);
        // 合成圈子logo
        synthesisImages = synthesisImages(circle_heard, 315, 169, synthesisImages, 60, 60);
        // 合成 分享用户头像
        synthesisImages = synthesisImages(user_heard, 335-getInitWidth(weChatNickname.length())/2, 445, synthesisImages, 60, 60);

        // 合成圈子名称 判断字数
        Integer initWidthPx = getInitWidth(circleTitle.length(), 343);
        synthesisImages = watermarkText(synthesisImages, circleTitle, 0, 1f, 25, 1, initWidthPx, 373, Color.BLACK);
        //合成用户昵称
        synthesisImages = watermarkText(synthesisImages, weChatNickname, 0, 1f, 23, 1, 405-getInitWidth(weChatNickname.length())/2, 465, Color.BLACK);
        return synthesisImages;
    }


    /**
     * 初始化字符串宽度 用户水平对齐文本
     *
     * @param lengthStr
     * @param initWidthPx
     * @return
     */
    private static int getInitWidth(int lengthStr, int initWidthPx) {
        if (lengthStr != 1) {
            int res = lengthStr - 1;
            initWidthPx = initWidthPx - res * 10;
            //计算间距 4px
            initWidthPx = initWidthPx - res * 2;
        }
        return initWidthPx;
    }

    private static int getInitWidth(int lengthStr) {
        int initWidthPx = 72;
        if (lengthStr != 1) {
            initWidthPx = initWidthPx + lengthStr * 20;
        }
        return initWidthPx;
    }

    public static void main(String[] args) {
        BufferedImage generateShareQrImg = generateShareQrImg("/Users/pantiemin/Downloads/workPath/idea/gravitation_app_api/src/main/resources/properties/share_qr_bg_v.png", "http://thirdwx.qlogo.cn/mmopen/vi_32/CYyEm6WlVevYyxicXfzEibUjiaSfnnILVMJ3D9Js2VSnsCThr4HHyibUPGhyFSMLQLiaKz4L3qq0I8pZJWOZ5eb79Aw/132", "回不够", "我是一个圈子名称", "wyyl-wecaht-app/product/1575454955000.jpg", "4", 1L);
        writeImageLocal("/Users/pantiemin/Downloads/workPath/idea/gravitation_app_api/src/main/resources/properties/" + System.currentTimeMillis() + ".png", generateShareQrImg, "png");
    }

}
