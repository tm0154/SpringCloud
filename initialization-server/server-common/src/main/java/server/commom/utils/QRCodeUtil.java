package server.commom.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.GenericMultipleBarcodeReader;
import com.google.zxing.multi.MultipleBarcodeReader;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang3.StringUtils;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 这是多维码生成与解析工具
 *
 * @Author tieMinPan
 * @Desc: Description
 * @Date 2019 -01-09 11:17
 */
public class QRCodeUtil {

    private static final Map<DecodeHintType, Object> HINTS;
    private static final Map<DecodeHintType, Object> HINTS_PURE;

    static {
        HINTS = new EnumMap<>(DecodeHintType.class);
        HINTS.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        HINTS.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(BarcodeFormat.class));
        HINTS_PURE = new EnumMap<>(HINTS);
        HINTS_PURE.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
    }

    /**
     * 生成二维码
     *
     * @param strContent 内容
     * @param width      宽度
     * @param height     高度
     * @param margin     边距
     * @return
     */
    public static BufferedImage generateQrCode(String strContent, int width, int height, int margin) {
        if (StringUtils.isEmpty(strContent)) {
            return null;
        }
        return generateBufferedImage(BarcodeFormat.QR_CODE, strContent, width, height, margin);
    }


    /**
     * 生成条形码
     *
     * @param strContent 内容
     * @param width      宽度(建议105)
     * @param height     高度(建议50 不然有可能出现识别误差)
     * @param margin     边距
     * @return
     */
    public static BufferedImage generateBarcode(String strContent, int width, int height, int margin) {
        if (StringUtils.isEmpty(strContent)) {
            return null;
        }
        return generateBufferedImage(BarcodeFormat.CODE_128, strContent, width, height, margin);
    }

    /**
     * 生成条形码(带底部文字条形码内容)
     *
     * @param strContent
     * @return
     */
    public static byte[] generateBarcode(String strContent) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        generate(strContent, ous, 150, "image/png");
        return ous.toByteArray();
    }

    /**
     * 生成条形码(带底部文字条形码内容)
     *
     * @param strContent
     * @return
     */
    public static BufferedImage generateBarcodeAndText(String strContent) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        generate(strContent, ous, 150, "image/png");
        return ImagesUtil.byteToBufferedImage(ous.toByteArray());
    }

    /**
     * 初始化多维码配置
     *
     * @param errorLevel
     * @param margin
     * @return
     */
    private static Hashtable<EncodeHintType, Object> initHintsMap(ErrorCorrectionLevel errorLevel, Integer margin) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>(16);
        hints.put(EncodeHintType.ERROR_CORRECTION, errorLevel);
        hints.put(EncodeHintType.MARGIN, margin);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        return hints;
    }


    /**
     * 生成多维码
     *
     * @param typeCode    多维码类型 :条形码-BarcodeFormat.CODE_128,二维啊-BarcodeFormat.QR_CODE
     * @param strContents 内容
     * @param width       宽度
     * @param height      高度
     * @param margin      边距
     * @return
     */
    private static BufferedImage generateBufferedImage(BarcodeFormat typeCode, String strContents, int width, int height, int margin) {

        Hashtable<EncodeHintType, Object> hints = initHintsMap(ErrorCorrectionLevel.H, margin);
        // 尺寸
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(strContents, typeCode, width, height, hints);
            MatrixToImageConfig config = new MatrixToImageConfig(0xFF000001, 0xFFFFFFFF);
            return MatrixToImageWriter.toBufferedImage(bitMatrix, config);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析多维码图片
     *
     * @param image 多维码图片
     */
    public static String parseMultidimensionalImages(BufferedImage image) {
        StringBuffer resultStr = new StringBuffer();
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));
        Collection<Result> results = new ArrayList<>(1);
        try {
            Reader reader = new MultiFormatReader();
            ReaderException savedException = null;
            try {
                MultipleBarcodeReader multiReader = new GenericMultipleBarcodeReader(reader);
                Result[] theResults = multiReader.decodeMultiple(bitmap, HINTS);
                if (theResults != null) {
                    results.addAll(Arrays.asList(theResults));
                }
            } catch (ReaderException re) {
                savedException = re;
            }
            if (results.isEmpty()) {
                try {
                    // Look for normal barcode in photo
                    Result theResult = reader.decode(bitmap, HINTS);
                    if (theResult != null) {
                        results.add(theResult);
                    }
                } catch (ReaderException re) {
                    savedException = re;
                }
            }

            if (results.isEmpty()) {
                try {
                    // Try again with other binarizer
                    BinaryBitmap hybridBitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Result theResult = reader.decode(hybridBitmap, HINTS);
                    if (theResult != null) {
                        results.add(theResult);
                    }
                } catch (ReaderException re) {
                    savedException = re;
                }
            }

            if (results.isEmpty()) {
                try {
                    throw savedException == null ? NotFoundException.getNotFoundInstance() : savedException;
                } catch (FormatException | ChecksumException e) {
                    e.printStackTrace();
                } catch (ReaderException e) {
                    e.printStackTrace();
                }
            }

            for (Result result : results) {
                resultStr.append(result.getText());
            }

        } catch (RuntimeException re) {
            re.printStackTrace();
        } finally {
            return resultStr.toString();
        }
    }


    /**
     * 生成条形码核心方法(带底部文字)
     *
     * @param strContent
     * @param ous        输出流
     * @param dpi        精度 150
     * @param format     格式: "image/png"
     */
    private static void generate(String strContent, OutputStream ous, int dpi, String format) {
        if (StringUtils.isEmpty(strContent) || ous == null) {
            return;
        }
        Code128Bean bean=new Code128Bean();
        // 配置对象
        bean.setModuleWidth(0.35);
        bean.doQuietZone(false);
        try {
            // 输出到流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);
            // 生成条形码
            bean.generateBarcode(canvas, strContent);
            // 结束绘制
            canvas.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
