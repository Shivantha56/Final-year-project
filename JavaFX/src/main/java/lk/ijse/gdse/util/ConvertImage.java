package lk.ijse.gdse.util;

import java.io.File;
import java.io.FileInputStream;

public class ConvertImage {
    private final String path = "C:\\Working directory\\Today task\\JavaFX\\src\\main\\resources\\qr\\qr.png";
    private final File file = new File(path);
    public FileInputStream saveImage(){
        try (FileInputStream inputStream = new FileInputStream(file);) {
            return inputStream;
        }catch (Exception ignored){

        }
        return null;
    }

}
