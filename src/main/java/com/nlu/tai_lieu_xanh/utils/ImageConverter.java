package com.nlu.tai_lieu_xanh.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class ImageConverter {
  public static final String WEBP_CONTENT_TYPE = "image/webp";

  public static void convertToWebp(BufferedImage image, OutputStream os) throws IOException {
    ImageIO.write(image, "webp", os);
  }

}
