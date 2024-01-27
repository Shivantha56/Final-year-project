package lk.ijse.gdse.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.scene.control.Alert;
import lk.ijse.gdse.dto.ItemsDTO;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Qr {

    private String  convertedByteStream;
    private ItemsDTO itemsDTO;
    static final String path = "C:\\Working directory\\Today task\\JavaFX\\src\\main\\resources\\qr\\qr.png";

    public Qr(ItemsDTO itemsDTO) {
        this.itemsDTO = itemsDTO;
    }

    public void encode() {

        String val = convertObjectToStream(itemsDTO);
        System.out.println("encode 1 :"+ val);
        String charset = "ISO-8859-1";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        file.deleteOnExit();
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(new String(val.getBytes(), charset), BarcodeFormat.QR_CODE, 200, 200);
            MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), file);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "can not process qr code");
            alert.show();
            throw new RuntimeException(e);
        }
    }

    public ItemsDTO decode() {
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        try {
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(path)))));
            Result rslt = new MultiFormatReader().decode(binaryBitmap);
            return convertByteStreamToObject(rslt.getText());

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "can not read the qrcode");
            alert.show();
        }
        return null;
    }

    public String convertObjectToStream(ItemsDTO itemsDTO){
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(itemsDTO);
            System.out.println(bos.toString());
            return bos.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ItemsDTO convertByteStreamToObject(String s){
        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(s.getBytes());
             ObjectInputStream objectStream = new ObjectInputStream(byteStream)) {

            // Read the object from the stream
            return (ItemsDTO) objectStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
